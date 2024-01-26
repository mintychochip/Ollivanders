package mintychochip.ollivanders.commands.generate;

import mintychochip.genesis.flags.Bindable;
import mintychochip.ollivanders.Ollivanders;
import mintychochip.ollivanders.spellbook.BookBuilder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class GenerationMethods implements Bindable {

    public static ItemStack generateBook(String key) {
        return new BookBuilder("CLASSIC",Ollivanders.getItemConfig().getBooks().getConfigurationSection(key)).defaultBuild();
    }
    public static List<ItemStack> generateAllBooks() {
        List<ItemStack> itemStackList = new ArrayList<>();
        for (String key : Ollivanders.getItemConfig().getBooks().getKeys(false)) {
            itemStackList.add(generateBook(key));
        }
        return itemStackList;
    }

}
