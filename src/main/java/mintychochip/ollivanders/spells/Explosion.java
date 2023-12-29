package mintychochip.ollivanders.spells;

import mintychochip.ollivanders.container.SpellMechanic;
import mintychochip.ollivanders.spells.shape.SpellArea;
import mintychochip.ollivanders.spells.shape.SpellSelf;
import org.bukkit.Location;

public class Explosion extends SpellMechanic implements SpellArea, SpellSelf {
    @Override
    public boolean castArea() {
        return genericCastMethod(null);
    }

    @Override
    public boolean castSelf() {
        return genericCastMethod(context.getPlayer().getLocation());
    }
    public boolean genericCastMethod(Location location) {
        Location castLocation = getCastLocation();
        if(location != null) {
            castLocation = location;
        }
        if(castLocation == null) {
            return false;
        }
        return castLocation.getWorld().createExplosion(castLocation, (float) effectiveMagnitude());
    }
}
