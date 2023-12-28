package mintychochip.ollivanders.spells;

import mintychochip.genesis.Genesis;
import mintychochip.ollivanders.Ollivanders;
import mintychochip.ollivanders.container.SpellMechanic;
import mintychochip.ollivanders.spells.shape.SpellArea;
import mintychochip.ollivanders.spells.shape.SpellSelf;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.Particle;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.List;

public class Immolate extends SpellMechanic implements SpellArea, SpellSelf {
    @Override
    public boolean castArea() {
        return genericCastMethod();
    }

    @Override
    public boolean castSelf() {
        return genericCastMethod();
    }

    public boolean genericCastMethod() {
        for (LivingEntity nearbyLivingEntity : getNearbyLivingEntities()) { //should just remove the player originally anyway
            int ceil = (int) Math.ceil(getEffectiveDuration());
            NamespacedKey fire = Genesis.getKeys().getMap().get("fire");
            SpellHelper.updateTimer(fire,nearbyLivingEntity,ceil);
            if (!Ollivanders.getSpellDamageHandler().getImmolate().containsKey(nearbyLivingEntity)) {
                Ollivanders.getSpellDamageHandler().getImmolate().put(nearbyLivingEntity,this); //can probs use the settings though
            }
        }
        return true;
    }
}
