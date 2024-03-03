package mintychochip.ollivanders.commands.generate;

import mintychochip.genesis.commands.abstraction.GenericCommandObject;
import mintychochip.genesis.commands.abstraction.SubCommand;
import mintychochip.genesis.config.abstraction.GenesisConfigurationSection;

import mintychochip.genesis.container.items.AbstractItem;
import mintychochip.genesis.flags.Bindable;
import mintychochip.genesis.util.Rarity;
import mintychochip.ollivanders.Ollivanders;
import mintychochip.ollivanders.items.container.OllivandersItem;
import mintychochip.ollivanders.items.container.WandData;
import mintychochip.ollivanders.util.OllivandersConfigMarker;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class GenerateWand extends GenericCommandObject implements SubCommand, Bindable {
    public GenerateWand(String executor, String description) {
        super(executor, description);
    }

    @Override
    public boolean execute(String[] strings, Player sender) {
        if (strings.length < depth) {
            return false;
        }
        String displayName = sender.getName();
        if (strings.length >= (depth + 2)) {
            displayName = strings[depth + 1];
        }
        List<ItemStack> materials = new ArrayList<>();
        materials.add(new ItemStack(Material.END_ROD));
        materials.add(new ItemStack(Material.EMERALD));
        materials.add(new ItemStack(Material.STRING));
        String executor = strings[depth - 1];
        boolean material = Boolean.parseBoolean(strings[depth]);
        if(material) {
            materials.add(new ItemStack(Material.getMaterial(executor.toUpperCase())));
        } else {
            materials.add(GenerationMethods.generateComponent(executor).getItemStack());
        }
        materials.add(GenerationMethods.generateComponent("lens").getItemStack());
        GenesisConfigurationSection main = Ollivanders.getWandConfig().getMainConfigurationSection(executor.toUpperCase());
        AbstractItem abstractItem = new OllivandersItem.WandBuilder(Ollivanders.getInstance(), Material.BLAZE_ROD, main, false, "CLASSIC", new WandData(main, materials), materials).defaultBuild();
        sender.getInventory().addItem(abstractItem.getItemStack());
        return false;
    }
}
