package mintychochip.ollivanders.spells;

import mintychochip.ollivanders.container.SpellMechanic;
import mintychochip.ollivanders.spells.shape.SpellProjectile;
import org.bukkit.Particle;

public class Fireball extends SpellMechanic implements SpellProjectile {
    @Override
    public void effectOnHit() {
        originalCastLocation.getWorld().spawnParticle(Particle.LAVA, originalCastLocation, 5);
    }
}
