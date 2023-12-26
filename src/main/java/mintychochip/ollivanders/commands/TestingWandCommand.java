package mintychochip.ollivanders.commands;

import mintychochip.genesis.config.GenesisRegistry;
import mintychochip.genesis.container.AbstractItem;
import mintychochip.ollivanders.Ollivanders;
import mintychochip.ollivanders.wand.builder.ComponentBuilder;
import mintychochip.ollivanders.wand.builder.WandBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TestingWandCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (commandSender instanceof Player player) {
            List<ItemStack> materials = new ArrayList<>();
            materials.add(new ItemStack(Material.BLAZE_ROD));
            materials.add(new ItemStack(Material.STRING));
            Ollivanders.getComponentConfig().setMain("lens", false);
            materials.add(new ComponentBuilder(new AbstractItem(Ollivanders.getInstance(), Material.STONE), GenesisRegistry.getThemes().get("CLASSIC")).defaultBuild());
            materials.add(new ItemStack(Material.END_CRYSTAL));
            boolean b = Boolean.parseBoolean(strings[1]);
            Ollivanders.getComponentConfig().setMain(strings[0], b);
            if (!b) {
                materials.add(new ComponentBuilder(new AbstractItem(Ollivanders.getInstance(), Material.STONE),GenesisRegistry.getThemes().get("CLASSIC")).defaultBuild());
            } else {
                materials.add(new ItemStack(Enum.valueOf(Material.class, strings[0].toUpperCase())));
            }
            WandBuilder wandBuilder;
            Ollivanders.getWandConfig().setMain(strings[0]);
            try {
                wandBuilder = new WandBuilder(new AbstractItem(Ollivanders.getInstance(), Material.BLAZE_ROD), GenesisRegistry.getThemes().get("CLASSIC"),materials);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            player.getInventory().addItem(wandBuilder.defaultBuild());
        }
        return true;
    }
}
