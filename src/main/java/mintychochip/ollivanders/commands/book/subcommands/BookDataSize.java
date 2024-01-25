package mintychochip.ollivanders.commands.book.subcommands;

import mintychochip.ollivanders.commands.abstraction.GenericCommandObject;
import mintychochip.ollivanders.commands.abstraction.SubCommand;
import mintychochip.ollivanders.items.util.OllivandersSerializer;
import mintychochip.ollivanders.spellbook.BookData;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.ItemMeta;

public class BookDataSize extends BookDataCommand implements SubCommand {

    public BookDataSize(String executor, String description) {
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
        return setBookDataSize(book, Integer.parseInt(args[depth - 1]));
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
