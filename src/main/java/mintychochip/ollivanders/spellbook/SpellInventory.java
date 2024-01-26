package mintychochip.ollivanders.spellbook;

import mintychochip.genesis.builder.ItemBuilder;
import mintychochip.genesis.container.AbstractItem;
import mintychochip.ollivanders.Ollivanders;
import mintychochip.ollivanders.container.Spell;
import mintychochip.ollivanders.items.util.OllivandersSerializer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SpellInventory implements Listener {

    private Inventory spellInventory;
    private Player player;

    private ItemStack book;

    private Map<Integer,Integer> mappings = new HashMap<>();

    private final int inventorySize;

    private final int size;

    private final ItemStack emptySpellSlot;

    private final ItemStack filler;
    public SpellInventory(int size, Map<Integer, Spell> spells, Player player, ItemStack book) throws IOException {
        if(spells == null) {
            throw new IOException("Spells cannot be null");
        }
        this.size = size;
        inventorySize = (int) Math.ceil((float) size / 9) * 9;
        this.spellInventory = Bukkit.createInventory(player,inventorySize,"Spells:");
        this.player = player;
        this.book = book;
        emptySpellSlot = new ItemBuilder(new AbstractItem(Ollivanders.getInstance(),Material.MAP),"CLASSIC").setCustomModelData(1).setDisplayName("Open Spell Slot",ChatColor.AQUA).build();
        filler = new ItemBuilder(new AbstractItem(Ollivanders.getInstance(),Material.GRAY_STAINED_GLASS_PANE),"CLASSIC").setDisplayName("",ChatColor.GRAY).build();
        loadInventory(spells);
    }
    public void loadInventory(Map<Integer,Spell> spells) {
        int count = 0;
        for (Integer i : spells.keySet()) {
            Spell spell = spells.get(i);
            if(spell != null) {
                mappings.put(count,i);
                ItemBuilder classic = new ItemBuilder(new AbstractItem(Ollivanders.getInstance(), Material.MAP,false), "CLASSIC")
                        .setCustomModelData(spell.getMechanic().getMechanicSettings().getSpellInventoryCustomModel())
                        .setDisplayName(spell.getMechanic().getName(), ChatColor.AQUA)
                        .addLore("Page: " + (i + 1));
                ItemStack build = classic.build();
                spellInventory.setItem(count++,build);
            }
        }
        populateRemainingSpellSlots(size,emptySpellSlot);
        populateRemainingSpellSlots(inventorySize,filler);
    }
    public void populateRemainingSpellSlots(int size, ItemStack filler) {
        for (int i = 0; i < size; i++) {
            if(spellInventory.getItem(i) == null) {
                spellInventory.setItem(i,filler);
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
        if(!event.getInventory().equals(spellInventory)) {
            return;
        }
        event.setCancelled(true);
        if(event.getClickedInventory() == null) {
            return;
        }
        if(!event.getClickedInventory().equals(spellInventory)) {
            return;
        }
        if(clickedValidSpell(player,event.getCurrentItem())) {
            Bukkit.broadcastMessage("here");
            player.sendMessage("Selected page: " + (mappings.get(event.getSlot()) + 1));
            BookData bookData = OllivandersSerializer.extractBookData(book);
            if (bookData != null) {
                bookData.setCurrentPage(mappings.get(event.getSlot()));
                bookData.setMappings(mappings);
                Bukkit.broadcastMessage(bookData.getCurrentPage() + "");
                bookData.serialize(book);
            }
        }
        player.closeInventory();
    }
    private boolean clickedValidSpell(Player player, ItemStack currentItem) {
        if(currentItem == null || currentItem.getType().isAir() || currentItem.equals(filler)) {
            player.closeInventory();
            return false;
        }
        if(currentItem.equals(emptySpellSlot)) {
            player.sendMessage("This spell slot is empty");
            return false;
        }
        return true;
    }

    public Map<Integer, Integer> getMappings() {
        return mappings;
    }
}
