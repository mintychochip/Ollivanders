package mintychochip.ollivanders.listener;

import mintychochip.genesis.Genesis;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.Map;

public class OllivandersItemListener implements Listener {

    @EventHandler
    public void onPlayerDropOllivandersItem(final PlayerDropItemEvent event) {
        Item itemDrop = event.getItemDrop();
        ItemMeta itemMeta = itemDrop.getItemStack().getItemMeta();
        if (itemMeta == null) {
            return;
        }
        Map<String, NamespacedKey> map = Genesis.getKeys().getMap();
        if (itemMeta.getPersistentDataContainer().has(map.get("wand"), PersistentDataType.BYTE_ARRAY)
                || itemMeta.getPersistentDataContainer().has(map.get("items"), PersistentDataType.BYTE_ARRAY)) {
            itemDrop.setCustomName(itemMeta.getDisplayName());
            itemDrop.setCustomNameVisible(true);
        }
    }
}
