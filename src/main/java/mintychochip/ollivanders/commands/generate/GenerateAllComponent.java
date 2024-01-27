package mintychochip.ollivanders.commands.generate;

import mintychochip.genesis.commands.abstraction.GenericCommandObject;
import mintychochip.genesis.commands.abstraction.SubCommand;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class GenerateAllComponent extends GenericCommandObject implements SubCommand { //could port all these to one command later idk
    public GenerateAllComponent(String executor, String description) {
        super(executor, description);
    }

    @Override
    public boolean execute(String[] strings, Player player) {
        for (ItemStack generateAllComponent : GenerationMethods.generateAllComponents()) {
            player.getInventory().addItem(generateAllComponent);
        }
        return true;
    }
}
