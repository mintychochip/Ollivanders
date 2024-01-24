package mintychochip.ollivanders.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public abstract class GenericCommandManager implements CommandExecutor {

    protected int depth;
    @NotNull
    protected List<SubCommand> subCommands;

    public GenericCommandManager(List<SubCommand> subCommands) {
        this.subCommands = subCommands;
        depth = 1;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (commandSender instanceof Player player) {
            if (strings.length > depth) {
                for (SubCommand subCommand : subCommands) {
                    if (subCommand.getExecutor().equals(strings[0])) {
                        return subCommand.execute(strings, player);
                    }
                }
            }
        }
        return false;
    }

    public void setSubCommands(List<SubCommand> subCommands) {
        this.subCommands = subCommands;
    }

    public @NotNull List<SubCommand> getSubCommands() {
        return subCommands;
    }
}