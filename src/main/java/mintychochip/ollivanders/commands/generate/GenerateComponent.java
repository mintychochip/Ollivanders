package mintychochip.ollivanders.commands.generate;

import mintychochip.genesis.commands.abstraction.GenericCommandObject;
import mintychochip.genesis.commands.abstraction.SubCommand;
import mintychochip.genesis.container.AbstractItem;
import mintychochip.ollivanders.Ollivanders;
import mintychochip.ollivanders.items.builder.ComponentBuilder;
import mintychochip.ollivanders.items.container.ComponentConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class GenerateComponent extends GenericCommandObject implements SubCommand {
    public GenerateComponent(String executor, String description) {
        super(executor, description);
    }

    @Override
    public boolean execute(String[] strings, Player player) {
        if(strings.length < depth) {
            return false;
        }
        ComponentConfigurationSection mainConfigurationSection = Ollivanders.getComponentConfig().getMainConfigurationSection(strings[depth - 1], false);
        ComponentBuilder classic = new ComponentBuilder(new AbstractItem(Ollivanders.getInstance(), mainConfigurationSection.getDefaultMaterial()), "CLASSIC", mainConfigurationSection);
        int size = 1;
        if(strings.length >= (depth + 1)) {
            size = Integer.parseInt(strings[depth]);
        }
        ItemStack itemStack = classic.defaultBuild();
        itemStack.setAmount(size);
        player.getInventory().addItem(itemStack);

        return false;
    }
}
