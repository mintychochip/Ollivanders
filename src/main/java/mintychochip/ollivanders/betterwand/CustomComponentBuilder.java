package mintychochip.ollivanders.betterwand;

import mintychochip.ollivanders.util.Keys;
import mintychochip.ollivanders.util.Serializer;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.io.IOException;

public class CustomComponentBuilder {

    private final ItemStack itemStack;

    public CustomComponentBuilder(Material material) {
        itemStack = new ItemStack(material);

    }

    public CustomComponentBuilder setCore(Core core) {
        itemStack.getItemMeta().getPersistentDataContainer().set(Keys.getCore(), PersistentDataType.STRING, core.toString());
        return this;
    }

    public CustomComponentBuilder setBoost(WandBoost boost) {
        if (itemStack.getItemMeta() != null) {
            PersistentDataContainer persistentDataContainer = itemStack.getItemMeta().getPersistentDataContainer();
            try {
                persistentDataContainer.set(Keys.getBoost(), PersistentDataType.BYTE_ARRAY, Serializer.serializeObject(boost));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return this;
    }

    public ItemStack build() {
        return itemStack;
    }
}
