package mintychochip.ollivanders.items.util;

import com.google.gson.Gson;
import mintychochip.genesis.Genesis;
import mintychochip.genesis.util.Serializer;
import mintychochip.ollivanders.items.container.spellbook.BookData;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.io.IOException;

public class OllivandersSerializer { //migrate this somewhere else

    public static BookData extractBookData(ItemStack itemStack) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta instanceof BookMeta bookMeta) {
            return extractBookData(bookMeta);
        }
        return null;
    }

    public static BookData extractBookData(BookMeta bookMeta) {
        PersistentDataContainer persistentDataContainer = bookMeta.getPersistentDataContainer();
        NamespacedKey namespacedKey = Genesis.getKey("book");
        if (persistentDataContainer.has(namespacedKey, PersistentDataType.STRING)) {
            String json = persistentDataContainer.get(namespacedKey, PersistentDataType.STRING);
            return new Gson().fromJson(json, BookData.class);
        }
        return null;
    }
}
