package mintychochip.ollivanders.spells;

import mintychochip.ollivanders.container.WizardMechanic;
import mintychochip.ollivanders.shape.WizardAoe;
import mintychochip.ollivanders.shape.WizardSelf;
import mintychochip.ollivanders.shape.implementation.AoeImplementation;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;

import java.util.List;

public class Fireblast extends WizardMechanic implements WizardSelf, WizardAoe {
    @Override
    public boolean castAoe() {
        Location castLocation = ((AoeImplementation) getImplementation()).getCastLocation();
        List<Entity> nearbyEntities = ((AoeImplementation) getImplementation()).getNearbyEntities();
        for (Entity nearbyEntity : nearbyEntities) {
            int fireTicks = nearbyEntity.getFireTicks();
            if(fireTicks > 0) {
                if(nearbyEntity instanceof LivingEntity entity && entity != context.getPlayer()) {
                    entity.damage(mechanicSettings.getDamage(),context.getPlayer());
                    entity.getLocation().getWorld().spawnParticle(Particle.LAVA,entity.getLocation(), mechanicSettings.getDamage());
                }
            }
        }
        return true;
    }

    @Override
    public boolean castSelf() {
        return false;
    }

    @Override
    public void applyParticleSelf() {

    }
}
