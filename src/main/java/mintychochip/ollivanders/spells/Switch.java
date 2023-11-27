package mintychochip.ollivanders.spells;

import mintychochip.ollivanders.container.WizardMechanic;
import mintychochip.ollivanders.shape.WizardAoe;
import mintychochip.ollivanders.shape.implementation.AoeImplementation;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.List;

public class Switch extends WizardMechanic implements WizardAoe {
    @Override
    public boolean castAoe() {
        Location castLocation = getImplementation().getCastLocation();
        if(castLocation == null) {
            return false;
        }
        List<Entity> nearbyEntities = getImplementation().getNearbyEntities();
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
