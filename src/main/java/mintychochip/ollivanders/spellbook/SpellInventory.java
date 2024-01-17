package mintychochip.ollivanders.spellbook;

import mintychochip.genesis.builder.ItemBuilder;
import mintychochip.genesis.container.AbstractItem;
import mintychochip.ollivanders.Ollivanders;
import mintychochip.ollivanders.container.Spell;
import mintychochip.ollivanders.wand.util.OllivandersSerializer;
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
    public SpellInventory(int size, Map<Integer, Spell> spells, Player player, ItemStack book) throws IOException {
        if(spells == null) {
            throw new IOException("Spells cannot be null");
        }
        this.spellInventory = Bukkit.createInventory(player,size,"Spells:");
        this.player = player;
        this.book = book;
        loadInventory(spells);
    }
    public void loadInventory(Map<Integer,Spell> spells) {
        int count = 0;
        for (Integer i : spells.keySet()) {
            Spell spell = spells.get(i);
            if(spell != null) {
                mappings.put(count,i);
                ItemBuilder classic = new ItemBuilder(new AbstractItem(Ollivanders.getInstance(), Material.MAP,false), "CLASSIC")
                        .setDisplayName(spell.getMechanic().getName(), ChatColor.AQUA)
                        .addLore("Page: " + (i + 1));
                ItemStack build = classic.build();
                spellInventory.setItem(count++,build);
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
        if(event.getCurrentItem() == null || event.getCurrentItem().getType().isAir()) {
            return;
        }
        player.sendMessage("Selected page: " + (mappings.get(event.getSlot()) + 1));
        SpellBook spellBook = OllivandersSerializer.extractBookData(book);
        if (spellBook != null) {
            spellBook.setCurrentPage(mappings.get(event.getSlot()));
            spellBook.serialize(book);
        }

    }
}
