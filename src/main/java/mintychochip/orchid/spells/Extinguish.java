package mintychochip.orchid.spells;

import mintychochip.orchid.container.OrchidMechanic;
import mintychochip.orchid.shape.OrchidAoe;
import mintychochip.orchid.shape.OrchidSelf;
import org.bukkit.Location;

public class Extinguish extends OrchidMechanic implements OrchidAoe, OrchidSelf {
    @Override
    public boolean castAoe() {
        return false;
    }

    @Override
    public boolean castSelf() {
        Location castLocation = context.getPlayer().getLocation();
        if(castLocation == null) {
            return false;
        }
        return true;
    }

    @Override
    public void applyParticleSelf() {

    }
}
