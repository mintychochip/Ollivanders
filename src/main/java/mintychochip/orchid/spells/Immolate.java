package mintychochip.orchid.spells;

import mintychochip.orchid.container.OrchidMechanic;
import mintychochip.orchid.shape.OrchidAoe;
import mintychochip.orchid.shape.OrchidSelf;
import mintychochip.orchid.shape.implementation.AoeImplementation;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;

public class Immolate extends OrchidMechanic implements OrchidSelf, OrchidAoe {
    @Override
    public boolean castAoe() {
        Location castLocation = ((AoeImplementation) getImplementation()).getCastLocation();
        if (castLocation.getWorld() == null) {
            return false;
        }
        for (Entity nearbyEntity : ((AoeImplementation) getImplementation()).getNearbyEntities()) {
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
