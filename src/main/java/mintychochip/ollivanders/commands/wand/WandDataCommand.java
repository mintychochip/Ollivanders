package mintychochip.ollivanders.commands.wand;

import mintychochip.genesis.commands.abstraction.GenericCommandObject;
import mintychochip.genesis.commands.abstraction.SubCommand;
import mintychochip.ollivanders.items.container.ComponentData;
import mintychochip.ollivanders.util.EnumUtil;
import org.bukkit.entity.Player;

public class WandDataCommand extends GenericCommandObject implements SubCommand {
    public WandDataCommand(String executor, String description) {
        super(executor, description);
    }

    @Override
    public boolean execute(String[] strings, Player player) {

        return false;
    }
}
