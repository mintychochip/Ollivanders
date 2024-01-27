package mintychochip.ollivanders.commands.generate;

import mintychochip.genesis.commands.abstraction.GenericCommandObject;
import mintychochip.genesis.commands.abstraction.SubCommand;
import mintychochip.genesis.container.AbstractItem;
import mintychochip.genesis.flags.Bindable;
import mintychochip.ollivanders.Ollivanders;
import mintychochip.ollivanders.items.builder.ComponentBuilder;
import mintychochip.ollivanders.items.builder.WandBuilder;
import mintychochip.ollivanders.items.container.ComponentConfigurationSection;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.IOException;
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
        if(strings.length >= (depth + 2)) {
            displayName = strings[depth + 1];
        }
        List<ItemStack> materials = new ArrayList<>();
        materials.add(new ItemStack(Material.END_ROD));
        materials.add(new ItemStack(Material.EMERALD));
        materials.add(new ItemStack(Material.STRING));
        materials.add(new ComponentBuilder(Ollivanders.getInstance(),"CLASSIC","lens").defaultBuild("lens",false));
        boolean b = Boolean.parseBoolean(strings[depth]);
        String core = strings[depth - 1];
        materials.add(new ComponentBuilder(Ollivanders.getInstance(),"CLASSIC",strings[depth - 1]).defaultBuild(core,b));
        try {
            ItemStack itemStack = new WandBuilder("CLASSIC", Ollivanders.getWandConfig().getMainConfigurationSection(strings[depth - 1].toUpperCase()), materials).defaultBuild();
            sender.getInventory().addItem(addBind(itemStack,displayName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return false;
    }
}
