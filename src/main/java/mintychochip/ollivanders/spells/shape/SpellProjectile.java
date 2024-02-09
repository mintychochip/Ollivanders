package mintychochip.ollivanders.spells.shape;

import mintychochip.ollivanders.container.MechanicModifier;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.util.Vector;

public interface SpellProjectile {

    abstract void effectOnHit();
    default Entity launchProjectile(EntityType entityType, Player player, MechanicModifier modifier) {
        Location playerLocation = player.getLocation();
        //injection
        if(playerLocation.getWorld() != null && entityType != null) {
            Projectile spawnedProjectile = (Projectile) playerLocation.getWorld().spawnEntity(playerLocation.add(playerLocation.getDirection().getX(),1f,playerLocation.getDirection().getZ()), entityType);
            float velocity = 1;
            spawnedProjectile.getLocation().setDirection(playerLocation.getDirection());
            return spawnedProjectile;
        }
        return null;
    }
}
