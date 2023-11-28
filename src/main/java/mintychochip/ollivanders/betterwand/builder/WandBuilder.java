package mintychochip.ollivanders.betterwand.builder;

import mintychochip.ollivanders.betterwand.ComponentRegistry;
import mintychochip.ollivanders.betterwand.ComponentType;
import mintychochip.ollivanders.betterwand.WandBoost;
import mintychochip.ollivanders.betterwand.container.ComponentData;
import mintychochip.ollivanders.betterwand.container.WandData;
import mintychochip.ollivanders.util.Keys;
import mintychochip.ollivanders.util.Serializer;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WandBuilder extends ItemBuilder {

    private final WandBoost wandBoost = new WandBoost();
    private final WandData wandData = new WandData();

    private final Map<ItemStack, ComponentType> mappedComponents = new HashMap<>();

    public WandBuilder(List<ItemStack> materials, Material material) {
        super(material);
        wandData.setMaterial(material);

        for (ItemStack itemStack : materials) {
            ComponentType componentType = ComponentRegistry.getMaterialComponentType().get(itemStack.getType());
            mappedComponents.put(itemStack, componentType);
        }
    } //add auto

    private void addWandBoostData(ItemStack itemStack) {
        if (itemStack.getItemMeta() != null) {
            PersistentDataContainer persistentDataContainer = itemStack.getItemMeta().getPersistentDataContainer();
            if (persistentDataContainer.has(Keys.getComponentData(), PersistentDataType.BYTE_ARRAY)) {
                try {
                    ComponentData componentData = Serializer.deserializeComponent(persistentDataContainer.get(Keys.getComponentData(), PersistentDataType.BYTE_ARRAY));
                    wandBoost.addAll(componentData.getWandBoost());
                } catch (IOException | ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            } else {
                wandBoost.addAll(ComponentRegistry.getMaterialBoostMap().get(itemStack.getType()));
            }
        }
    }

    public WandBuilder setUnstackable() {
        return (WandBuilder) super.setUnstackable();
    }

    public WandBuilder setCustomModelData(int integer) {
        return (WandBuilder) super.setCustomModelData(integer);
    }

    public WandBuilder addLore(List<String> description) {
        return (WandBuilder) super.addLore(description);
    }
    public WandBuilder addAllBoosts() {
        for (ItemStack stack : mappedComponents.keySet()) {
            addWandBoostData(stack);
        }
        wandBoost.addCost(1).addRange(1).addHaste(1).addDuration(1).addPower(1);
        wandData.setWandBoost(wandBoost);
        return this;
    }

    public WandBuilder setDisplayName(String displayName) {
        itemMeta.setDisplayName(displayName);
        wandData.setWandName(displayName);
        return this;
    }

    @Override
    public ItemStack build() {
        itemMeta.setLore(lore);
        try {
            itemMeta.getPersistentDataContainer().set(Keys.getComponentData(), PersistentDataType.BYTE_ARRAY, Serializer.serializeWand(wandData));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }
}
