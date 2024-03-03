package mintychochip.ollivanders.util;

import mintychochip.genesis.Genesis;
import mintychochip.genesis.config.abstraction.GenesisConfigurationSection;
import mintychochip.genesis.container.items.AbstractItem;
import mintychochip.ollivanders.Ollivanders;
import mintychochip.ollivanders.items.container.ComponentConfigurationSection;
import mintychochip.ollivanders.items.container.ComponentData;
import mintychochip.ollivanders.items.container.spellbook.BookData;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class RecipeItemLoader {
    private final Map<String, ItemStack> components = new HashMap<>();
    private final Map<String, ItemStack> books = new HashMap<>();

    public RecipeItemLoader() {
        try {
            loadComponents();
            loadBooks();
            Ollivanders instance = Ollivanders.getInstance();
            Genesis.getItemManager().addRecipeItems(instance, components);
            Genesis.getItemManager().addRecipeResults(instance, components);
            Genesis.getItemManager().addRecipeResults(instance, books);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void loadBooks() throws IOException {
        for (String key : Ollivanders.getItemConfig().getBooks().getKeys(false)) {
            GenesisConfigurationSection configurationSection = Ollivanders.getItemConfig().getBooks().getConfigurationSection(key);
            books.put(key, new AbstractItem.EmbeddedDataBuilder(Ollivanders.getInstance(), Material.WRITABLE_BOOK, configurationSection, false, "CLASSIC", new BookData(configurationSection.getInt(OllivandersConfigMarker.spell_slot_size))).defaultBuild().getItemStack());
        }
    }

    public void loadComponents() {
        for (String key : Ollivanders.getItemConfig().getComponents().getKeys(false)) {
            ComponentConfigurationSection mainConfigurationSection = Ollivanders.getComponentConfig().getMainConfigurationSection(key, false);
            components.put(key, new AbstractItem.EmbeddedDataBuilder(Ollivanders.getInstance(), Ollivanders.getItemConfig().getComponents().getConfigurationSection(key), false, "CLASSIC", new ComponentData(mainConfigurationSection)).defaultBuild().getItemStack());
        }
    }

    public Map<String, ItemStack> getBooks() {
        return books;
    }

    public Map<String, ItemStack> getComponents() {
        return components;
    }
}
