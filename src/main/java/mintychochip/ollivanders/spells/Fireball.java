package mintychochip.ollivanders.spells;

import mintychochip.ollivanders.Ollivanders;
import mintychochip.ollivanders.container.SpellMechanic;
import mintychochip.ollivanders.spells.shape.SpellProjectile;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.scheduler.BukkitRunnable;

public class Fireball extends SpellMechanic implements SpellProjectile {
    @Override
    public void effectOnHit() {
        Location castLocation = getCastLocation();
        castLocation.getWorld().spawnParticle(Particle.LAVA, castLocation, 5);
    }
}
