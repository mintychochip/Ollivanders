package mintychochip.ollivanders.spells;

import mintychochip.ollivanders.container.WizardMechanic;
import mintychochip.ollivanders.shape.WizardSelf;
import mintychochip.ollivanders.shape.implementation.Implementation;
import mintychochip.ollivanders.shape.implementation.SelfImplementation;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;

import java.util.List;

public class FireShield extends WizardMechanic implements WizardSelf {
    @Override
    public boolean castSelf() {
        for (LivingEntity nearbyLivingEntity : implementation.getNearbyLivingEntities()) {
            nearbyLivingEntity.damage(mechanicSettings.getDamage());
            nearbyLivingEntity.setVisualFire(false);
        }
        return true;
    }

    @Override
    public void applyParticleSelf() {

    }
}
