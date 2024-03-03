package mintychochip.ollivanders.items.util;

import com.google.gson.Gson;
import mintychochip.genesis.Genesis;
import mintychochip.genesis.container.items.interfaces.Embeddable;
import mintychochip.genesis.util.Serializer;
import mintychochip.ollivanders.items.config.ComponentRegistry;
import mintychochip.ollivanders.items.container.ComponentData;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.io.IOException;

public class ComponentUtil {
    public static ComponentData componentDataFromItemStack(ItemStack itemStack) {
        PersistentDataContainer persistentDataContainer = itemStack.getItemMeta().getPersistentDataContainer();
        ComponentData componentData = null;
        NamespacedKey component = Genesis.getKey("component");
        if (persistentDataContainer.has(component, PersistentDataType.STRING)) {
            componentData = new Gson().fromJson(persistentDataContainer.get(component,PersistentDataType.STRING),ComponentData.class);
        } else {
            componentData = ComponentRegistry.getComponentData().get(itemStack.getType());
        }
        Bukkit.broadcastMessage(componentData.toString());
        return componentData;
    }
}
