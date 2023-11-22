package mintychochip.orchid.spells;

import mintychochip.orchid.container.OrchidMechanic;
import mintychochip.orchid.shape.OrchidAoe;
import mintychochip.orchid.shape.implementation.AoeImplementation;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.List;

public class Switch extends OrchidMechanic implements OrchidAoe {
    @Override
    public boolean castAoe() {
        Location castLocation = ((AoeImplementation) getImplementation()).getCastLocation();
        if(castLocation == null) {
            return false;
        }
        List<Entity> nearbyEntities = ((AoeImplementation) getImplementation()).getNearbyEntities();
        Location location = context.getPlayer().getLocation();
        for (Entity nearbyEntity : nearbyEntities) {
            if(nearbyEntity instanceof LivingEntity entity) {
                if(entity instanceof Player) {
                    context.getPlayer().teleport(entity.getLocation());
                    entity.teleport(location);
                }
            }

        }

        return true;
    }
}
