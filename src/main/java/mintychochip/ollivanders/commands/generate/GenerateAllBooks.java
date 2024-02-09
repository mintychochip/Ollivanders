package mintychochip.ollivanders.commands.generate;

import mintychochip.genesis.commands.abstraction.GenericCommandObject;
import mintychochip.genesis.commands.abstraction.SubCommand;
import mintychochip.genesis.flags.Bindable;
import mintychochip.ollivanders.Ollivanders;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Set;

public class GenerateAllBooks extends GenericCommandObject implements SubCommand, Bindable {
    public GenerateAllBooks(String executor, String description) {
        super(executor, description);
    }

    @Override
    public boolean execute(String[] strings, Player sender) {
        String displayName = sender.getDisplayName();
        if(strings.length >= depth) {
            displayName = strings[depth - 1];
        }
        for (ItemStack itemStack : GenerationMethods.generateAllBooks()) {
            sender.getInventory().addItem(addBind(itemStack,displayName));
        }
        return true;
    }
}
