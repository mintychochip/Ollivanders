package mintychochip.ollivanders.shape.implementation;

import mintychochip.ollivanders.betterwand.WandBoost;
import mintychochip.ollivanders.container.WizardMechanic;
import mintychochip.ollivanders.shape.WizardSelf;

public class SelfImplementation extends Implementation implements WizardSelf {

    public SelfImplementation(WizardMechanic mechanic, WandBoost wandBoost) {
        super(wandBoost, mechanic);
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
