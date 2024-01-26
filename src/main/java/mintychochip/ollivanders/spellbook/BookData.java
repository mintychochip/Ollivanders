package mintychochip.ollivanders.spellbook;

import mintychochip.genesis.container.ItemData;
import mintychochip.genesis.util.Serializer;
import mintychochip.ollivanders.Ollivanders;
import mintychochip.ollivanders.container.Spell;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

import java.io.IOException;
import java.util.*;

public class BookData extends ItemData {

    private static final long serialVersionUID = 1205948223123L;

    private Map<Integer, String> content = new HashMap<>();
    private int currentPage = 0;
    private int bookTotalPages = 0;
    private Map<Integer,Integer> mappings;

    private int size;

    public int getSize() {
        return size;
    }

    public BookData(int size) throws IOException {
        super("book");
        this.size = size;
    }

    public BookData setMappings(Map<Integer, Integer> mappings) {
        this.mappings = mappings;
        return this;
    }

    public BookData setSize(int size) {
        this.size = size;
        return this;
    }

    public void openSpellBook(Player player, ItemStack book) {
        try {
            SpellInventory spellInventory = new SpellInventory(size, getSpells(), player, book);
            mappings = spellInventory.getMappings();
            Bukkit.getPluginManager().registerEvents(spellInventory, Ollivanders.getInstance());
            spellInventory.open();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Map<Integer, Spell> getSpells() {
        Map<Integer, Spell> spellMap = new HashMap<>();
        for (int i = 0; i < bookTotalPages; i++) {
            Spell spellByPage = getSpellByPage(i);
            if (spellByPage != null) {
                spellMap.put(i, spellByPage);
            }
        }
        return spellMap;
    }

    public BookData setCurrentPage(int page) {
        currentPage = page;
        return this;
    }

    public Spell getSpellOnCurrentPage() {
        return getSpellByPage(currentPage);
    }

    public void updateBook(ItemStack book) throws IOException {
        if (book == null) {
            return;
        }
        if (book.getItemMeta() instanceof BookMeta bookMeta) {
            content.clear();
            List<String> pages = bookMeta.getPages();
            bookTotalPages = pages.size();
            for (int i = 0; i < bookTotalPages; i++) {
                content.put(i, pages.get(i));
            }
        }
    }

    public Spell getSpellByPage(int pageNumber) {
        String page = content.get(pageNumber);
        if (page == null) {
            return null;
        }
        List<Spell> spellList = new ArrayList<>();
        for (String s : page.split(",")) {
            Spell spell = Ollivanders.getTokenizer().defaultBuild(s);
            if (spell == null) {
                return null;
            }
            spell.getMechanic().setTransition(null);
            spellList.add(spell);
        }
        Collections.reverse(spellList);
        int index = spellList.size() - 1;
        for (int i = 0; i < index; i++) {
            Spell subSpell = spellList.get(i);
            spellList.get(i + 1).getMechanic().setTransition(subSpell);
        }
        return spellList.get(index);
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public boolean serialize(ItemStack item) {
        return Serializer.serializeToItem(this, item);
    }

    public Map<Integer, Integer> getMappings() {
        return mappings;
    }
}
