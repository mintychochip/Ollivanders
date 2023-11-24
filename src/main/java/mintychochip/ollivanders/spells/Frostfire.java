package mintychochip.ollivanders.spells;

import mintychochip.ollivanders.container.WizardMechanic;
import mintychochip.ollivanders.shape.WizardProjectile;
import mintychochip.ollivanders.shape.implementation.ProjectileImplementation;

public class Frostfire extends WizardMechanic implements WizardProjectile {
    @Override
    public int castProjectile() {
        ProjectileImplementation implementation = new ProjectileImplementation(this);
        return implementation.castProjectile();
    }

    @Override
    public void effect() {

    }

    @Override
    public void applyParticleProjectile() {

    }
}
