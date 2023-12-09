package mintychochip.ollivanders.spells.shape;

import mintychochip.ollivanders.container.MechanicModifier;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;

public interface SpellProjectile {

    abstract void effectOnHit();
    default int launchProjectile(EntityType entityType, Player player, MechanicModifier modifier) {
        Location playerLocation = player.getLocation();
        //injection
        if(playerLocation.getWorld() != null && entityType != null) {
            Projectile spawnedProjectile = (Projectile) playerLocation.getWorld().spawnEntity(playerLocation.add(playerLocation.getDirection().getX(),1f,playerLocation.getDirection().getZ()), entityType);

            spawnedProjectile.setVelocity(playerLocation.getDirection().multiply(modifier.getVelocity()));
            return spawnedProjectile.getEntityId();
        }
        return -1;
    }
}
