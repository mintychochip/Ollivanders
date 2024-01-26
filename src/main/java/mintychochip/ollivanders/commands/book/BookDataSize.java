package mintychochip.ollivanders.commands.book;

import mintychochip.genesis.commands.abstraction.SubCommand;
import mintychochip.ollivanders.items.util.OllivandersSerializer;
import mintychochip.ollivanders.spellbook.BookData;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class BookDataSize extends BookDataCommand implements SubCommand {

    public BookDataSize(String executor, String description) {
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
        int i = Integer.parseInt(args[depth - 1]);
        if(i < 1 || i > 54) {
            Bukkit.broadcastMessage(ChatColor.RED + "Invalid slot size!");
            return false;
        }
        return setBookDataSize(book,i);
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
