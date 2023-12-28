package mintychochip.ollivanders.spells;

import mintychochip.ollivanders.api.SpellDamageEvent;
import mintychochip.ollivanders.container.SpellMechanic;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class SpellHelper {

    public static void inflictSpellDamage(LivingEntity inflicted, SpellMechanic mechanic, double damage) {
        Bukkit.getPluginManager().callEvent(new SpellDamageEvent(inflicted,mechanic,damage));

        inflicted.setHealth(Math.abs(inflicted.getHealth() - damage));
        if(inflicted.getHealth() <= 0) {
            inflicted.setVisualFire(false);
        }
    }
    public static void updateTimer(NamespacedKey key, LivingEntity nearbyLivingEntity,int add) {
        Long l = 0L;
        PersistentDataContainer persistentDataContainer = nearbyLivingEntity.getPersistentDataContainer();
        if(persistentDataContainer.has(key, PersistentDataType.LONG)) {
            l = persistentDataContainer.get(key, PersistentDataType.LONG);
        }
        l += add;
        persistentDataContainer.set(key,PersistentDataType.LONG,l);
    }
}
