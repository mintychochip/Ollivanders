package mintychochip.ollivanders.wand.container;

import mintychochip.genesis.config.GenesisConfigurationSection;
import mintychochip.genesis.container.ItemData;
import mintychochip.ollivanders.wand.enums.CoreType;
import mintychochip.ollivanders.wand.util.ComponentUtil;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class WandData extends ItemData {

    public static final long serialVersionUID = 1234567L;
    private final WandBoost wandBoost = new WandBoost().addAll(1);
    private final CoreType coreType;
    private final List<ComponentData> componentData = new ArrayList<>();
    public WandData(GenesisConfigurationSection main, List<ItemStack> materials) {
        super("wand");
        coreType = Enum.valueOf(CoreType.class,main.getPath().toUpperCase());
        for (ItemStack material : materials) {
            wandBoost.addAll(ComponentUtil.componentDataFromItemStack(material).getWandBoost());
        }
        for (ItemStack material : materials) {
            componentData.add(ComponentUtil.componentDataFromItemStack(material));
        }
    }

    public List<ComponentData> getComponentData() {
        return componentData;
    }

    public WandData setDisplayName(String displayName) {
        return (WandData) super.setDisplayName(displayName);
    }
    public WandBoost getWandBoost() {
        return wandBoost;
    }
    public CoreType getCoreType() {
        return coreType;
    }
    public String toString() {
        return String.format("%s %s %s", wandBoost.toString(),displayName, coreType.toString());
    }
}
