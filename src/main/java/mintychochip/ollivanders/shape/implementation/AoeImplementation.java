package mintychochip.ollivanders.shape.implementation;

import mintychochip.ollivanders.container.WizardMechanic;
import mintychochip.ollivanders.shape.WizardAoe;
import org.bukkit.Location;
import org.bukkit.entity.Entity;

import java.util.ArrayList;
import java.util.List;

public class AoeImplementation extends Implementation implements WizardAoe {
    //probably want a method to collect entities within the range of the mechanic

    public AoeImplementation(WizardMechanic mechanic) {
        super(mechanic);
    }

    @Override
    public boolean castAoe() {
        if (!(mechanic instanceof WizardAoe aoe)) {
            return false;
        }
        return aoe.castAoe();
    }

    public Location getCastLocation() {
        return castLocation;
    }

    public List<Entity> getNearbyEntities() {
        return nearbyEntities;
    }
}
