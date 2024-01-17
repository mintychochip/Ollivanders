package mintychochip.ollivanders.listener;

import mintychochip.genesis.Genesis;
import mintychochip.ollivanders.Ollivanders;
import mintychochip.ollivanders.spellbook.SpellBook;
import mintychochip.ollivanders.wand.util.OllivandersSerializer;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerEditBookEvent;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.Map;

public class OllivandersItemListener implements Listener {

    
    @EventHandler
    public void onPlayerCreateBookOverLimit(final PlayerEditBookEvent event) {
        BookMeta previousBookMeta = event.getPreviousBookMeta();
        PersistentDataContainer persistentDataContainer = previousBookMeta.getPersistentDataContainer();
        if(persistentDataContainer.has(Genesis.getKey("book"),PersistentDataType.BYTE_ARRAY)) {
            SpellBook spellBook = OllivandersSerializer.extractBookData(event.getPreviousBookMeta());
            if(spellBook == null) {
                return;
            }
            if(event.getNewBookMeta().getPageCount() > spellBook.getSize()) {
                event.setCancelled(true);
            }
        }
    }
}
