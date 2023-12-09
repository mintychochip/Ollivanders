package mintychochip.ollivanders.listener;

import mintychochip.genesis.Genesis;
import mintychochip.ollivanders.Ollivanders;
import mintychochip.ollivanders.api.AoeCastEvent;
import mintychochip.ollivanders.container.*;
import mintychochip.ollivanders.util.SpellCaster;
import mintychochip.ollivanders.wand.Serializer;
import mintychochip.ollivanders.wand.container.WandData;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.io.IOException;

public class PlayerListener implements Listener {

    @EventHandler
    public void onPlayerCast(final PlayerInteractEvent event) {
        if(event.getPlayer().getInventory().getItemInMainHand().getType() == Material.BLAZE_ROD) {
            ItemMeta itemMeta = event.getPlayer().getInventory().getItemInOffHand().getItemMeta();
            if(itemMeta instanceof BookMeta bookMeta) {
                if (!bookMeta.hasPages()) {
                    return;
                }
                try {
                    SpellBook spellBook = new SpellBook(bookMeta);
                    Spell spell = spellBook.mainSpell(0);
                    SpellCaster.cast(spell,extractWandData(event.getPlayer().getInventory().getItemInMainHand()),new Context(event.getPlayer()));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
    @EventHandler
    public void onProjectileSpellHit(final ProjectileHitEvent event) {
        int entityId = event.getEntity().getEntityId();
        if(Ollivanders.getProjectileHandler().getProjectileMap().containsKey(entityId)) {
            SpellMechanic spellMechanic = Ollivanders.getProjectileHandler().getProjectileMap().get(entityId);

            Player player = spellMechanic.getContext().getPlayer();
            Location hitLocation;
            if(event.getHitEntity() != null) {
                hitLocation = event.getHitEntity().getLocation();
            } else {
                hitLocation = event.getHitBlock().getLocation();
            }

            Context context = new Context(player,hitLocation);

            SpellCaster.castEffect(spellMechanic, context);

            if(spellMechanic.getTransition() != null) {

                SpellCaster.cast(spellMechanic.getTransition(),spellMechanic.getWandData(),context);
            }
        }
    }
    @EventHandler
    public void onAoeCastEvent(final AoeCastEvent event) {

        SpellMechanic mechanic = event.getSpell().getMechanic();
        if(mechanic.getMechanicSettings().isPersistent()) {
            Ollivanders.getPersistentSpellManager().add(event.getSpell(),mechanic.getContext(),mechanic.getWandData());
        }
        if(mechanic.getTransition() != null) {

        }
    }
    public WandData extractWandData(ItemStack itemStack) {

        try {
            return (WandData) Serializer.deserialize(itemStack
                    .getItemMeta()
                    .getPersistentDataContainer()
                    .get(Genesis.getKeys().getMap().get("wand"), PersistentDataType.BYTE_ARRAY));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
