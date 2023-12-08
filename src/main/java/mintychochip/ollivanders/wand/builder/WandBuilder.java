package mintychochip.ollivanders.wand.builder;

import mintychochip.genesis.Genesis;
import mintychochip.genesis.builder.ItemBuilder;
import mintychochip.genesis.container.AbstractItem;
import mintychochip.ollivanders.Ollivanders;
import mintychochip.ollivanders.wand.config.WandConfig;
import mintychochip.ollivanders.wand.util.ComponentUtil;
import mintychochip.ollivanders.wand.Serializer;
import mintychochip.ollivanders.wand.container.ComponentData;
import mintychochip.ollivanders.wand.container.WandBoost;
import mintychochip.ollivanders.wand.container.WandData;
import mintychochip.ollivanders.wand.enums.ComponentType;
import mintychochip.ollivanders.wand.enums.CoreType;
import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.io.IOException;
import java.util.List;

public class WandBuilder extends ItemBuilder {
    private final WandConfig wc = Ollivanders.getWandConfig();
    private final List<ItemStack> materials;
    private final WandData wandData;

    public WandBuilder(AbstractItem abstractItem, List<ItemStack> materials) throws IOException {
        super(abstractItem);
        if (materials == null) {
            throw new IOException("Wand builder materials cannot be null!");
        }
        wandData = new WandData();
        this.materials = materials;
    }

    public ComponentData findComponentData(ComponentType componentType) {
        for (ItemStack material : materials) {
            ComponentData componentData = ComponentUtil.componentDataFromItemStack(material);
            if (componentData.getComponentType() == componentType) {
                return componentData;
            }
        }
        return null;
    }

    public String generateWandName() {
        return String.format("%s %s of the %s",
                findComponentData(ComponentType.CRYSTAL).getTitle(),
                findComponentData(ComponentType.STICK).getTitle(),
                findComponentData(ComponentType.CORE).getTitle());
    }

    public WandBuilder setWandBoost(WandBoost wandBoost) {
        wandData.setWandBoost(wandBoost);
        return this;
    }

    public WandBuilder setCustomModelData(int model) {
        Bukkit.broadcastMessage(model + "");
        return (WandBuilder) super.setCustomModelData(model);
    }

    public WandBuilder setDisplayName(String displayName) {
        wandData.setWandName(displayName);
        return (WandBuilder) super.setDisplayName(displayName);
    }

    public WandBuilder setCoreType(CoreType coreType) {
        wandData.setCoreType(coreType);
        return this;
    }

    public WandBuilder setWandLore(List<String> wandLore) {
        wandData.setWandLore(wandLore);
        return (WandBuilder) super.addLore(wandLore);
    }

    public WandBoost sumWandBoostCore(boolean withCore) {
        WandBoost result = new WandBoost();
        for (ItemStack material : materials) {
            ComponentData componentData = ComponentUtil.componentDataFromItemStack(material);
            if (withCore && ComponentType.CORE == componentData.getComponentType()) {
                result.addAll(componentData.getWandBoost());
            } else {
                result.addAll(componentData.getWandBoost());
            }
        }
        return result;
    }

    public WandBuilder setUnstackable(boolean unstackable) {
        return (WandBuilder) super.setUnstackable(unstackable);
    }

    public ItemStack defaultBuild() {
        return this.setWandBoost(sumWandBoostCore(true))
                .setWandLore(wc.getStringList("lore"))
                .setCoreType(Enum.valueOf(CoreType.class, wc.getPath().toUpperCase()))
                .setCustomModelData(wc.getInt("model"))
                .setDisplayName(generateWandName())
                .setUnstackable(true).build();
    }

    public WandData getWandData() {
        return wandData;
    }

    @Override
    public ItemStack build() {
        ItemStack itemStack = abstractItem.getItemStack();
        try {
            abstractItem.getItemMeta().getPersistentDataContainer().set(Genesis
                    .getKeys()
                    .getMap()
                    .get("wand"), PersistentDataType.BYTE_ARRAY, Serializer.serialize(wandData));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        itemStack.setItemMeta(abstractItem.getItemMeta());
        return itemStack;
    }
}
