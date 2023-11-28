package mintychochip.ollivanders.betterwand.builder;

import mintychochip.ollivanders.Ollivanders;
import mintychochip.ollivanders.betterwand.ComponentConfig;
import mintychochip.ollivanders.betterwand.ComponentType;
import mintychochip.ollivanders.betterwand.WandBoost;
import mintychochip.ollivanders.betterwand.container.ComponentData;
import mintychochip.ollivanders.betterwand.core.Core;
import mintychochip.ollivanders.util.Keys;
import mintychochip.ollivanders.util.Serializer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.io.IOException;

public class CustomComponentBuilder extends ItemBuilder { //migrate all methods to the config because they shouldnt be fetching

    private final ComponentConfig componentConfig;
    private final ComponentData componentData;
    private final String mainPath;

    public CustomComponentBuilder(Material material, String itemPath) {
        super(material);

        this.mainPath = "custom-items." + itemPath;
        componentConfig = Ollivanders.getWandConfig();
        componentData = new ComponentData();
        if (material == null) {
            String string = componentConfig.getConfigurationSection(mainPath).getString("material");
            if (string != null) {
                Material configMaterial = Material.valueOf(string.toUpperCase());
                itemStack = new ItemStack(configMaterial);
            }
        }
    }


    public CustomComponentBuilder setMaterial(Material material) {
        componentData.setMaterial(material);
        return this;
    }

    public ItemStack defaultCoreBuild() {
        try {
            return this.getComponentType()
                    .getDisplayName()
                    .getDefaultLore()
                    .getWandBoost()
                    .getCore()
                    .getTitle()
                    .build();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public ItemStack defaultBuild() {
        try {
            return this.getComponentType()
                    .getDisplayName()
                    .getDefaultLore()
                    .getWandBoost()
                    .build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public CustomComponentBuilder getComponentType() {
        componentData.setType(getComponent());
        return this;
    }

    public CustomComponentBuilder setComponentType(ComponentType type) {
        componentData.setType(type);
        return this;
    }

    public ComponentType getComponent() {
        String string = componentConfig.getConfigurationSection(mainPath).getString("type");
        ComponentType componentType = ComponentType.valueOf(string != null ? string.toUpperCase() : null);
        return componentType;
    }

    public CustomComponentBuilder getTitle() {
        String string = componentConfig.getConfigurationSection(mainPath).getString("title");
        Bukkit.broadcastMessage(string);
        componentData.setTitle(string);
        return this;
    }

    public CustomComponentBuilder getWandBoost() {
        WandBoost wandBoost = new WandBoost();
        ConfigurationSection configurationSection = componentConfig.getConfigurationSection(mainPath + ".modifiers");
        if (configurationSection != null) {
            for (String key : configurationSection.getKeys(false)) {
                switch (componentConfig.getWandModifier(key)) {
                    case COST -> wandBoost.addCost(configurationSection.getDouble(key));
                    case HASTE -> wandBoost.addHaste(configurationSection.getDouble(key));
                    case POWER -> wandBoost.addPower(configurationSection.getDouble(key));
                    case RANGE -> wandBoost.addRange(configurationSection.getDouble(key));
                    case DURATION -> wandBoost.addDuration(configurationSection.getDouble(key));
                }
            }
        }
        componentData.setWandBoost(wandBoost);
        return this;
    }

    public CustomComponentBuilder getDisplayName() {
        String string = componentConfig.getConfigurationSection(mainPath).getString("name");
        componentData.setName(string);
        itemMeta.setDisplayName(string);
        return this;
    }

    public CustomComponentBuilder setDisplayName(String name) {
        componentData.setName(name);
        itemMeta.setDisplayName(name);
        return this;
    }

    public CustomComponentBuilder addLore(String string) {
        return (CustomComponentBuilder) super.addLore(string);
    }

    public CustomComponentBuilder getDefaultLore() {
        for (String string : componentConfig.getConfigurationSection(mainPath).getStringList("lore")) { //add color to list function
            string = ChatColor.DARK_GRAY + string;
            addLore(string);
        }
        addLore("");
        return this;
    }

    public CustomComponentBuilder getCore() throws IOException {
        if (!isCore()) {
            throw new IOException("Item path is missing a core component");
        }
        String string = componentConfig.getConfigurationSection(mainPath).getString("core");
        if (string != null) {
            componentData.setCore(Core.valueOf(string.toUpperCase()));
        }
        return this;
    }

    public boolean isCore() {
        return componentData.getType() == ComponentType.CORE || getComponent() == ComponentType.CORE;
    }

    @Override
    public ItemStack build() {
        itemMeta.setLore(lore);
        try {
            itemMeta.getPersistentDataContainer().set(Keys.getComponentData(), PersistentDataType.BYTE_ARRAY, Serializer.serializeComponent(componentData));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }
}
