package mintychochip.ollivanders.commands.generate;

import mintychochip.genesis.commands.abstraction.GenericCommandObject;
import mintychochip.genesis.commands.abstraction.SubCommand;
import mintychochip.genesis.flags.Bindable;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class GenerateBook extends GenericCommandObject implements SubCommand, Bindable {
    public GenerateBook(String executor, String description) {
        super(executor, description);
    }

    @Override
    public boolean execute(String[] strings, Player sender) {
        if (strings.length < depth) {
            return false;
        }
        ItemStack itemStack = GenerationMethods.generateBook(strings[depth - 1]);
        if (strings.length >= (depth + 1)) {
            itemStack = addBind(itemStack, strings[depth]);
        } else {
            itemStack = addBind(itemStack, sender.getName());
        }
        sender.getInventory().addItem(itemStack);
        return false;
    }
}
