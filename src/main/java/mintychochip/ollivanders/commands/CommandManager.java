package mintychochip.ollivanders.commands;

import mintychochip.genesis.container.AbstractItem;
import mintychochip.ollivanders.Ollivanders;
import mintychochip.ollivanders.wand.builder.ComponentBuilder;
import mintychochip.ollivanders.wand.container.ComponentConfigurationSection;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.InvocationTargetException;

public class CommandManager implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player player) {
            ItemStack itemStack = Ollivanders.getItemLoader().getComponents().get(strings[0]).clone();
            if(strings.length > 1 && strings[1] != null) {
                itemStack.setAmount(Integer.parseInt(strings[1]));
            }
            player.getInventory().addItem(itemStack);
        }
        return true;
    }
}
