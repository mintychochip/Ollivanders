package mintychochip.ollivanders.commands.subcommands;

import mintychochip.ollivanders.betterwand.builder.CustomComponentBuilder;
import mintychochip.ollivanders.betterwand.builder.WandBuilder;
import mintychochip.ollivanders.commands.SubCommand;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class WandCommand extends SubCommand {
    @Override
    public String getName() {
        return "give";
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
    public void perform(Player player, String[] args) {
        String arg = args[1];
        int i = Integer.parseInt(arg);


        List<ItemStack> materials =
                new ArrayList<>();
        materials.add(new ItemStack(Material.STICK));
        materials.add(new ItemStack(Material.ENDER_EYE));
        materials.add(new ItemStack(Material.END_CRYSTAL));
        materials.add(new ItemStack(Material.AMETHYST_SHARD));
        materials.add(new ItemStack(Material.QUARTZ));
        ItemStack hello = new WandBuilder(materials, Material.BLAZE_ROD)
                .getDefaultLore()
                .addAllBoosts()
                .getDefaultDisplayName()
                .setUnstackable()
                .setCustomModelData(i).addStatLore()
                .build();
        player.getInventory().addItem(hello);
    }
}
