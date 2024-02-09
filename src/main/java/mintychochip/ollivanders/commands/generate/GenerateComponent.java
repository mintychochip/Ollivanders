package mintychochip.ollivanders.commands.generate;

import mintychochip.genesis.commands.abstraction.GenericCommand;
import mintychochip.genesis.commands.abstraction.GenericCommandObject;
import mintychochip.genesis.commands.abstraction.SubCommand;
import mintychochip.genesis.container.AbstractItem;
import mintychochip.ollivanders.Ollivanders;
import mintychochip.ollivanders.items.builder.ComponentBuilder;
import mintychochip.ollivanders.items.container.ComponentConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashSet;
import java.util.Set;

public class GenerateComponent extends GenericCommand implements SubCommand {
    public GenerateComponent(String executor, String description, Set<String>  strings) {
        super(executor, description, strings);
    }

    @Override
    public boolean execute(String[] strings, Player player) {
        if(strings.length < depth) {
            return false;
        }
        String executor = strings[depth - 1];
        ComponentBuilder classic = new ComponentBuilder(Ollivanders.getInstance(), "CLASSIC", executor);
        int size = 1;
        if(strings.length >= (depth + 1)) {
            size = Integer.parseInt(strings[depth]);
        }
        ItemStack itemStack = classic.defaultBuild(false);
        itemStack.setAmount(size);
        player.getInventory().addItem(itemStack);

        return false;
    }
}
