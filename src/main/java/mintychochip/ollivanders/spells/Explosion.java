package mintychochip.ollivanders.spells;

import mintychochip.ollivanders.container.WizardMechanic;
import mintychochip.ollivanders.shape.WizardAoe;
import mintychochip.ollivanders.shape.implementation.AoeImplementation;
import org.bukkit.Location;

public class Explosion extends WizardMechanic implements WizardAoe {


    @Override
    public boolean castAoe() {
        Location castLocation = ((AoeImplementation) getImplementation()).getCastLocation();
        if (castLocation.getWorld() == null) {
            return false;
        }
        castLocation.getWorld().createExplosion(castLocation, WizardModifier.getMagnitude());
        return true;
    }

}
