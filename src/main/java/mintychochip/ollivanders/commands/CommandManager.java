package mintychochip.ollivanders.commands;

import mintychochip.ollivanders.commands.subcommands.WandCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class CommandManager implements CommandExecutor {

    private final List<SubCommand> subCommandList = new ArrayList<>();

    public CommandManager() {
        subCommandList.add(new WandCommand());
    }
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        if(commandSender instanceof Player player) {
            if(strings.length > 0) {
                for (SubCommand subCommand : subCommandList) {
                    if(strings[0].equalsIgnoreCase(subCommand.getName())) {
                        subCommand.perform(player,strings);
                    }
                }
            }
        }
        return true;
    }

    public List<SubCommand> getSubCommandList() {
        return subCommandList;
    }
}
