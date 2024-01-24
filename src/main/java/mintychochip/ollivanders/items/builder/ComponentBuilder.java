package mintychochip.ollivanders.items.builder;

import mintychochip.genesis.Genesis;
import mintychochip.genesis.builder.ItemBuilder;
import mintychochip.genesis.container.AbstractItem;
import mintychochip.genesis.util.Serializer;
import mintychochip.ollivanders.items.container.ComponentConfigurationSection;
import mintychochip.ollivanders.items.container.ComponentData;
import mintychochip.ollivanders.items.enums.ComponentType;
import mintychochip.ollivanders.items.enums.CoreType;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.io.IOException;
import java.util.List;

public class ComponentBuilder extends ItemBuilder {
    protected final ComponentData componentData;

    private final ComponentConfigurationSection main;

    public ComponentBuilder(AbstractItem abstractItem, String theme, ComponentConfigurationSection main) {
        super(abstractItem, theme);
        this.componentData = new ComponentData(main);
        this.main = main;
    }

    public ComponentConfigurationSection getComponentConfigurationSection() {
        return main;
    }

    public ComponentBuilder setDisplayName(String displayName, char color) {
        return (ComponentBuilder) super.setDisplayName(displayName, color);
    }

    public ComponentBuilder setDisplayName(String displayName) {
        return (ComponentBuilder) super.setDisplayName(displayName, ChatColor.getByChar(componentData.getRarity().getColorCode()));
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

    //-----------------------------------------------------------
    public ItemStack defaultBuild() {
        ComponentBuilder componentBuilder = this.setDisplayName(main.getString("name"))
                .addLore(main.getStringList("lore"))
                .setCustomModelData(main.getInt("model"));
        if (componentData.getComponentType() == ComponentType.CORE) {
            componentData.setCoreType(Enum.valueOf(CoreType.class, main.getItemPath().toUpperCase()));
        }
        return componentBuilder.build();
    }

    @Override
    public ItemStack build() {
        ItemStack itemStack = abstractItem.getItemStack();
        try {
            abstractItem.getItemMeta().getPersistentDataContainer().set(Genesis.getKeys()
                    .getMap()
                    .get("items"), PersistentDataType.BYTE_ARRAY, Serializer.serialize(componentData));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        itemStack.setItemMeta(abstractItem.getItemMeta());
        return itemStack;
    }
}
