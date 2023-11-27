package mintychochip.ollivanders.betterwand.builder;

import mintychochip.ollivanders.betterwand.ComponentRegistry;
import mintychochip.ollivanders.betterwand.ComponentType;
import mintychochip.ollivanders.betterwand.WandBoost;
import mintychochip.ollivanders.util.Keys;
import mintychochip.ollivanders.util.Serializer;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WandBuilder extends ItemBuilder {

    private final WandBoost wandBoost = new WandBoost();

    private final Map<ItemStack, ComponentType> mappedComponents = new HashMap<>();

    public WandBuilder(List<ItemStack> materials, Material material) {
        super(material);
        for (ItemStack itemStack : materials) {
            ComponentType componentType = ComponentRegistry.getMaterialComponentType().get(itemStack.getType());
            mappedComponents.put(itemStack, componentType);
        }
    }
    public void addBoost(WandBoost wandBoost) {
        this.wandBoost.addAll(wandBoost);
    }
    public WandBuilder addLore(List<String> description) {
        List<String> lore = description != null ? new ArrayList<>(description) : new ArrayList<>();
        lore.add(ChatColor.YELLOW + "* " + ChatColor.GOLD + "Duration: " + ChatColor.GRAY + wandBoost.getDuration() + "x");
        lore.add(ChatColor.YELLOW + "* " + ChatColor.GOLD + "Efficiency: " + ChatColor.GRAY + wandBoost.getCost() + "x");
        lore.add(ChatColor.YELLOW + "* " + ChatColor.GOLD + "Range: " + ChatColor.GRAY + wandBoost.getRange() + "x");
        lore.add(ChatColor.YELLOW + "* " + ChatColor.GOLD + "Power: " + ChatColor.GRAY + wandBoost.getPower() + "x");
        lore.add(ChatColor.YELLOW + "* " + ChatColor.GOLD + "Haste: " + ChatColor.GRAY + wandBoost.getHaste() + "x");
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);
        return this;
    }

    public void addBoost(ItemStack itemStack) {
        ComponentType componentType = mappedComponents.get(itemStack);
        if (componentType != ComponentType.CORE) {
            if (itemStack.getItemMeta() != null) {
                PersistentDataContainer persistentDataContainer = itemStack.getItemMeta().getPersistentDataContainer();
                if (persistentDataContainer.has(Keys.getBoost(), PersistentDataType.BYTE_ARRAY)) {
                    try {
                        this.wandBoost.addAll(Serializer.deserializeBoost(persistentDataContainer.get(Keys.getBoost(), PersistentDataType.BYTE_ARRAY)));
                    } catch (IOException | ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    this.wandBoost.addAll(ComponentRegistry.getMaterialBoostMap().get(itemStack.getType()));
                }
            }
        }
    }

    public WandBuilder addAllBoosts() {
        for (ItemStack stack : mappedComponents.keySet()) {
            addBoost(stack);
        }
        wandBoost.addCost(1).addRange(1).addHaste(1).addDuration(1).addPower(1);
        try {
            ItemMeta itemMeta = itemStack.getItemMeta();
            itemMeta.getPersistentDataContainer().set(Keys.getBoost(), PersistentDataType.BYTE_ARRAY, Serializer.serializeObject(wandBoost));
            itemStack.setItemMeta(itemMeta);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return this;
    }
}
