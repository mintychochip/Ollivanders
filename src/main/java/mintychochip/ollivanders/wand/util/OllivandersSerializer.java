package mintychochip.ollivanders.wand.util;

import mintychochip.genesis.Genesis;
import mintychochip.genesis.util.Serializer;
import mintychochip.ollivanders.spellbook.SpellBook;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.io.IOException;

public class OllivandersSerializer { //migrate this somewhere else

    public static SpellBook extractBookData(ItemStack itemStack) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        if(itemMeta instanceof BookMeta bookMeta) {
            return extractBookData(bookMeta);
        }
        return null;
    }
    public static SpellBook extractBookData(BookMeta bookMeta) {
        PersistentDataContainer persistentDataContainer = bookMeta.getPersistentDataContainer();
        NamespacedKey namespacedKey = Genesis.getKey("book");
        if(persistentDataContainer.has(namespacedKey, PersistentDataType.BYTE_ARRAY)) {
            byte[] bytes = persistentDataContainer.get(namespacedKey, PersistentDataType.BYTE_ARRAY);
            if(bytes != null) {
                try {
                    return (SpellBook) Serializer.deserialize(bytes);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return null;
    }
}
