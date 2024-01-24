package mintychochip.ollivanders.listener;

import mintychochip.genesis.Genesis;
import mintychochip.ollivanders.spellbook.BookData;
import mintychochip.ollivanders.items.util.OllivandersSerializer;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerEditBookEvent;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.entity.Player;

public class BookListener implements Listener {

    
    @EventHandler
    public void onPlayerCreateBookOverLimit(final PlayerEditBookEvent event) {
        BookMeta previousBookMeta = event.getPreviousBookMeta();
        PersistentDataContainer persistentDataContainer = previousBookMeta.getPersistentDataContainer();
        if(persistentDataContainer.has(Genesis.getKey("book"),PersistentDataType.BYTE_ARRAY)) {
            BookData bookData = OllivandersSerializer.extractBookData(event.getPreviousBookMeta());
            if(bookData == null) {
                return;
            }
            BookMeta newBookMeta = event.getNewBookMeta();
            if(spellBookIsOverLimit(newBookMeta.getPageCount(),bookData.getSize())) {
                event.setCancelled(true);
                event.getPlayer().sendMessage("The book has been reverted, you went over the spell book size limit.");
            }
            if(spellBookWasSigned(newBookMeta.getAuthor())) {
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
