package mintychochip.ollivanders.commands.generate;

import mintychochip.genesis.commands.abstraction.GenericCommand;
import mintychochip.genesis.commands.abstraction.SubCommand;
import org.bukkit.entity.Player;

import java.util.Set;

public class GenerateComponent extends GenericCommand implements SubCommand {
    public GenerateComponent(String executor, String description, Set<String> strings) {
        super(executor, description, strings);
    }

    @Override
    public boolean execute(String[] strings, Player player) {
        if (strings.length < depth) {
            return false;
        }
        String executor = strings[depth - 1];
        player.getInventory().addItem(GenerationMethods.generateComponent(executor).getItemStack());

        return false;
    }
}
