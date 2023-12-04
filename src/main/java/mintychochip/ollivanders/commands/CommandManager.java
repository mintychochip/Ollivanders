package mintychochip.ollivanders.commands;

import mintychochip.genesis.container.AbstractItem;
import mintychochip.ollivanders2.Ollivanders2;
import mintychochip.ollivanders2.wand.ComponentConfig;
import mintychochip.ollivanders2.wand.builder.ComponentBuilder;
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
            ComponentConfig componentConfig = Ollivanders2.getComponentConfig();
            Bukkit.broadcastMessage(strings[0]);
            componentConfig.setMain(strings[0], false);
            ItemStack itemStack = new ComponentBuilder(new AbstractItem(Ollivanders2.getInstance(),
                    Ollivanders2.getComponentConfig().getDefaultMaterial())).defaultBuild();
            player.getInventory().addItem(itemStack);
        }
        return true;
    }
}
