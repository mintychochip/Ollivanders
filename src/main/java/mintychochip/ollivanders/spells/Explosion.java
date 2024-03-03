package mintychochip.ollivanders.spells;

import mintychochip.ollivanders.api.SpellExplosionEvent;
import mintychochip.ollivanders.container.SpellMechanic;
import mintychochip.ollivanders.spells.shape.SpellArea;
import mintychochip.ollivanders.spells.shape.SpellSelf;
import org.bukkit.Bukkit;
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
        if (location != null) {
            originalCastLocation = location;
        }
        if (originalCastLocation == null) {
            return false;
        }
        Bukkit.getPluginManager().callEvent(new SpellExplosionEvent(originalCastLocation, (float) this.effectiveMagnitude()));
        return true;
    }
}
