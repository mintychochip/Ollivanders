package mintychochip.ollivanders.wand.container;

import mintychochip.genesis.config.GenesisConfigurationSection;
import mintychochip.genesis.container.ItemData;
import mintychochip.ollivanders.wand.enums.CoreType;
import mintychochip.ollivanders.wand.util.ComponentUtil;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class WandData extends ItemData {

    public static final long serialVersionUID = 1234567L;
    private WandBoost wandBoost = new WandBoost().addAll(1);
    private CoreType coreType;

    private Map<ComponentData, ItemStack> mappedMaterials;
    public WandData(GenesisConfigurationSection main,List<ItemStack> materials) {
        coreType = Enum.valueOf(CoreType.class,main.getPath().toUpperCase());
        mappedMaterials = materials.stream().collect(Collectors.toMap(ComponentUtil::componentDataFromItemStack, material -> material));
        for (ItemStack material : materials) {
            wandBoost.addAll(ComponentUtil.componentDataFromItemStack(material).getWandBoost());
        }
    }
    public Map<ComponentData, ItemStack> getMappedMaterials() {
        return mappedMaterials;
    }

    public WandData setMappedMaterials(Map<ComponentData, ItemStack> mappedMaterials) {
        this.mappedMaterials = mappedMaterials;
        return this;
    }

    public WandData setDisplayName(String displayName) {
        return (WandData) super.setDisplayName(displayName);
    }
    public WandBoost getWandBoost() {
        return wandBoost;
    }

    public void setWandBoost(WandBoost wandBoost) {
        this.wandBoost = wandBoost;
    }

    public CoreType getCoreType() {
        return coreType;
    }

    public void setCoreType(CoreType CoreType) {
        this.coreType = CoreType;
    }
    public String toString() {
        return String.format("%s %s %s", wandBoost.toString(),displayName, coreType.toString());
    }
}
