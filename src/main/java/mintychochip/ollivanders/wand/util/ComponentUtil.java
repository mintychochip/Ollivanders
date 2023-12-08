package mintychochip.ollivanders.wand.util;

import mintychochip.genesis.Genesis;
import mintychochip.ollivanders.wand.Serializer;
import mintychochip.ollivanders.wand.config.ComponentRegistry;
import mintychochip.ollivanders.wand.container.ComponentData;
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
