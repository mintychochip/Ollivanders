package mintychochip.orchid.shape.implementation;

import mintychochip.orchid.Orchid;
import mintychochip.orchid.container.OrchidMechanic;
import mintychochip.orchid.shape.OrchidLaser;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Snowball;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.BlockIterator;
import org.bukkit.util.Vector;

public class LaserImplementation extends Implementation implements OrchidLaser {



    public LaserImplementation(OrchidMechanic mechanic) {
        super(mechanic);
    }

    @Override
    public int castLaser() {
        BlockIterator blockIterator = new BlockIterator(player.getEyeLocation(),0D,30);
        Snowball projectile = player.launchProjectile(Snowball.class);
        projectile.setVelocity(player.getLocation().getDirection().multiply(3));
        projectile.setGravity(false);
        float rotationSpeed = 120;
        final double INCREMENT = (2 * Math.PI) / rotationSpeed;

        double circlePointOffset = 0;

        Location lastLocation = player.getEyeLocation();
        new BukkitRunnable() {
            double circlePointOffset = 0;
            float radius = 1f;
            Location lastLocation = player.getEyeLocation();
            @Override
            public void run() {
                if(projectile.isDead() || projectile.isOnGround()) {
                    this.cancel();
                    return;
                }
                Location startLoc = projectile.getLocation();
                startLoc.setDirection(projectile.getVelocity());

                double distance = Math.abs(startLoc.distance(lastLocation));
                double
                        pitch = (90 + startLoc.getPitch()) * 0.017453292F,
                        yaw = -startLoc.getYaw() * 0.017453292F;
                float spacing = 0.1f;

                for (double i = 0; i <= distance; i+=spacing) {
                    double x =  radius * Math.cos(2 * Math.PI + circlePointOffset);
                    double z =  radius * Math.sin(2 * Math.PI + circlePointOffset);

//                    if(reverse) {
//                        z = -z;
//                        x = -x;
//                    }

                    Vector vec = new Vector(x, i, z);
                    Vector inverseVec = new Vector(-x,i,-z);
                    vec.rotateAroundX(pitch);
                    vec.rotateAroundY(yaw);
                    inverseVec.rotateAroundX(pitch);
                    inverseVec.rotateAroundY(yaw);
                    Location add1 = startLoc.clone().add(vec);
                    Location add = startLoc.clone().add(inverseVec);
                    float random = 0.03f;
                    add1.getWorld().spawnParticle(Particle.SOUL_FIRE_FLAME,add1,1,0,random,random,random,null,true);
                    add.getWorld().spawnParticle(Particle.SOUL_FIRE_FLAME,add,1,0,random,random,random,null,true);



                    circlePointOffset += INCREMENT;
                    if (circlePointOffset >= (2 * Math.PI)) {
                        circlePointOffset = 0;
                    }
                }

                lastLocation = startLoc;
            }
        }.runTaskTimer(Orchid.getInstance(),2L,2L);
        return projectile.getEntityId();
    }
}
