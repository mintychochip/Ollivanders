package mintychochip.ollivanders.commands.generate;

import mintychochip.genesis.config.abstraction.GenesisConfigurationSection;
import mintychochip.genesis.container.AbstractItem;
import mintychochip.genesis.flags.Bindable;
import mintychochip.ollivanders.Ollivanders;
import mintychochip.ollivanders.items.builder.ComponentBuilder;
import mintychochip.ollivanders.items.container.ComponentConfigurationSection;
import mintychochip.ollivanders.spellbook.BookBuilder;
import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class GenerationMethods implements Bindable {

    public static ItemStack generateBook(String key) {
        GenesisConfigurationSection configurationSection = Ollivanders.getItemConfig().getBooks().getConfigurationSection(key);
        if(configurationSection.isNull()) {
            return null;
        }
        return new BookBuilder("CLASSIC", configurationSection).defaultBuild();
    }
    public static List<ItemStack> generateAllBooks() {
        List<ItemStack> itemStackList = new ArrayList<>();
        for (String key : Ollivanders.getItemConfig().getBooks().getKeys(false)) {
            itemStackList.add(generateBook(key));
        }
        return itemStackList;
    }
    public static ItemStack generateComponent(String component) {
        return new ComponentBuilder(Ollivanders.getInstance(), "CLASSIC", component).defaultBuild(component,false);
    }
    public static List<ItemStack> generateAllComponents() {
        List<ItemStack> itemStackList = new ArrayList<>();
        for (String key : Ollivanders.getComponentConfig().getCustom().getKeys(false)) {
            itemStackList.add(generateComponent(key));
        }
        return itemStackList;
    }
}
