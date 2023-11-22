package mintychochip.orchid.listener;

import mintychochip.orchid.container.Context;
import mintychochip.orchid.container.OrchidBook;
import mintychochip.orchid.container.OrchidMechanic;
import mintychochip.orchid.container.OrchidSpell;
import mintychochip.orchid.events.AoeCastEvent;
import mintychochip.orchid.events.LaserCastEvent;
import mintychochip.orchid.events.SelfCastEvent;
import mintychochip.orchid.handler.ProjectileHandler;
import mintychochip.orchid.sequencer.BookReader;
import mintychochip.orchid.spellcaster.OrchidCaster;
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

public class PlayerListener implements Listener {
    @EventHandler
    public void playerInteract(PlayerInteractEvent event) {
        PlayerInventory playerInventory = event.getPlayer().getInventory();

        if (playerInventory.getItemInMainHand().getType() != Material.BLAZE_ROD) {
            return;
        }
        if (event.getHand().equals(EquipmentSlot.HAND) && event.getAction() == Action.LEFT_CLICK_AIR) {
            ItemStack itemInOffHand = playerInventory.getItemInOffHand();
            if (itemInOffHand.getType() == Material.WRITABLE_BOOK && itemInOffHand.getItemMeta() instanceof BookMeta bookMeta) {
                OrchidBook book = new OrchidBook(new BookReader(bookMeta));
                OrchidSpell spell = book.getSpell(0);
                OrchidCaster caster = new OrchidCaster(spell);
                caster.cast(new Context(event.getPlayer()));
            }
        }
    }

    @EventHandler
    public void onProjectileHit(ProjectileHitEvent event) {
        OrchidSpell orchidSpell = ProjectileHandler.getInstance().getHitMap().remove(event.getEntity().getEntityId());
        if (orchidSpell == null) {
            return;
        }
        orchidSpell.getMechanic().setContext(new Context(orchidSpell.getMechanic().getContext().getPlayer(), null, event.getHitBlock(), event.getHitEntity()));
        if (orchidSpell.getMechanic().getTransition() != null) {
            OrchidCaster caster = new OrchidCaster(orchidSpell.getMechanic().getTransition());
            Context context = orchidSpell.getMechanic().getContext();
            Location location = null;
            if (event.getHitBlock() == null) {
                location = event.getHitEntity().getLocation();
            } else {
                location = event.getHitBlock().getLocation();
            }
            caster.cast(new Context(context.getPlayer(), location, event.getHitBlock(), event.getHitEntity()));
        }
    }

    @EventHandler
    public void onAoeCastEvent(AoeCastEvent event) {
        OrchidMechanic orchidMechanic = event.getMechanic();
        if (orchidMechanic == null) {
            return;
        }
        if (orchidMechanic.getTransition() != null) {
            OrchidCaster caster = new OrchidCaster(orchidMechanic.getTransition());
            caster.cast(new Context(orchidMechanic.getContext().getPlayer(), orchidMechanic.getContext().getHitLocation()));
        }
    }

    @EventHandler
    public void onPlayerShiftTest(PlayerToggleSneakEvent event) {
        boolean sneaking = event.getPlayer().isSneaking();

            event.getPlayer().getLocation().getWorld().spawnParticle(Particle.ELECTRIC_SPARK,event.getPlayer().getLocation(),50);
    }

    @EventHandler
    public void onSelfCastEvent(SelfCastEvent event) {
        OrchidMechanic orchidMechanic = event.getMechanic();
        if(orchidMechanic == null) {
            return;
        }
        if(orchidMechanic.getTransition() != null) {
            OrchidCaster caster = new OrchidCaster(orchidMechanic.getTransition());
            Player player = orchidMechanic.getContext().getPlayer();
            caster.cast(new Context(player,player.getLocation()));
        }
    }
    //@EventHandler
    public void onLaserCastEvent(LaserCastEvent event) {
        OrchidMechanic orchidMechanic = event.getMechanic();
        if (orchidMechanic == null) {
            return;
        }
        if (orchidMechanic.getTransition() != null) {
            OrchidCaster caster = new OrchidCaster(orchidMechanic.getTransition());
            caster.cast(new Context(orchidMechanic.getContext().getPlayer(), orchidMechanic.getContext().getHitLocation()));
        }
    }

}
