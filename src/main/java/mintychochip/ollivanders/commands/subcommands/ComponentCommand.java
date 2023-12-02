package mintychochip.ollivanders.commands.subcommands;

import mintychochip.ollivanders.betterwand.builder.CustomComponentBuilder;
import mintychochip.ollivanders.commands.SubCommand;
import org.bukkit.entity.Player;

public class ComponentCommand extends SubCommand {
    @Override
    public String getName() {
        return "component";
    } // ex: /ollivanders wand build || /ollivanders component defaultbuild ... 3 args

    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public String getSyntax() {
        return null;
    }

    @Override
    public void perform(Player player, String[] args) { //really really basic way to do this, might want to make a parser to make it easy for chaos to get items
        if (args.length < 2) {
            return;
        }
        if (args[1].equalsIgnoreCase("defaultbuild")) {
            if (args.length < 3) {
                return;
            }
            player.getInventory().addItem(new CustomComponentBuilder(args[2]).defaultBuild());
        }
    }
}
