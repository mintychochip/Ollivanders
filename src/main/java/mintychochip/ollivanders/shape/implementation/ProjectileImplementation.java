package mintychochip.ollivanders.shape.implementation;

import mintychochip.ollivanders.betterwand.WandBoost;
import mintychochip.ollivanders.container.WizardMechanic;
import mintychochip.ollivanders.registry.WizardRegistry;
import mintychochip.ollivanders.shape.WizardProjectile;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Projectile;

public class ProjectileImplementation extends Implementation implements WizardProjectile {
    private Projectile spawnedProjectile;
    private EntityType type;

    public ProjectileImplementation(WizardMechanic mechanic, WandBoost wandBoost) {
        super(wandBoost, mechanic);
        String name = mechanic.getName();
        if(name != null) {
            type = WizardRegistry.getProjectileAlias().get(name);
        }
    }


    @Override
    public int castProjectile() { //generalized method to cast projectile objects
        Location playerLocation = player.getLocation();
        //injection
        if(playerLocation.getWorld() != null && type != null) {
            spawnedProjectile = (Projectile) playerLocation.getWorld().spawnEntity(playerLocation.add(playerLocation.getDirection().getX(),1f,playerLocation.getDirection().getZ()), type); //cant have errors at this line, because type is pulled from hash
            spawnedProjectile.setVelocity(playerLocation.getDirection().multiply(mechanic.getWizardModifier().getVelocity()));
            return spawnedProjectile.getEntityId();
        }
        return -1;
    }

    @Override
    public void effect() {
        if(mechanic instanceof WizardProjectile projectile) {
            projectile.effect();
        }
    }

    @Override
    public void applyParticleProjectile() {
        if(spawnedProjectile != null) {//add this projectile, and give it effect, probs hash
        }
    }

    public EntityType getType() {
        return type;
    }

    public Projectile getSpawnedProjectile() {
        return spawnedProjectile;
    }
}
