package mintychochip.ollivanders.util;

import mintychochip.genesis.Genesis;
import mintychochip.genesis.container.AbstractItem;
import mintychochip.ollivanders.Ollivanders;
import mintychochip.ollivanders.items.builder.ComponentBuilder;
import mintychochip.ollivanders.items.container.ComponentConfigurationSection;
import mintychochip.ollivanders.spellbook.BookBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class RecipeItemLoader {
    private final Map<String,ItemStack> components = new HashMap<>();
    private final Map<String,ItemStack> books = new HashMap<>();
    public RecipeItemLoader() {
        try {
            loadComponents();
            loadBooks();
            Ollivanders instance = Ollivanders.getInstance();
            Genesis.getItemManager().addRecipeItems(instance,components);
            Genesis.getItemManager().addRecipeResults(instance,components);
            Genesis.getItemManager().addRecipeResults(instance,books);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void loadBooks() throws IOException {
        for (String key : Ollivanders.getItemConfig().getBooks().getKeys(false)) {
            books.put(convertStringToKeyable(key),new BookBuilder(new AbstractItem(Ollivanders.getInstance(),Material.WRITABLE_BOOK,true), "CLASSIC", Ollivanders.getItemConfig().getBooks().getConfigurationSection(key)).defaultBuild());
        }
    }
    public String convertStringToKeyable(String text) {
        return ChatColor.stripColor(text.strip().replace(" ", "_").toLowerCase().replace("'",""));
    }

    public void loadComponents() {
        for (String key : Ollivanders.getComponentConfig().getCustom().getKeys(false)) {
            ComponentConfigurationSection main = Ollivanders.getComponentConfig().getMainConfigurationSection(key, false);
            components.put(key, new ComponentBuilder(new AbstractItem(Ollivanders.getInstance(), main.getDefaultMaterial(),false), "CLASSIC", main).defaultBuild());
        }
    }

    public Map<String, ItemStack> getBooks() {
        return books;
    }

    public Map<String, ItemStack> getComponents() {
        return components;
    }
}
