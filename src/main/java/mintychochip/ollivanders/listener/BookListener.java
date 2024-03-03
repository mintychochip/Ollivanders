package mintychochip.ollivanders.listener;

import mintychochip.genesis.Genesis;
import mintychochip.ollivanders.items.util.OllivandersSerializer;
import mintychochip.ollivanders.items.container.spellbook.BookData;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerEditBookEvent;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class BookListener implements Listener {


    @EventHandler
    public void onPlayerCreateBookOverLimit(final PlayerEditBookEvent event) {
        BookMeta previousBookMeta = event.getPreviousBookMeta();
        PersistentDataContainer persistentDataContainer = previousBookMeta.getPersistentDataContainer();
        if (persistentDataContainer.has(Genesis.getKey("book"), PersistentDataType.STRING)) {
            BookData bookData = OllivandersSerializer.extractBookData(event.getPreviousBookMeta());
            if (bookData == null) {
                return;
            }
            BookMeta newBookMeta = event.getNewBookMeta();
            if (spellBookIsOverLimit(newBookMeta.getPageCount(), bookData.getSize())) {
                event.setCancelled(true);
                event.getPlayer().sendMessage("The book has been reverted, you went over the spell book size limit.");
            }
            if (spellBookWasSigned(newBookMeta.getAuthor())) {
                event.setCancelled(true);
                event.getPlayer().sendMessage(ChatColor.RED + "You cannot sign spell books!");
            }
        }
    }

    private boolean spellBookIsOverLimit(int currentSize, int size) {
        return currentSize > size;
    }

    public boolean spellBookWasSigned(String author) {
        return author != null;
    }
}
