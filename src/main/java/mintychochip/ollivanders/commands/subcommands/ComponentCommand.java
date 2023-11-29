package mintychochip.ollivanders.commands.subcommands;

import mintychochip.ollivanders.betterwand.ComponentRegistry;
import mintychochip.ollivanders.betterwand.builder.CustomComponentBuilder;
import mintychochip.ollivanders.commands.SubCommand;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ComponentCommand extends SubCommand {
    @Override
    public String getName() {
        return "component";
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
        ItemStack build = null;

        build = new CustomComponentBuilder(Material.FEATHER, "phoenix-feather").defaultBuild(true);
        player.getInventory().addItem(build);
    }
}
