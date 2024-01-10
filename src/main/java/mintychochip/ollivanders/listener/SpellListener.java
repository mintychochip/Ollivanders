package mintychochip.ollivanders.listener;

import mintychochip.genesis.Genesis;
import mintychochip.genesis.util.Serializer;
import mintychochip.ollivanders.Ollivanders;
import mintychochip.ollivanders.api.SpellCastEvent;
import mintychochip.ollivanders.container.Context;
import mintychochip.ollivanders.container.Spell;
import mintychochip.ollivanders.container.SpellBook;
import mintychochip.ollivanders.container.SpellMechanic;
import mintychochip.ollivanders.enums.Shape;
import mintychochip.ollivanders.util.SpellCaster;
import mintychochip.ollivanders.wand.config.ComponentRegistry;
import mintychochip.ollivanders.wand.container.WandData;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SpellListener implements Listener {

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
            Bukkit.broadcastMessage("here");
            waitingForNextEvent.add(player);
            return;
        }
        if(waitingForNextEvent.contains(player)) {
            waitingForNextEvent.remove(player);
            player.closeInventory();
            return;
        }
        if (inventory.getItemInMainHand().getType() == Material.BLAZE_ROD) {
            ItemMeta itemMeta = inventory.getItemInOffHand().getItemMeta();
            if (itemMeta instanceof BookMeta bookMeta) {
                if (!bookMeta.hasPages()) {
                    return;
                }
                try {
                    SpellBook spellBook = new SpellBook(bookMeta);
                    Spell spell = spellBook.mainSpell(0);
                    SpellCaster.cast(spell, extractWandData(inventory.getItemInMainHand()), new Context(player));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
    @EventHandler
    public void onInteract(final PlayerInteractEvent event) {
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
                SpellCaster.cast(spellMechanic.getTransition(), spellMechanic.getWandData(), context); //can add delay in the future
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
                case AREA -> {
                    passingContext = new Context(player, event.getContext().getHitLocation());
                }
                case SELF -> {
                    passingContext = new Context(player, player.getLocation());
                }
            }
            Spell transition = mechanic.getTransition();
            SpellCaster.cast(transition, mechanic.getWandData(), passingContext);
        }
    }
    public WandData extractWandData(ItemStack itemStack) {
        if (itemStack.getItemMeta() == null) {
            return null;
        }
        try {
            byte[] bytes = itemStack.getItemMeta().getPersistentDataContainer().get(Genesis.getKeys().getMap().get("wand"), PersistentDataType.BYTE_ARRAY);
            if (bytes != null) {
                return (WandData) Serializer.deserialize(bytes);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
