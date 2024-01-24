package mintychochip.ollivanders.commands.book;

import mintychochip.ollivanders.commands.SubCommand;
import mintychochip.ollivanders.items.util.OllivandersSerializer;
import mintychochip.ollivanders.spellbook.BookData;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

public class SetBookDataSize extends SubCommand {

    public SetBookDataSize(String executor, String description) {
        super(executor, description);
    }

    @Override
    public boolean execute(String[] args, Player sender) {
        if(args.length < 2) {
            return false;
        }
        PlayerInventory inventory = sender.getInventory();
        ItemStack book = getBook(inventory.getItemInMainHand(), inventory.getItemInOffHand());
        if(book == null) {
            return false;
        }
        return setBookDataSize(book, Integer.parseInt(args[2]));
    }
    public ItemStack getBook(ItemStack main, ItemStack off) {
        if(isValidBook(main)) {
            return main;
        }
        if(isValidBook(off)) {
            return off;
        }
        return null;
    }
    public boolean isValidBook(ItemStack item) {
        if(item == null) {
            return false;
        }
        ItemMeta itemMeta = item.getItemMeta();
        if(itemMeta == null) {
            return false;
        }
        return itemMeta instanceof BookMeta;
    }
    public boolean setBookDataSize(ItemStack book, int size) {
        BookData bookData = OllivandersSerializer.extractBookData(book);
        if(bookData == null) {
            return false;
        }
        bookData.setSize(size).serialize(book);
        return true;
    }
}
