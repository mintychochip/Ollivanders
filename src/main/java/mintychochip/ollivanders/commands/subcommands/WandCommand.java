package mintychochip.ollivanders.commands.subcommands;

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
        List<ItemStack> materials =
                new ArrayList<>();
        materials.add(new ItemStack(Material.STICK));
        materials.add(new ItemStack(Material.NETHER_STAR));
        materials.add(new ItemStack(Material.END_CRYSTAL));
        materials.add(new ItemStack(Material.AMETHYST_SHARD));
        materials.add(new ItemStack(Material.QUARTZ));
        List<String> lore = new ArrayList<>();
        ItemStack hello = new WandBuilder(materials, Material.BLAZE_ROD)
                .addLore(lore)
                .addAllBoosts()
                .setDisplayName("Hello")
                .setUnstackable()
                .setCustomModelData(1)
                .build();
        player.getInventory().addItem(hello);
    }
}
