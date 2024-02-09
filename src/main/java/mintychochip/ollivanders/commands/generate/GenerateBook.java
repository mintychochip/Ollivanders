package mintychochip.ollivanders.commands.generate;

import mintychochip.genesis.commands.abstraction.GenericCommand;
import mintychochip.genesis.commands.abstraction.GenericCommandObject;
import mintychochip.genesis.commands.abstraction.SubCommand;
import mintychochip.genesis.flags.Bindable;
import mintychochip.ollivanders.Ollivanders;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Set;

public class GenerateBook extends GenericCommand implements SubCommand, Bindable {
    public GenerateBook(String executor, String description, Set<String> strings) {
        super(executor, description, strings);
    }

    @Override
    public boolean execute(String[] strings, Player sender) {
        if (strings.length < depth) {
            return false;
        }
        ItemStack itemStack = GenerationMethods.generateBook(strings[depth - 1]);
        if (itemStack == null) {
            return false;
        }
        if (strings.length >= (depth + 1)) {
            itemStack = addBind(itemStack, strings[depth]);
        } else {
            itemStack = addBind(itemStack, sender.getName());
        }
        sender.getInventory().addItem(itemStack);
        return false;
    }
}
