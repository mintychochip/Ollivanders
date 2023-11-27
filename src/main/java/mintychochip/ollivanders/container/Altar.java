package mintychochip.ollivanders.container;

import org.bukkit.Location;

public class Altar {

    private final Location location;

    private boolean active;

    public Altar(Location location) {
        this.location = location;
    }

    public Location getLocation() {
        return location;
    }
}
