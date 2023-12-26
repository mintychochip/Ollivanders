package mintychochip.ollivanders.listener;

import mintychochip.genesis.Genesis;
import mintychochip.genesis.container.ItemData;
import mintychochip.genesis.util.Serializer;
import mintychochip.ollivanders.Ollivanders;
import mintychochip.ollivanders.api.AoeCastEvent;
import mintychochip.ollivanders.api.SelfCastEvent;
import mintychochip.ollivanders.api.SpellCastEvent;
import mintychochip.ollivanders.container.*;
import mintychochip.ollivanders.util.SpellCaster;
import mintychochip.ollivanders.wand.container.WandData;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.IOException;
import java.io.Serial;

public class SpellListener implements Listener {

    @EventHandler
    public void onPlayerCast(final PlayerInteractEvent event) {
        if (event.getPlayer().getInventory().getItemInMainHand().getType() == Material.BLAZE_ROD) {
            ItemMeta itemMeta = event.getPlayer().getInventory().getItemInOffHand().getItemMeta();
            if (itemMeta instanceof BookMeta bookMeta) {
                if (!bookMeta.hasPages()) {
                    return;
                }
                try {
                    SpellBook spellBook = new SpellBook(bookMeta);
                    Spell spell = spellBook.mainSpell(0);
                    SpellCaster.cast(spell, extractWandData(event.getPlayer().getInventory().getItemInMainHand()), new Context(event.getPlayer()));
                } catch (IOException e) {
                    throw new RuntimeException(e);
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

            SpellCaster.castEffect(spellMechanic, context);
            if (spellMechanic.getTransition() != null) {

                SpellCaster.cast(spellMechanic.getTransition(), spellMechanic.getWandData(), context);
            }
        }
    }

    @EventHandler
    public void onSpellCastEvent(final SpellCastEvent event) {
        SpellMechanic mechanic = event.getSpell().getMechanic();

        if (mechanic.getMechanicSettings().isPersistent()) {
            Ollivanders.getPersistentSpellManager().add(event.getSpell(), mechanic.getContext(), mechanic.getWandData());
        }
    }

    @EventHandler
    public void onAoeCastEvent(final AoeCastEvent event) {
        SpellMechanic mechanic = event.getSpell().getMechanic();
        Player player = event.getPlayer();
        if (mechanic.getTransition() != null) {
            SpellCaster.cast(mechanic.getTransition(), mechanic.getWandData(), new Context(player, event.getContext().getHitLocation()));
        }
    }

    @EventHandler
    public void onSelfCastEvent(final SelfCastEvent event) {
        SpellMechanic mechanic = event.getSpell().getMechanic();
        Player player = event.getPlayer();
        if (mechanic.getTransition() != null) {
            SpellCaster.cast(mechanic.getTransition(), mechanic.getWandData(), new Context(player, player.getLocation()));
        }
    }

    public WandData extractWandData(ItemStack itemStack) {
        if (itemStack.getItemMeta() == null) {
            return null;
        }
        try {
            byte[] bytes = itemStack.getItemMeta().getPersistentDataContainer().get(Genesis.getKeys().getMap().get("wand"), PersistentDataType.BYTE_ARRAY);
            if(bytes != null) {
                return (WandData) Serializer.deserialize(bytes);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @EventHandler
    public void onPlayerClick(final PlayerInteractEvent event) {

    }
}
