package mintychochip.ollivanders.api;

import org.bukkit.Location;
import org.bukkit.entity.Entity;

import java.util.List;

public class SpellExplosionEvent extends OllivandersEvent {

    private Location explosionLocation;

    private List<Entity> affectedEntities;

    public SpellExplosionEvent(Location explosionLocation, float magnitude) {
        this.explosionLocation = explosionLocation;
        if(explosionLocation != null) {
            if(explosionLocation.getWorld() != null) {
                explosionLocation.getWorld().createExplosion(explosionLocation,magnitude);

            }
        }

    }

    public Location getExplosionLocation() {
        return explosionLocation;
    }
}
