package mintychochip.ollivanders.commands.book;

import mintychochip.ollivanders.commands.SubCommand;
import org.bukkit.entity.Player;

public class PageCommand extends SubCommand {
    public PageCommand(String executor, String description) {
        super(executor, description);
    }

    @Override
    public boolean execute(String[] args, Player sender) {
        return false;
    }
}
