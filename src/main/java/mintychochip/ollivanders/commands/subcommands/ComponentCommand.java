package mintychochip.ollivanders.commands.subcommands;

import mintychochip.ollivanders.betterwand.builder.CustomComponentBuilder;
import mintychochip.ollivanders.commands.SubCommand;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class ComponentCommand extends SubCommand {
    @Override
    public String getName() {
        return null;
    }

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
        if(args.length < 3) {
            return;
        }
        Material material = Material.valueOf(args[1].toUpperCase());
        String itemPath = args[2];
        CustomComponentBuilder customComponentBuilder = new CustomComponentBuilder(material, itemPath);
    }
}
