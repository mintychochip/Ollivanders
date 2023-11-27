package mintychochip.ollivanders.spells;

import mintychochip.ollivanders.container.WizardMechanic;
import mintychochip.ollivanders.shape.WizardAoe;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class GravityWell extends WizardMechanic implements WizardAoe {
    @Override
    public boolean castAoe() {
        for (Entity nearbyLivingEntity : implementation.getNearbyEntities()) {
            if(!(nearbyLivingEntity == context.getPlayer())) {
                Vector subtract = nearbyLivingEntity.getLocation().toVector();
                Vector vector = implementation.getCastLocation().toVector();
                Vector subtract1 = vector.subtract(subtract);
                nearbyLivingEntity.setVelocity(subtract1.normalize());
            }
        }
        return true;
    }
}
