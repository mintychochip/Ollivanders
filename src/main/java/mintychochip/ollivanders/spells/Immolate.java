package mintychochip.ollivanders.spells;

import mintychochip.ollivanders.container.WizardMechanic;
import mintychochip.ollivanders.shape.WizardAoe;
import mintychochip.ollivanders.shape.WizardSelf;
import mintychochip.ollivanders.shape.implementation.AoeImplementation;
import org.bukkit.Location;
import org.bukkit.entity.Entity;

public class Immolate extends WizardMechanic implements WizardSelf, WizardAoe {
    @Override
    public boolean castAoe() {
        Location castLocation = getImplementation().getCastLocation();
        if (castLocation.getWorld() == null) {
            return false;
        }
        for (Entity nearbyEntity : getImplementation().getNearbyEntities()) {
            nearbyEntity.setFireTicks(mechanicSettings.getDuration());
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
