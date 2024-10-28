package mintychochip.ollivanders.spells;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.LivingEntity;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class SpellHelper {


    public static void updateTimer(NamespacedKey key, LivingEntity nearbyLivingEntity, int add) {
        Long l = 0L;
        PersistentDataContainer persistentDataContainer = nearbyLivingEntity.getPersistentDataContainer();
        if (persistentDataContainer.has(key, PersistentDataType.LONG)) {
            l = persistentDataContainer.get(key, PersistentDataType.LONG);
        }
        l += add;
        persistentDataContainer.set(key, PersistentDataType.LONG, l);
    }


}
