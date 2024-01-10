package mintychochip.ollivanders.spells;

import mintychochip.ollivanders.container.SpellMechanic;
import mintychochip.ollivanders.spells.shape.SpellArea;
import mintychochip.ollivanders.spells.shape.SpellSelf;
import mintychochip.ollivanders.spells.shape.SpellSight;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;

public class GravityWell extends SpellMechanic implements SpellArea, SpellSelf, SpellSight {


    @Override
    public boolean castArea() {
        for (LivingEntity nearbyLivingEntity : nearbyLivingEntities(true)) {
            Vector subtract = originalCastLocation.toVector().subtract(nearbyLivingEntity.getLocation().toVector());
            if (!subtract.isZero()) {
                nearbyLivingEntity.setVelocity(subtract.normalize());
            }
        }
        return true;
    }

    @Override
    public boolean castSelf() {
        Player player = context.getPlayer();

        RayTraceResult rayTraceResult = player.getWorld().rayTraceEntities(player.getEyeLocation().add(player.getLocation().getDirection()),
                player.getEyeLocation().getDirection(), effectiveRange(),
                3,
                entity -> !entity.getUniqueId().equals(player.getUniqueId()));
        if (rayTraceResult == null) {
            return false;
        }
        Entity hitEntity = rayTraceResult.getHitEntity();
        Bukkit.broadcastMessage(effectiveRange() + "");
        if (hitEntity != null && hitEntity != context.getPlayer()) {
            hitEntity.teleport(player.getLocation());
            return true;
        }
        return false;
    }
}
