package mintychochip.ollivanders.spells;

import mintychochip.ollivanders.container.WizardMechanic;
import mintychochip.ollivanders.shape.WizardAoe;
import mintychochip.ollivanders.shape.WizardSelf;
import org.bukkit.Location;

public class Extinguish extends WizardMechanic implements WizardAoe, WizardSelf {
    @Override
    public boolean castAoe() {
        return false;
    }

    @Override
    public boolean castSelf() {
        Location castLocation = context.getPlayer().getLocation();
        return castLocation != null;
    }

    @Override
    public void applyParticleSelf() {

    }
}
