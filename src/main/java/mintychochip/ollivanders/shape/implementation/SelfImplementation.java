package mintychochip.ollivanders.shape.implementation;

import mintychochip.ollivanders.container.WizardMechanic;
import mintychochip.ollivanders.shape.WizardSelf;

import java.util.ArrayList;

public class SelfImplementation extends Implementation implements WizardSelf {

    public SelfImplementation(WizardMechanic mechanic) {
        super(mechanic);
    }

    @Override
    public boolean castSelf() {
        if (!(mechanic instanceof WizardSelf self)) {
            return false;
        }
        return self.castSelf();
    }

    @Override
    public void applyParticleSelf() {

    }

}
