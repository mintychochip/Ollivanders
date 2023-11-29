package mintychochip.ollivanders.betterwand.builder;

import mintychochip.ollivanders.Ollivanders;
import mintychochip.ollivanders.betterwand.*;
import mintychochip.ollivanders.betterwand.container.ComponentData;
import mintychochip.ollivanders.betterwand.container.WandData;
import mintychochip.ollivanders.betterwand.core.Core;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WandBuilder extends ItemBuilder {

    private final ComponentConfig config;
    private WandBoost wandBoost;
    private final WandData wandData = new WandData();

    private ItemStack coreItem;
    private Core coreType;

    private final Map<ItemStack, ComponentType> mappedComponents = new HashMap<>();

    public WandBuilder(List<ItemStack> materials, Material material) {
        super(material);
        wandData.setMaterial(material);
        config = Ollivanders.getWandConfig();

        for (ItemStack itemStack : materials) {
            ItemMeta itemMeta = itemStack.getItemMeta();
            if (itemMeta.getPersistentDataContainer().has(Keys.getComponentData(), PersistentDataType.BYTE_ARRAY)) {
                byte[] bytes = itemMeta.getPersistentDataContainer().get(Keys.getComponentData(), PersistentDataType.BYTE_ARRAY);
                try {
                    ComponentData componentData = Serializer.deserializeComponent(bytes);
                    mappedComponents.put(itemStack, componentData.getType());
                    if (componentData.hasCore()) {
                        coreItem = itemStack;
                        coreType = componentData.getCore();
                    }

                } catch (IOException | ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            } else {
                ComponentType componentType = ComponentRegistry.getMaterialComponentData().get(itemStack.getType()).getType();
                mappedComponents.put(itemStack, componentType);
                if (componentType == ComponentType.CORE) {
                    coreItem = itemStack;
                    coreType = Core.valueOf(itemStack.getType().toString());
                }
            }
        }
    }
    //add auto

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
                wandBoost.addAll(ComponentRegistry.getMaterialComponentData().get(itemStack.getType()).getWandBoost());
            }
        }
    }

    public WandBuilder setUnstackable() {
        return (WandBuilder) super.setUnstackable();
    }

    public WandBuilder setCustomModelData(int integer) {
        return (WandBuilder) super.setCustomModelData(integer);
    }

    public WandBuilder getDefaultLore() {
        List<String> lore;
        if (containsComponentData(coreItem)) {
            try {
                ComponentData componentData = Serializer.deserializeComponent(coreItem.getItemMeta().getPersistentDataContainer().get(Keys.getComponentData(), PersistentDataType.BYTE_ARRAY));
                lore = componentData.getLore();
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        } else {
            lore = ComponentRegistry.getMaterialComponentData().get(coreItem.getType()).getLore();
        }
        for (String string : lore) { //add color to list function
            string = ChatColor.DARK_GRAY + string;
            addLore(string);
        }
        addLore("");
        return this;
    }

    public WandBuilder addLore(List<String> description) {
        return (WandBuilder) super.addLore(description);
    }

    public WandBuilder addLore(String string) {
        return (WandBuilder) super.addLore(string);
    }

    public ItemStack getDefaultBuild() {
        return this.getDefaultLore()
                .addAllBoosts()
                .getDefaultDisplayName()
                .setUnstackable()
                .getDefaultCustomModelData().addStatLore()
                .build();
    }

    public WandBuilder addAllBoosts() {
        wandBoost = new WandBoost();
        for (ItemStack stack : mappedComponents.keySet()) {
            addWandBoostData(stack);
        }
        wandBoost.addCost(1).addRange(1).addHaste(1).addDuration(1).addPower(1);
        wandData.setWandBoost(wandBoost);
        return this;
    }

    public ItemStack findComponent(ComponentType type) {
        for (ItemStack stack : mappedComponents.keySet()) {
            if (mappedComponents.get(stack) == type) {
                return stack;
            }
        }
        return null;
    }

    public WandBuilder getDefaultCustomModelData() {
        int model = config.getConfigurationSection("core." + coreType.toString()).getInt("model");
        setCustomModelData(model);
        return this;
    }

    public String getTitle(ItemStack itemStack) {
        PersistentDataContainer persistentDataContainer = itemStack.getItemMeta().getPersistentDataContainer();
        if (persistentDataContainer.has(Keys.getComponentData(), PersistentDataType.BYTE_ARRAY)) {
            try {
                ComponentData componentData = Serializer.deserializeComponent(persistentDataContainer.get(Keys.getComponentData(), PersistentDataType.BYTE_ARRAY));
                return componentData.getTitle();
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        } else {
            return ComponentRegistry.getMaterialComponentData().get(itemStack.getType()).getTitle();
        }
    }

    public WandBuilder getDefaultDisplayName() {
        Rarity rarity;
        if (containsComponentData(coreItem)) {
            try {
                rarity = Serializer.deserializeComponent(coreItem.getItemMeta().getPersistentDataContainer().get(Keys.getComponentData(), PersistentDataType.BYTE_ARRAY)).getRarity();
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        } else {
            rarity = ComponentRegistry.getMaterialComponentData().get(coreItem.getType()).getRarity();
        }
        ChatColor byChar = ChatColor.getByChar(rarity.getColorCode());
        String displayName = byChar + String.format("%s %s of the %s",
                getTitle(findComponent(ComponentType.CRYSTAL)), getTitle(findComponent(ComponentType.STICK)), getTitle(findComponent(ComponentType.CORE)));
        setDisplayName(displayName);
        return this;
    }

    public WandBuilder setDisplayName(String displayName) {
        itemMeta.setDisplayName(displayName);
        wandData.setWandName(displayName);
        return this;
    }

    public WandBuilder addStatLore() {
        if (wandBoost != null) {
            String asterisk = ChatColor.GREEN + "*";
            addLore(asterisk + " Duration: " + ChatColor.WHITE + wandBoost.getDuration() + "x");
            addLore(asterisk + " Range: " + ChatColor.WHITE + wandBoost.getRange() + "x");
            addLore(asterisk + " Magnitude: " + ChatColor.WHITE + wandBoost.getPower() + "x");
            addLore(asterisk + " Efficiency: " + ChatColor.WHITE + wandBoost.getCost() + "x");
            addLore(asterisk + " Haste: " + ChatColor.WHITE + wandBoost.getHaste() + "x");
        }
        return this;
    }

    public WandBuilder addComponents() {
        if (mappedComponents != null) {
            for (ItemStack stack : mappedComponents.keySet()) {
                ItemMeta itemMeta = stack.getItemMeta();
                if (itemMeta.getPersistentDataContainer().has(Keys.getComponentData(), PersistentDataType.BYTE_ARRAY)) {
                    byte[] bytes = itemMeta.getPersistentDataContainer().get(Keys.getComponentData(), PersistentDataType.BYTE_ARRAY);
                    try {
                        ComponentData componentData = Serializer.deserializeComponent(bytes);
                        componentData.getName();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
        return null;
    }

    public static boolean containsComponentData(ItemStack itemStack) {
        return itemStack.getItemMeta().getPersistentDataContainer().has(Keys.getComponentData(), PersistentDataType.BYTE_ARRAY);
    }

    @Override
    public ItemStack build() {
        itemMeta.setLore(lore);
        try {
            itemMeta.getPersistentDataContainer().set(Keys.getWandData(), PersistentDataType.BYTE_ARRAY, Serializer.serializeWand(wandData));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }
}
