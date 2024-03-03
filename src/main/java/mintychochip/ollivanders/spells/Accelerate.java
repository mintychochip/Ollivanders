package mintychochip.ollivanders.spells;

import mintychochip.genesis.util.MathUtil;
import mintychochip.ollivanders.Ollivanders;
import mintychochip.ollivanders.container.SpellMechanic;
import mintychochip.ollivanders.spells.shape.SpellArea;
import mintychochip.ollivanders.spells.shape.SpellSelf;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class Accelerate extends SpellMechanic implements SpellSelf, SpellArea {
    @Override
    public boolean castArea() {
        return false;
    }

    @Override
    public boolean castSelf() {
        final Player player = context.getPlayer();

        Location location = player.getLocation();
        Entity entity = location.getWorld().spawnEntity(location, EntityType.FIREBALL);
        new BukkitRunnable() {
            final Location loc = entity.getLocation();
            double t = 0;
            final double r = 2;

            public void run() {
                t = t + Math.PI / 16;
                double x = r * Math.cos(t);
                double y = r * Math.sin(t) + 1;
                double z = r;

                Vector vector = new Vector(x, y, z);
                vector = MathUtil.rotateFunction(vector, loc);
                loc.getWorld().spawnParticle(Particle.FLAME, loc.add(vector), 1, 0, 0, 0, 0, null, true);
                loc.subtract(vector);
                if (t > Math.PI * 52) {
                    this.cancel();
                }
            }
        }.runTaskTimer(Ollivanders.getInstance(), 0, 1);
        return true;
    }
}
