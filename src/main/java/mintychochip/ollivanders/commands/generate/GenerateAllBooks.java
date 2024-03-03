package mintychochip.ollivanders.commands.generate;

import mintychochip.genesis.commands.abstraction.GenericCommandObject;
import mintychochip.genesis.commands.abstraction.SubCommand;
import mintychochip.genesis.container.items.AbstractItem;
import mintychochip.genesis.flags.Bindable;
import mintychochip.ollivanders.Ollivanders;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Set;

import static mintychochip.ollivanders.commands.generate.GenerationMethods.generateAllBooks;

public class GenerateAllBooks extends GenericCommandObject implements SubCommand, Bindable {
    public GenerateAllBooks(String executor, String description) {
        super(executor, description);
    }

    @Override
    public boolean execute(String[] strings, Player sender) {
        String displayName = sender.getDisplayName();
        if (strings.length >= depth) {
            displayName = strings[depth - 1];
        }
        for (AbstractItem abstractItem : generateAllBooks()) {
            sender.getInventory().addItem(addBind(abstractItem.getItemStack(), displayName));

        }
        return true;
    }
}
