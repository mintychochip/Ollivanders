package mintychochip.ollivanders.commands.generate;

import mintychochip.genesis.commands.abstraction.GenericCommandObject;
import mintychochip.genesis.commands.abstraction.SubCommand;
import mintychochip.genesis.config.GenesisConfigurationSection;
import mintychochip.genesis.flags.Bindable;
import mintychochip.ollivanders.Ollivanders;
import mintychochip.ollivanders.spellbook.BookBuilder;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class GenerateAllBooks extends GenericCommandObject implements SubCommand, Bindable {
    public GenerateAllBooks(String executor, String description) {
        super(executor, description);
    }

    @Override
    public boolean execute(String[] args, Player sender) {
        String displayName = sender.getDisplayName();
        if(args.length >= depth) {
            displayName = args[depth - 1];
        }
        for (ItemStack itemStack : GenerationMethods.generateAllBooks()) {
            sender.getInventory().addItem(addBind(itemStack,displayName));
        }
        return true;
    }
}
