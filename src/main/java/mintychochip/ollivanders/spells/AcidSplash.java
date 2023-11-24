package mintychochip.ollivanders.spells;

import mintychochip.ollivanders.container.WizardMechanic;
import mintychochip.ollivanders.shape.WizardProjectile;
import mintychochip.ollivanders.shape.implementation.ProjectileImplementation;

public class AcidSplash extends WizardMechanic implements WizardProjectile {
    @Override
    public int castProjectile() {
        ProjectileImplementation implementation = (ProjectileImplementation) getImplementation();
        return 0;
    }

    @Override
    public void effect() {

    }

    @Override
    public void applyParticleProjectile() {

    }
}
