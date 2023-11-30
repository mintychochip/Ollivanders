package mintychochip.ollivanders.spells;

import mintychochip.ollivanders.container.WizardMechanic;
import mintychochip.ollivanders.shape.WizardAoe;
import mintychochip.ollivanders.shape.WizardSelf;
import mintychochip.ollivanders.shape.implementation.AoeImplementation;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;

public class Immolate extends WizardMechanic implements WizardSelf, WizardAoe {
    @Override
    public boolean castAoe() {
        Location castLocation = getImplementation().getCastLocation();
        if (castLocation.getWorld() == null) {
            return false;
        }
        double range = getMechanicSettings().getRange() / 2f;
        castLocation.getWorld().spawnParticle(Particle.FLAME,castLocation,50,range,range,range,0,null,true);
        for (Entity nearbyEntity : getImplementation().getNearbyLivingEntities()) {
            if(nearbyEntity != context.getPlayer()) {
                nearbyEntity.setFireTicks(mechanicSettings.getDuration());
                double random = 0.5;
                nearbyEntity.getLocation().getWorld().spawnParticle(Particle.SMOKE_LARGE,nearbyEntity.getLocation().add(0f,2f,0f),1,random,random,random,0,null,true);
            }
        }
        return true;
    }

    @Override
    public boolean castSelf() {
        Location castLocation = context.getPlayer().getLocation();
        if (castLocation.getWorld() == null) {
            return false;
        }
        context.getPlayer().setFireTicks(mechanicSettings.getDuration());
        return true;
    }

    @Override
    public void applyParticleSelf() {
        //need a lib for this
    }
}
