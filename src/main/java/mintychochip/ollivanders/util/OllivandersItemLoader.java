package mintychochip.ollivanders.util;

import mintychochip.genesis.Genesis;
import mintychochip.genesis.container.AbstractItem;
import mintychochip.ollivanders.Ollivanders;
import mintychochip.ollivanders.spellbook.BookBuilder;
import mintychochip.ollivanders.spellbook.BookType;
import mintychochip.ollivanders.wand.builder.ComponentBuilder;
import mintychochip.ollivanders.wand.container.ComponentConfigurationSection;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.CraftingRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OllivandersItemLoader {
    private final Map<String,ItemStack> components = new HashMap<>();
    private final Map<String,ItemStack> books = new HashMap<>();
    public OllivandersItemLoader() {
        try {
            loadComponents();
            loadBooks();
            Ollivanders instance = Ollivanders.getInstance();
            Genesis.getItemManager().addRecipeItems(instance,components);
            Genesis.getItemManager().addRecipeResults(Ollivanders.getInstance(),books);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void loadBooks() throws IOException {
        for (BookType value : BookType.values()) {
            books.put(convertStringToKeyable(value.toString()),
                    new BookBuilder(new AbstractItem(Ollivanders.getInstance(), Material.WRITABLE_BOOK,true), "CLASSIC", value).defaultBuild());
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
