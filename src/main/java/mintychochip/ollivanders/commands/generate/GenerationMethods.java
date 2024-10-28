package mintychochip.ollivanders.commands.generate;

import mintychochip.genesis.config.abstraction.GenesisConfigurationSection;
import mintychochip.genesis.container.items.AbstractItem;
import mintychochip.genesis.container.items.actions.ActionPacket;
import mintychochip.genesis.container.items.actions.EventAction;
import mintychochip.genesis.flags.Bindable;
import mintychochip.ollivanders.Ollivanders;
import mintychochip.ollivanders.items.container.ComponentConfigurationSection;
import mintychochip.ollivanders.items.container.ComponentData;
import mintychochip.ollivanders.items.container.spellbook.BookData;
import mintychochip.ollivanders.util.OllivandersConfigMarker;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GenerationMethods implements Bindable {

    public static AbstractItem generateBook(String key) {
        GenesisConfigurationSection configurationSection = Ollivanders.getItemConfig().getBooks().getConfigurationSection(key);
        if (configurationSection.isNull()) {
            return null;
        }
        try {
            return new AbstractItem.EmbeddedDataBuilder(Ollivanders.getInstance(), Material.WRITABLE_BOOK, configurationSection, true, "CLASSIC", new BookData(configurationSection.getInt(OllivandersConfigMarker.spell_slot_size))).defaultBuild();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<AbstractItem> generateAllBooks() {
        List<AbstractItem> itemStackList = new ArrayList<>();
        for (String key : Ollivanders.getItemConfig().getBooks().getKeys(false)) {
            itemStackList.add(generateBook(key));
        }
        return itemStackList;
    }

    public static AbstractItem generateComponent(String component) {
        ComponentConfigurationSection mainConfigurationSection = Ollivanders.getComponentConfig().getMainConfigurationSection(component, false);
        return new AbstractItem.EmbeddedDataBuilder(Ollivanders.getInstance(), Ollivanders.getItemConfig().getComponents().getConfigurationSection(component), false, "CLASSIC", new ComponentData(mainConfigurationSection))
                .addClickEvent("buss",new ActionPacket(EventAction.EXECUTE_COMMAND,"playsound minecraft:dreamy.buss voice @a")).defaultBuild();
    }

    public static List<AbstractItem> generateAllComponents() {
        List<AbstractItem> itemStackList = new ArrayList<>();
        for (String key : Ollivanders.getComponentConfig().getCustom().getKeys(false)) {
            itemStackList.add(generateComponent(key));
        }
        return itemStackList;
    }
}
