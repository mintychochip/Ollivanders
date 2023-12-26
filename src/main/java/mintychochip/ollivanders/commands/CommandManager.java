package mintychochip.ollivanders.commands;

import mintychochip.genesis.config.GenesisRegistry;
import mintychochip.genesis.container.AbstractItem;
import mintychochip.ollivanders.Ollivanders;
import mintychochip.ollivanders.wand.config.ComponentConfig;
import mintychochip.ollivanders.wand.builder.ComponentBuilder;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class CommandManager implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player player) {
            ComponentConfig componentConfig = Ollivanders.getComponentConfig();
            componentConfig.setMain(strings[0], false);
            ItemStack itemStack = new ComponentBuilder(new AbstractItem(Ollivanders.getInstance(),
                    Ollivanders.getComponentConfig().getDefaultMaterial()), GenesisRegistry.getThemes().get("CLASSIC")).defaultBuild();
            player.getInventory().addItem(itemStack);
        }
        return true;
    }
}
