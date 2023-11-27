package mintychochip.ollivanders.shape.implementation;

import mintychochip.ollivanders.betterwand.WandBoost;
import mintychochip.ollivanders.container.Context;
import mintychochip.ollivanders.container.WizardMechanic;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public abstract class Implementation {
    protected final WandBoost wandBoost;
    protected final WizardMechanic mechanic; // can generalize this, make a base class
    protected Context context;
    protected Player player;
    protected Location castLocation;
    protected List<Entity> nearbyEntities;
    protected List<LivingEntity> nearbyLivingEntities;
    protected Implementation(WandBoost wandBoost, WizardMechanic mechanic) { //dont need wandBoost
        this.wandBoost = wandBoost;
        this.mechanic = mechanic;
        Context context = null;
        if (mechanic != null) {
            mechanic.setImplementation(this);
            context = mechanic.getContext();
        }
        if (context != null) {
            this.context = context;
            this.player = context.getPlayer();
        }
        castLocation = context.getHitLocation() != null ? context.getHitLocation() : player.getLocation();
        if (castLocation != null) {
            nearbyEntities = new ArrayList<>();
            double range = mechanic.getMechanicSettings().getRange() * mechanic.getWandBoost().getRange() / 2f;
            if(mechanic.getWandBoost().getRange() < 0) {
                range = 0;
            }
            if (castLocation.getWorld() != null) {
                nearbyEntities.addAll(castLocation.getWorld().getNearbyEntities(castLocation, range, range, range));
            }
        }
        for (Entity nearbyEntity : nearbyEntities) {
            if(nearbyEntity instanceof LivingEntity) {
                if(nearbyLivingEntities == null) {
                    nearbyLivingEntities = new ArrayList<>();
                }
                nearbyLivingEntities.add((LivingEntity) nearbyEntity);
            }
        }

    }

    public Location getCastLocation() {
        return castLocation;
    }

    public Context getContext() {
        return context;
    }

    public Player getPlayer() {
        return player;
    }

    public List<Entity> getNearbyEntities() {
        return nearbyEntities;
    }

    public WizardMechanic getMechanic() {
        return mechanic;
    }

    public List<LivingEntity> getNearbyLivingEntities() {
        return nearbyLivingEntities;
    }
}
