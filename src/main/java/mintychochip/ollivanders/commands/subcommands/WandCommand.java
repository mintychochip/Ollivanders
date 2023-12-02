package mintychochip.ollivanders.commands.subcommands;

import mintychochip.ollivanders.Ollivanders;
import mintychochip.ollivanders.betterwand.builder.CustomComponentBuilder;
import mintychochip.ollivanders.betterwand.builder.WandBuilder;
import mintychochip.ollivanders.commands.SubCommand;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;

public class WandCommand extends SubCommand {
    NamespacedKey last = new NamespacedKey(Ollivanders.getInstance(), "last");

    @Override
    public String getName() {
        return "build";
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
        if (args.length < 2) {
            return;
        }
        if (args[1].equalsIgnoreCase("last")) {
            if (player.getPersistentDataContainer().has(last, PersistentDataType.STRING)) {
                String s = player.getPersistentDataContainer().get(last, PersistentDataType.STRING);
                if (s != null) {
                    getItem(s, player);
                }
            }
        } else {
            getItem(args[1], player);
        }

    }

    public void getItem(String arg, Player player) {
        List<ItemStack> wandMaterials = new ArrayList<>();
        for (String material : arg.split(",")) {
            if (material.contains("core:")) {
                String[] split = material.split(":");
                wandMaterials.add(new CustomComponentBuilder(Material.STONE, split[1]).defaultBuild());
            } else if (material.contains("construct:")) {
                String[] split = material.split(":");
                wandMaterials.add(new CustomComponentBuilder(Material.STONE, split[1]).defaultBuild());
            } else {
                wandMaterials.add(new ItemStack(Material.valueOf(material.toUpperCase())));
            }
        }
        ItemStack defaultBuild = new WandBuilder(wandMaterials, Material.BLAZE_ROD).getDefaultBuild();
        player.getInventory().addItem(defaultBuild);

        player.getPersistentDataContainer().set(last, PersistentDataType.STRING, arg);
    }
}
