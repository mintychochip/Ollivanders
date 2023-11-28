package mintychochip.ollivanders.commands.subcommands;

import mintychochip.ollivanders.betterwand.builder.CustomComponentBuilder;
import mintychochip.ollivanders.commands.SubCommand;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.IOException;

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
        Bukkit.broadcastMessage("bus");
        ItemStack build = null;
        try {
            build = new CustomComponentBuilder(Material.FEATHER, "phoenix-feather").getComponentType().getDisplayName().getDefaultLore().getCore().build();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        player.getInventory().addItem(build);
    }
}
