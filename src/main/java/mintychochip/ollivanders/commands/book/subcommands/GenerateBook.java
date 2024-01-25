package mintychochip.ollivanders.commands.book.subcommands;

import mintychochip.ollivanders.commands.abstraction.GenericCommandObject;
import mintychochip.ollivanders.commands.abstraction.SubCommand;
import org.bukkit.entity.Player;

public class GenerateBook extends GenericCommandObject implements SubCommand {
    public GenerateBook(String executor, String description, int depth) {
        super(executor, description, depth);
    }

    @Override
    public boolean execute(String[] args, Player sender) {
        if(args.length > depth) {

        }
        return false;
    }
}
