package mintychochip.ollivanders.listener;

import mintychochip.genesis.Genesis;
import mintychochip.ollivanders.api.EntitySpellDamageEvent;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class DamageListener implements Listener {

    @EventHandler
    private void preventDamageWhenImmune(final EntitySpellDamageEvent event) {
        Entity entity = event.getEntity();
        PersistentDataContainer persistentDataContainer = entity.getPersistentDataContainer();
        if(persistentDataContainer.has(Genesis.getKey("immune"), PersistentDataType.)) {
            event.setCancelled(true);
        }
    }
}
