package mintychochip.ollivanders.listener;

import com.google.gson.Gson;
import mintychochip.genesis.Genesis;
import mintychochip.genesis.util.Serializer;
import mintychochip.ollivanders.Ollivanders;
import mintychochip.ollivanders.api.EntitySpellDamageEvent;
import mintychochip.ollivanders.api.SpellCastEvent;
import mintychochip.ollivanders.api.SpellExplosionEvent;
import mintychochip.ollivanders.container.Context;
import mintychochip.ollivanders.container.Spell;
import mintychochip.ollivanders.container.SpellMechanic;
import mintychochip.ollivanders.enums.Shape;
import mintychochip.ollivanders.items.container.WandData;
import mintychochip.ollivanders.items.util.OllivandersSerializer;
import mintychochip.ollivanders.items.container.spellbook.BookData;
import mintychochip.ollivanders.util.SpellCaster;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SpellListener implements Listener {

    private final SpellCaster spellCaster;
    public SpellListener(SpellCaster spellCaster) {
        this.spellCaster = spellCaster;
    }
    private final List<Player> waitingForNextEvent = new ArrayList<>();

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerCastEvent(final PlayerInteractEvent event) {
        Player player = event.getPlayer();

        PlayerInventory inventory = player.getInventory();
        if (event.getHand() == EquipmentSlot.OFF_HAND) {
            return;
        }
        Action action = event.getAction();
        EquipmentSlot hand = event.getHand();
        boolean conditionTwo = inventory.getItemInOffHand().getItemMeta() instanceof BookMeta && inventory.getItemInMainHand().getType() == Material.BLAZE_ROD;
        if (action == Action.RIGHT_CLICK_AIR && hand == EquipmentSlot.HAND && conditionTwo) {
            waitingForNextEvent.add(player);
            return;
        }
        if (waitingForNextEvent.contains(player)) {
            waitingForNextEvent.remove(player);
            return;
        }
        if (inventory.getItemInMainHand().getType() == Material.BLAZE_ROD) {
            ItemStack book = inventory.getItemInOffHand();
            ItemMeta itemMeta = book.getItemMeta();


            if (itemMeta instanceof BookMeta bookMeta) {
                if (!bookMeta.hasPages()) {
                    return;
                }
                BookData bookData = OllivandersSerializer.extractBookData(bookMeta);
                if (bookData == null) {
                    return;
                }
                try {
                    bookData.updateBook(book);

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                if (player.isSneaking()) {
                    bookData.openSpellBook(player, book);

                } else {
                    Spell spellOnCurrentPage = bookData.getSpellOnCurrentPage();
                    if (spellOnCurrentPage == null) {
                        return;
                    }
                    ItemStack blazerod = inventory.getItemInMainHand();
                    ItemMeta blazerodItemMeta = blazerod.getItemMeta();
                    PersistentDataContainer persistentDataContainer = blazerodItemMeta.getPersistentDataContainer();
                    WandData wandData = null;

                    if(persistentDataContainer.has(Genesis.getKey("wand"),PersistentDataType.STRING)) {
                        wandData = new Gson().fromJson(persistentDataContainer.get(Genesis.getKey("wand"),PersistentDataType.STRING),WandData.class);
                    }
                    if(wandData != null) {
                        spellCaster.cast(spellOnCurrentPage, wandData, new Context(player));
                    }
                }
            }
        }
    }

    @EventHandler
    public void onProjectileSpellHit(final ProjectileHitEvent event) {
        int entityId = event.getEntity().getEntityId();
        if (Ollivanders.getProjectileHandler().getProjectileMap().containsKey(entityId)) {
            SpellMechanic spellMechanic = Ollivanders.getProjectileHandler().getProjectileMap().get(entityId);
            Player player = spellMechanic.getContext().getPlayer();
            Location hitLocation;
            if (event.getHitEntity() != null) {
                hitLocation = event.getHitEntity().getLocation();
            } else {
                hitLocation = event.getHitBlock().getLocation();
            }
            Context context = new Context(player, hitLocation);
            if (spellMechanic.getTransition() != null) {
                spellCaster.cast(spellMechanic.getTransition(), spellMechanic.getWandData(), context); //can add delay in the future
            }
        }
    }

    @EventHandler
    public void onSpellCastEvent(final SpellCastEvent event) {
        SpellMechanic mechanic = event.getSpell().getMechanic();
        Player player = event.getPlayer();
        if (mechanic.getTransition() != null && mechanic.getShape() != Shape.PROJECTILE) {
            Context passingContext = null;
            switch (mechanic.getShape()) {
                case AREA -> passingContext = new Context(player, event.getContext().getHitLocation());
                case SELF -> passingContext = new Context(player, player.getLocation());
            }
            Spell transition = mechanic.getTransition();
            spellCaster.cast(transition, mechanic.getWandData(), passingContext);

        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    private void entityDamageEvent(final EntitySpellDamageEvent event) {
        if (!event.isCancelled()) {
            event.getInflicted().damage(event.getDamagePacket().getDamage());
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    private void entityDssamageEvent(final EntitySpellDamageEvent event) {
        if (event.getInflicted().getType() == EntityType.PIG) {
            event.setCancelled(true);
        }
        if (event.getCause() == EntityDamageEvent.DamageCause.BLOCK_EXPLOSION) {
            event.setCancelled(true);
        }
    }
    @EventHandler
    private void onClicK(final PlayerInteractEvent event) {
        Player player = event.getPlayer();
        PlayerInventory inventory = player.getInventory();
        ItemStack itemInMainHand = inventory.getItemInMainHand();
        if(itemInMainHand.getType() == Material.AIR) {
            return;
        }
        ItemMeta itemMeta = itemInMainHand.getItemMeta();
        if(itemMeta == null) {
            return;
        }
        PersistentDataContainer persistentDataContainer = itemMeta.getPersistentDataContainer();
        if (persistentDataContainer.has(Genesis.getKey("wand"), PersistentDataType.STRING)) {
            String s = persistentDataContainer.get(Genesis.getKey("wand"), PersistentDataType.STRING);
            Bukkit.broadcastMessage(s);
        }
    }
}
