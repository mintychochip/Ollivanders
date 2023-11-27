package mintychochip.ollivanders.spells;

import mintychochip.ollivanders.container.WizardMechanic;
import mintychochip.ollivanders.shape.WizardAoe;
import org.bukkit.Location;

public class Explosion extends WizardMechanic implements WizardAoe {


    @Override
    public boolean castAoe() {
        Location castLocation = getImplementation().getCastLocation();
        if (castLocation.getWorld() == null) {
            return false;
        }
        castLocation.getWorld().createExplosion(castLocation, (float) (wizardModifier.getMagnitude() * wandBoost.getPower()));
        return true;
    }

}
