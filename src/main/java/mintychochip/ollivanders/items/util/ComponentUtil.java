package mintychochip.ollivanders.items.util;

import mintychochip.genesis.Genesis;
import mintychochip.genesis.util.Serializer;
import mintychochip.ollivanders.items.config.ComponentRegistry;
import mintychochip.ollivanders.items.container.ComponentData;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.io.IOException;

public class ComponentUtil {
    public static ComponentData componentDataFromItemStack(ItemStack itemStack) {
        PersistentDataContainer persistentDataContainer = itemStack.getItemMeta().getPersistentDataContainer();
        ComponentData componentData;
        if (persistentDataContainer.has(Genesis.getKeys().getMap().get("items"), PersistentDataType.BYTE_ARRAY)) {
            try {
                componentData = (ComponentData) Serializer.deserialize(persistentDataContainer.get(Genesis.getKeys().getMap().get("items"), PersistentDataType.BYTE_ARRAY));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            componentData = ComponentRegistry.getComponentData().get(itemStack.getType());
        }
        return componentData;
    }
}
