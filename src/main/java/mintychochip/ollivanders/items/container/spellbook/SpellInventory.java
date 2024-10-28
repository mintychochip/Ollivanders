package mintychochip.ollivanders.items.container.spellbook;

import com.google.gson.Gson;
import mintychochip.genesis.Genesis;
import mintychochip.genesis.container.items.AbstractItem;
import mintychochip.genesis.util.Serializer;
import mintychochip.ollivanders.Ollivanders;
import mintychochip.ollivanders.container.Spell;
import mintychochip.ollivanders.items.util.OllivandersSerializer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SpellInventory implements Listener {

    private final int inventorySize;
    private final int size;
    private final ItemStack emptySpellSlot;
    private final ItemStack filler;
    private final Inventory spellInventory;
    private final Player player;
    private final ItemStack book;
    private final Map<Integer, Integer> mappings = new HashMap<>();

    public SpellInventory(int size, Map<Integer, Spell> spells, Player player, ItemStack book) throws IOException {
        if (spells == null) {
            throw new IOException("Spells cannot be null");
        }
        this.size = size;
        inventorySize = (int) Math.ceil((float) size / 9) * 9;
        this.spellInventory = Bukkit.createInventory(player, inventorySize, "Spells:");
        this.player = player;
        this.book = book;
        emptySpellSlot = new AbstractItem.ItemBuilder(Ollivanders.getInstance(), Material.MAP, false, "CLASSIC").setDisplayName(ChatColor.AQUA + "Open Spell Slot").setCustomModelData(1).build().getItemStack();
        filler = new AbstractItem.ItemBuilder(Ollivanders.getInstance(), Material.GRAY_STAINED_GLASS_PANE, false, "CLASSIC").setDisplayName(" ").build().getItemStack();
        loadInventory(spells);
    }

    public void loadInventory(Map<Integer, Spell> spells) {
        int count = 0;
        for (Integer i : spells.keySet()) {
            Spell spell = spells.get(i);
            if (spell != null) {
                mappings.put(count, i);
                ItemStack classic = new AbstractItem.ItemBuilder(Ollivanders.getInstance(), Material.MAP, false, "CLASSIC").setCustomModelData(spell.getMechanic().getMechanicSettings().getSpellInventoryCustomModel())
                        .setDisplayName(ChatColor.AQUA + spell.getMechanic().getName())
                        .addLore("Page: " + (i + 1)).build().getItemStack();
                spellInventory.setItem(count++, classic);
            }
        }
        populateRemainingSpellSlots(size, emptySpellSlot);
        populateRemainingSpellSlots(inventorySize, filler);
    }

    public void populateRemainingSpellSlots(int size, ItemStack filler) {
        for (int i = 0; i < size; i++) {
            if (spellInventory.getItem(i) == null) {
                spellInventory.setItem(i, filler);
            }
        }
    }

    public void open() {
        this.open(this.player);
    }

    public void open(Player player) {
        player.openInventory(spellInventory);
    }

    @EventHandler
    public void onClickInventory(final InventoryClickEvent event) {
        if (!event.getInventory().equals(spellInventory)) {
            return;
        }
        event.setCancelled(true);
        if (event.getClickedInventory() == null) {
            return;
        }
        if (!event.getClickedInventory().equals(spellInventory)) {
            return;
        }
        if (clickedValidSpell(player, event.getCurrentItem())) {
            player.sendMessage("Selected page: " + (mappings.get(event.getSlot()) + 1));
            BookData bookData = Genesis.getSerializer().deserialize("book", book, BookData.class);
            if (bookData != null) {
                bookData.setCurrentPage(mappings.get(event.getSlot())).setMappings(mappings);
                ItemMeta itemMeta = book.getItemMeta();
                itemMeta.getPersistentDataContainer().set(Genesis.getKey("book"), PersistentDataType.STRING, new Gson().toJson(bookData));
                book.setItemMeta(itemMeta);
            }
        }
        player.updateInventory();
        player.closeInventory();
    }

    @EventHandler(priority = EventPriority.LOW)
    public void moveItemInventory(final InventoryMoveItemEvent event) {
        if(event.getSource() == spellInventory || event.getDestination() == spellInventory) {
            event.setCancelled(true);
        }
    }
    private boolean clickedValidSpell(Player player, ItemStack currentItem) {
        if (currentItem == null || currentItem.getType().isAir() || currentItem.equals(filler)) {
            player.closeInventory();
            return false;
        }
        if (currentItem.equals(emptySpellSlot)) {
            player.sendMessage("This spell slot is empty");
            return false;
        }
        return true;
    }

    public Map<Integer, Integer> getMappings() {
        return mappings;
    }
}
