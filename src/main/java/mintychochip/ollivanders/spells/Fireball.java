package mintychochip.ollivanders.spells;

import mintychochip.ollivanders.container.WizardMechanic;
import mintychochip.ollivanders.shape.WizardProjectile;

public class Fireball extends WizardMechanic implements WizardProjectile {


    @Override
    public int castProjectile() {
        //all fireball code is handled by the implementation
        return 0;
    }

    @Override
    public void effect() {
        //lava impact whne it lands
    }

    @Override
    public void applyParticleProjectile() {
        //add back the lava line projectile effect
    }
}
