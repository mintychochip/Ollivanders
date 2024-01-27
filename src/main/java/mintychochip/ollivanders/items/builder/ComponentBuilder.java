package mintychochip.ollivanders.items.builder;

import mintychochip.genesis.Genesis;
import mintychochip.genesis.builder.ConfigurationItemBuilder;
import mintychochip.genesis.config.GenesisConfigurationSection;
import mintychochip.genesis.container.AbstractItem;
import mintychochip.genesis.util.Serializer;
import mintychochip.ollivanders.Ollivanders;
import mintychochip.ollivanders.items.container.ComponentConfigurationSection;
import mintychochip.ollivanders.items.container.ComponentData;
import mintychochip.ollivanders.items.enums.ComponentType;
import mintychochip.ollivanders.items.enums.CoreType;
import mintychochip.ollivanders.util.OllivandersConfigMarker;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.util.List;

public class ComponentBuilder extends ConfigurationItemBuilder {
    public ComponentBuilder(JavaPlugin instance, String genesisTheme, String itemKey) { //key from the item config
        this(instance,genesisTheme, Ollivanders.getItemConfig().getComponents().getConfigurationSection(itemKey));
    }
    public ComponentBuilder(JavaPlugin instance, String genesisTheme, GenesisConfigurationSection main) { //can be used to embed an item with different data
        super(instance, genesisTheme, main);
    }
    public ComponentBuilder(AbstractItem abstractItem, String genesisTheme, GenesisConfigurationSection main) {
        super(abstractItem,genesisTheme,main);
    }
    public ComponentBuilder setDisplayName(String displayName, char color) {
        return (ComponentBuilder) super.setDisplayName(displayName, color);
    }
    public ComponentBuilder setDisplayName(String displayName, ChatColor color) {
        return (ComponentBuilder) super.setDisplayName(displayName, color);
    }
    public ComponentBuilder setUnbreakable(boolean unbreakable) {
        return (ComponentBuilder) super.setUnbreakable(unbreakable);
    }
    public ComponentBuilder setUnstackable(boolean unstackable) {
        return (ComponentBuilder) super.setUnstackable(unstackable);
    }
    public ComponentBuilder addLore(List<String> text) {
        return (ComponentBuilder) super.addLore(text);
    }

    public ComponentBuilder addLore(String text) {
        return (ComponentBuilder) super.addLore(text);
    }

    public ComponentBuilder setCustomModelData(int model) {
        return (ComponentBuilder) super.setCustomModelData(model);
    }

    public ComponentBuilder addBulletedLore(String term, String text) {
        return (ComponentBuilder) super.addBulletedLore(term, text);
    }
    public ComponentBuilder defaultBuilder() {
        return (ComponentBuilder) super.defaultBuilder();
    }
    public ItemStack defaultBuild(String componentKey, boolean material) {
        return defaultBuilder().build(componentKey,material);
    }
    public ItemStack build(String componentKey, boolean material) {
        ItemStack itemStack = abstractItem.getItemStack();
        try {
            abstractItem.getItemMeta().getPersistentDataContainer().set(Genesis.getKeys()
                    .getMap()
                    .get("items"), PersistentDataType.BYTE_ARRAY, Serializer.serialize(new ComponentData(Ollivanders.getComponentConfig().getMainConfigurationSection(componentKey, material))));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        itemStack.setItemMeta(abstractItem.getItemMeta());
        return itemStack;
    }
}
