package mintychochip.ollivanders.commands.book;

import mintychochip.genesis.commands.abstraction.GenericCommandObject;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.ItemMeta;

public class BookDataCommand extends GenericCommandObject {
    public BookDataCommand(String executor, String description) {
        super(executor, description);
    }

    public ItemStack getBook(ItemStack main, ItemStack off) {
        if (isValidBook(main)) {
            return main;
        }
        if (isValidBook(off)) {
            return off;
        }
        return null;
    }

    public boolean isValidBook(ItemStack item) {
        if (item == null) {
            return false;
        }
        ItemMeta itemMeta = item.getItemMeta();
        if (itemMeta == null) {
            return false;
        }
        return itemMeta instanceof BookMeta;
    }
}
