package mintychochip.ollivanders.listener;

import mintychochip.ollivanders.Ollivanders;
import mintychochip.ollivanders.betterwand.WandBoost;
import mintychochip.ollivanders.container.Context;
import mintychochip.ollivanders.container.WizardBook;
import mintychochip.ollivanders.container.WizardMechanic;
import mintychochip.ollivanders.container.WizardSpell;
import mintychochip.ollivanders.events.AoeCastEvent;
import mintychochip.ollivanders.events.LaserCastEvent;
import mintychochip.ollivanders.events.SelfCastEvent;
import mintychochip.ollivanders.handler.ProjectileHandler;
import mintychochip.ollivanders.spellcaster.WizardCaster;
import mintychochip.ollivanders.util.Keys;
import mintychochip.ollivanders.util.Serializer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.persistence.PersistentDataType;

import java.io.IOException;

public class PlayerListener implements Listener {

    int count = 0;
    @EventHandler
    public void playerInteract(final PlayerInteractEvent event) {
        PlayerInventory playerInventory = event.getPlayer().getInventory();

        if (playerInventory.getItemInMainHand().getType() != Material.BLAZE_ROD) {
            return;
        }
        if (event.getHand().equals(EquipmentSlot.HAND) && event.getAction() == Action.LEFT_CLICK_AIR && event.getPlayer().getInventory().getItemInMainHand().getItemMeta().getPersistentDataContainer().has(Keys.getBoost(),PersistentDataType.BYTE_ARRAY)) {
            ItemStack itemInOffHand = playerInventory.getItemInOffHand();
            if (itemInOffHand.getType() == Material.WRITABLE_BOOK && itemInOffHand.getItemMeta() instanceof BookMeta bookMeta) {

                WizardBook wizardBook = new WizardBook(bookMeta);
                WizardSpell spell = wizardBook.getSpell(0);
                Bukkit.broadcastMessage(spell.getMechanic().getMechanicSettings().isPersistent() +"");
                Context context = new Context(event.getPlayer());
                WizardCaster caster = new WizardCaster(spell);
                byte[] bytes = playerInventory.getItemInMainHand().getItemMeta().getPersistentDataContainer().get(Keys.getBoost(), PersistentDataType.BYTE_ARRAY);
                try {
                    WandBoost wandBoost = Serializer.deserializeBoost(bytes);
                    caster.cast(context, wandBoost);
                    if(spell.getMechanic().getMechanicSettings().isPersistent()) {
                        Ollivanders.getPersistentSpellManager().add(spell,context,wandBoost);
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    @EventHandler
    public void onProjectileHit(ProjectileHitEvent event) {
        WizardSpell WizardSpell = ProjectileHandler.getInstance().getHitMap().remove(event.getEntity().getEntityId());
        if (WizardSpell == null) {
            return;
        }
        WizardSpell.getMechanic().setContext(new Context(WizardSpell.getMechanic().getContext().getPlayer(), null, event.getHitBlock(), event.getHitEntity()));
        if (WizardSpell.getMechanic().getTransition() != null) {
            WizardCaster caster = new WizardCaster(WizardSpell.getMechanic().getTransition());
            Context context = WizardSpell.getMechanic().getContext();
            Location location = null;
            if (event.getHitBlock() == null) {
                location = event.getHitEntity().getLocation();
            } else {
                location = event.getHitBlock().getLocation();
            }
            caster.cast(new Context(context.getPlayer(), location, event.getHitBlock(), event.getHitEntity()),WizardSpell.getMechanic().getWandBoost());
        }
    }

    @EventHandler
    public void onAoeCastEvent(AoeCastEvent event) {
        WizardMechanic WizardMechanic = event.getMechanic();
        if (WizardMechanic == null) {
            return;
        }
        Bukkit.broadcastMessage("CAST");
        if (WizardMechanic.getTransition() != null) {
            Bukkit.broadcastMessage("wrong block");
            WizardCaster caster = new WizardCaster(WizardMechanic.getTransition());
            caster.cast(new Context(WizardMechanic.getContext().getPlayer(), WizardMechanic.getContext().getHitLocation()),WizardMechanic.getWandBoost());
        }
        if(event.getMechanic().getMechanicSettings().isPersistent()) {
            Ollivanders.getPersistentSpellManager().add(event.getSpell(),new Context(WizardMechanic.getContext().getPlayer(), WizardMechanic.getContext().getHitLocation()),WizardMechanic.getWandBoost());
        }
    }

    @EventHandler
    public void onPlayerShiftTest(PlayerToggleSneakEvent event) {
        boolean sneaking = event.getPlayer().isSneaking();

        event.getPlayer().getLocation().getWorld().spawnParticle(Particle.ELECTRIC_SPARK,event.getPlayer().getLocation(),50);
    }

    @EventHandler
    public void onSelfCastEvent(SelfCastEvent event) {
        WizardMechanic WizardMechanic = event.getMechanic();
        if(WizardMechanic == null) {
            return;
        }
        if(WizardMechanic.getTransition() != null) {
            WizardCaster caster = new WizardCaster(WizardMechanic.getTransition());
            Player player = WizardMechanic.getContext().getPlayer();
            caster.cast(new Context(player,player.getLocation()),WizardMechanic.getWandBoost());
        }
    }
    //@EventHandler
    public void onLaserCastEvent(LaserCastEvent event) {
        WizardMechanic WizardMechanic = event.getMechanic();
        if (WizardMechanic == null) {
            return;
        }
        if (WizardMechanic.getTransition() != null) {
            WizardCaster caster = new WizardCaster(WizardMechanic.getTransition());
            caster.cast(new Context(WizardMechanic.getContext().getPlayer(), WizardMechanic.getContext().getHitLocation()),WizardMechanic.getWandBoost());
        }
    }

}
