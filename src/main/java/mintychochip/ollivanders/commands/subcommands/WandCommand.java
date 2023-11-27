package mintychochip.ollivanders.commands.subcommands;

import mintychochip.ollivanders.betterwand.WandBuilder;
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
        lore.add(ChatColor.WHITE + "Beware of the busters of nuts");
        lore.add(ChatColor.RED + "Because Taco gonna nut in your butt!!!");
        lore.add(ChatColor.GREEN + "He is gonna bend you over like a mutt");
        lore.add(ChatColor.BLUE + "GG");
        ItemStack build = new WandBuilder(materials).addAllBoosts().addName(ChatColor.BOLD + "This the nut I NEED").addLore(null).setCustomModelData(1).setUnstackable().build();
        player.getInventory().addItem(build);
    }
}
