package mintychochip.ollivanders.spells;

import mintychochip.ollivanders.container.WizardMechanic;
import mintychochip.ollivanders.shape.WizardAoe;
import mintychochip.ollivanders.shape.WizardSelf;
import mintychochip.ollivanders.shape.implementation.AoeImplementation;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.List;

public class Ablaze extends WizardMechanic implements WizardAoe, WizardSelf {
    @Override
    public boolean castAoe() {
        List<Entity> nearbyEntities = getImplementation().getNearbyEntities();
        if (nearbyEntities == null) {
            return false;
        }
        for (Entity nearbyEntity : nearbyEntities) {
            if (nearbyEntity instanceof Player player) {
                if (player.getFireTicks() > 0) {
                    //add potion effects
                }
            }
        }
        return true;
    }

    @Override
    public boolean castSelf() {
        //add potion effects
        return context.getPlayer().getFireTicks() > 0;
    }

    @Override
    public void applyParticleSelf() {

    }
}
