package mintychochip.ollivanders.betterwand;

import mintychochip.ollivanders.util.Keys;
import mintychochip.ollivanders.util.Serializer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.io.IOException;
import java.util.*;

public class WandBuilder {

    private ItemStack itemStack = new ItemStack(Material.BLAZE_ROD);

    private final WandBoost wandBoost = new WandBoost();

    private Map<ItemStack, ComponentType> mappedComponents = new HashMap<>();

    public WandBuilder(List<ItemStack> materials) {
        for (ItemStack material : materials) {
            ComponentType componentType = ComponentRegistry.getMaterialComponentType().get(material.getType());
            mappedComponents.put(material, componentType);
        }
        for (ItemStack stack : mappedComponents.keySet()) {
            if (mappedComponents.get(stack) == ComponentType.CORE) {
                itemStack.getItemMeta().getPersistentDataContainer().set(Keys.getCore(), PersistentDataType.STRING, stack.getType().toString());
            }
        }

    }

    public void addBoost(WandBoost wandBoost) {
        this.wandBoost.addAll(wandBoost);
    }
    public WandBuilder addLore(List<String> description) {
        List<String> lore = description != null ? new ArrayList<>(description) : new ArrayList<>();
        lore.add(ChatColor.GOLD + "Duration: " + ChatColor.GRAY + wandBoost.getDuration() + "x multiplier");
        lore.add(ChatColor.GOLD + "Drain: " + ChatColor.GRAY + wandBoost.getCost() + "x multiplier");
        lore.add(ChatColor.GOLD + "Range: " + ChatColor.GRAY + wandBoost.getRange() + "x multiplier");
        lore.add(ChatColor.GOLD + "Power: " + ChatColor.GRAY + wandBoost.getPower() + "x multiplier");
        lore.add(ChatColor.GOLD + "Cooldown: " + ChatColor.GRAY + wandBoost.getCooldown() + "x multiplier");
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

    public WandBuilder addName(String name) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(ChatColor.WHITE + name);
        itemStack.setItemMeta(itemMeta);
        return this;
    }

    public WandBuilder addAllBoosts() {
        for (ItemStack stack : mappedComponents.keySet()) {
            addBoost(stack);
        }
        wandBoost.addCost(1).addRange(1).addCooldown(1).addDuration(1).addPower(1);
        try {
            ItemMeta itemMeta = itemStack.getItemMeta();
            itemMeta.getPersistentDataContainer().set(Keys.getBoost(), PersistentDataType.BYTE_ARRAY, Serializer.serializeObject(wandBoost));
            itemStack.setItemMeta(itemMeta);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return this;
    }

    public ItemStack build() {
        return itemStack;
    }
}
