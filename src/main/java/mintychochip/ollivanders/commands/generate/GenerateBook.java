package mintychochip.ollivanders.commands.generate;

import mintychochip.genesis.commands.abstraction.GenericCommandObject;
import mintychochip.genesis.commands.abstraction.SubCommand;
import mintychochip.genesis.flags.Bindable;
import mintychochip.ollivanders.Ollivanders;
import mintychochip.ollivanders.spellbook.BookBuilder;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class GenerateBook extends GenericCommandObject implements SubCommand, Bindable {
    public GenerateBook(String executor, String description) {
        super(executor, description);
    }

    @Override
    public boolean execute(String[] args, Player sender) {
        if (args.length < depth) {
            return false;
        }
        ItemStack itemStack = GenerationMethods.generateBook(args[depth - 1]);
        if (args.length >= (depth + 1)) {
            itemStack = addBind(itemStack, args[depth]);
        } else {
            itemStack = addBind(itemStack, sender.getName());
        }
        sender.getInventory().addItem(itemStack);
        return false;
    }
}
