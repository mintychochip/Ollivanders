package mintychochip.ollivanders.betterwand.builder;

import mintychochip.ollivanders.Ollivanders;
import mintychochip.ollivanders.betterwand.ComponentConfig;
import mintychochip.ollivanders.betterwand.ComponentType;
import mintychochip.ollivanders.betterwand.Rarity;
import mintychochip.ollivanders.betterwand.WandBoost;
import mintychochip.ollivanders.betterwand.container.ComponentData;
import mintychochip.ollivanders.betterwand.core.Core;
import mintychochip.ollivanders.util.Keys;
import mintychochip.ollivanders.util.Serializer;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.io.IOException;
import java.util.List;

public class CustomComponentBuilder extends ItemBuilder { //migrate all methods to the config because they shouldnt be fetching

    private final ComponentConfig componentConfig = Ollivanders.getWandConfig();
    private final ComponentData componentData;
    private final String mainPath;
    private final Material defaultMaterial = componentConfig.getCustomComponentMaterial();
    private boolean useDefaultMaterial = false;

    public CustomComponentBuilder(Material material, String itemPath) {

        super(material);

        this.mainPath = "custom-items." + itemPath;
        componentConfig.setConfigurationPath(itemPath,false);
        componentData = new ComponentData();
        if (useDefaultMaterial) {
            itemStack.setType(defaultMaterial);
            componentData.setMaterial(defaultMaterial);
        } else {
            componentData.setMaterial(material);
        }
    }

    public CustomComponentBuilder(String itemPath) {
        this(Material.STONE, itemPath);
        useDefaultMaterial = true;
    }

    public ItemStack defaultBuild() {
        CustomComponentBuilder customComponentBuilder = this.setDefaultComponentType()
                .setDefaultWandBoost()
                .setDefaultDisplayName()
                .setDefaultWandLore()
                .setDefaultItemLore();
        if (componentData != null && componentData.hasCore()) {
            customComponentBuilder.setDefaultTitle()
                    .setDefaultCore()
                    .setDefaultRarity();
        }
        return customComponentBuilder.build();
    }
    // could add a way to add rarity
    public CustomComponentBuilder setDefaultWandBoost() {
        setWandBoost(componentConfig.getWandBoost());
        return this;
    }
    public CustomComponentBuilder setWandBoost(WandBoost wandBoost) {
        componentData.setWandBoost(wandBoost);
        return this;
    }
    public CustomComponentBuilder setDefaultComponentType() {
        setComponentType(componentConfig.getComponentType());
        return this;
    }
    public CustomComponentBuilder setComponentType(ComponentType type) {
        componentData.setType(type);
        return this;
    }
    public CustomComponentBuilder setDefaultDisplayName() {
        setDisplayName(ChatColor.getByChar(componentConfig.getRarity().getColorCode()) + componentConfig.getComponentName());
        return this;
    }
    public CustomComponentBuilder setDisplayName(String name) {
        componentData.setName(name);
        itemMeta.setDisplayName(name);
        return this;
    }
    public CustomComponentBuilder setDefaultRarity() {
        setRarity(componentConfig.getRarity());
        return this;
    }
    public CustomComponentBuilder setRarity(Rarity rarity) {
        componentData.setRarity(rarity);
        return this;
    }
    public CustomComponentBuilder setDefaultWandLore() {
        componentData.setLore(componentConfig.getWandLore());
        return this;
    }
    public CustomComponentBuilder setDefaultItemLore() {
        setItemLore(componentConfig.getItemLore());
        return this;
    }
    public CustomComponentBuilder setItemLore(List<String> lore) {
        for (String s : lore) {
            addLore(ChatColor.GRAY + s);
        }
        addLore("");
        return this;
    }
    public CustomComponentBuilder addLore(String string) {
        return (CustomComponentBuilder) super.addLore(string);
    }
    public CustomComponentBuilder addLore(List<String> string) {
        return (CustomComponentBuilder) super.addLore(string);
    }
    public CustomComponentBuilder setDefaultCore() {
        setCore(componentConfig.getCore());
        return this;
    }
    public CustomComponentBuilder setCore(Core core) {
        componentData.setCore(core);
        return this;
    }
    public CustomComponentBuilder setDefaultTitle() {
        setTitle(componentConfig.getComponentTitle());
        return this;
    }
    public CustomComponentBuilder setTitle(String title) {
        componentData.setTitle(title);
        return this;
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
