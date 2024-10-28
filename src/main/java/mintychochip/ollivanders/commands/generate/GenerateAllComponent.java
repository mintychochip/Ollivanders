package mintychochip.ollivanders.commands.generate;

import mintychochip.genesis.commands.abstraction.GenericCommand;
import mintychochip.genesis.commands.abstraction.GenericCommandObject;
import mintychochip.genesis.commands.abstraction.SubCommand;
import mintychochip.genesis.container.items.AbstractItem;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Set;

public class GenerateAllComponent extends GenericCommandObject implements SubCommand { //could port all these to one command later idk
    public GenerateAllComponent(String executor, String description) {
        super(executor, description);
    }

    @Override
    public boolean execute(String[] strings, Player player) {
        for (AbstractItem abstractItem : GenerationMethods.generateAllComponents()) {
            player.getInventory().addItem(abstractItem.getItemStack());

        }
        return true;
    }
}
