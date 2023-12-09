package mintychochip.ollivanders.spells;

import mintychochip.ollivanders.container.SpellMechanic;
import mintychochip.ollivanders.spells.shape.SpellProjectile;
import org.bukkit.Location;
import org.bukkit.Particle;

public class Fireball extends SpellMechanic implements SpellProjectile {
    @Override
    public void effectOnHit() {
        Location castLocation = getCastLocation();
        castLocation.getWorld().spawnParticle(Particle.LAVA,castLocation,5);
    }
}
