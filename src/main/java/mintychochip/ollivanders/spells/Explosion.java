package mintychochip.ollivanders.spells;

import mintychochip.ollivanders.container.SpellMechanic;
import mintychochip.ollivanders.spells.shape.SpellArea;
import org.bukkit.Bukkit;
import org.bukkit.Location;

public class Explosion extends SpellMechanic implements SpellArea {
    @Override
    public boolean castArea() {
        Location castLocation = getCastLocation();
        double effectiveMagnitude = getEffectiveMagnitude();
        Bukkit.broadcastMessage(effectiveMagnitude + "");
        castLocation.getWorld().createExplosion(castLocation, (float) effectiveMagnitude);
        return true;
    }
}
