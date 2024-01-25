package mintychochip.ollivanders.commands.book.subcommands;

import mintychochip.ollivanders.commands.abstraction.SubCommand;
import mintychochip.ollivanders.items.util.OllivandersSerializer;
import mintychochip.ollivanders.spellbook.BookData;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class BookDataCurrentPage extends BookDataCommand implements SubCommand {
    public BookDataCurrentPage(String executor, String description) {
        super(executor, description);
    }

    @Override
    public boolean execute(String[] args, Player sender) {
       if(args.length < depth) {
           return false;
       }
        PlayerInventory inventory = sender.getInventory();
        ItemStack book = getBook(inventory.getItemInMainHand(), inventory.getItemInOffHand());
        if(book == null) {
            return false;
        }
        return setBookCurrentPage(book,Integer.parseInt(args[depth - 1]));
    }
    public boolean setBookCurrentPage(ItemStack book, int currentPage) {
        BookData bookData = OllivandersSerializer.extractBookData(book);
        if(bookData == null) {
            return false;
        }
        bookData.setCurrentPage(currentPage).serialize(book);
        return true;
    }
}
