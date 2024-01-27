package mintychochip.ollivanders.commands.book;

import mintychochip.genesis.commands.abstraction.SubCommand;
import mintychochip.ollivanders.items.util.OllivandersSerializer;
import mintychochip.ollivanders.spellbook.BookData;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class CurrentSlot extends BookDataCommand implements SubCommand {
    public CurrentSlot(String executor, String description) {
        super(executor, description);
    }

    @Override
    public boolean execute(String[] strings, Player sender) {
       if(strings.length < depth) {
           Bukkit.broadcastMessage("book was not able to be set");
           return false;
       }
        PlayerInventory inventory = sender.getInventory();
        ItemStack book = getBook(inventory.getItemInMainHand(), inventory.getItemInOffHand());
        if(book == null) {
            return false;
        }
        return setBookCurrentPage(book,Integer.parseInt(strings[depth - 1]) - 1);
    }
    public boolean setBookCurrentPage(ItemStack book, int currentPage) {
        BookData bookData = OllivandersSerializer.extractBookData(book);
        if(bookData == null) {
            return false;
        }
        bookData.setCurrentPage(bookData.getMappings().get(currentPage));
        bookData.serialize(book);
        return true;
    }
}
