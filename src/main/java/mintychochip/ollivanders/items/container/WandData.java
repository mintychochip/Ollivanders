package mintychochip.ollivanders.items.container;


import mintychochip.genesis.Genesis;
import mintychochip.genesis.config.abstraction.GenesisConfigurationSection;
import mintychochip.genesis.container.ItemData;
import mintychochip.genesis.container.items.interfaces.Appraisable;
import mintychochip.genesis.container.items.interfaces.Embeddable;
import mintychochip.genesis.util.Rarity;
import mintychochip.genesis.util.Serializer;
import mintychochip.ollivanders.items.enums.CoreType;
import mintychochip.ollivanders.items.util.ComponentUtil;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class WandData extends ItemData implements Embeddable, Appraisable {
    private static final NamespacedKey key = Genesis.getKey("wand");
    private final WandBoost wandBoost = new WandBoost().addAll(1);
    private final CoreType coreType;
    private final List<ComponentData> componentData = new ArrayList<>();

    private Rarity rarity;

    public WandData(GenesisConfigurationSection main, List<ItemStack> materials) {
        coreType = Enum.valueOf(CoreType.class, main.getPath().toUpperCase());
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

    public WandBoost getWandBoost() {
        return wandBoost;
    }

    public CoreType getCoreType() {
        return coreType;
    }

    public String toString() {
        return wandBoost.toString() + " " + coreType + " " + componentData + " " + rarity.toString();
    }


    @Override
    public NamespacedKey getKey() {
        return key;
    }

    @Override
    public void setRarity(Rarity rarity) {
        this.rarity = rarity;
    }

}
