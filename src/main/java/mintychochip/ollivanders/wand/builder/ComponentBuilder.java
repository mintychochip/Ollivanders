package mintychochip.ollivanders.wand.builder;

import mintychochip.genesis.Genesis;
import mintychochip.genesis.builder.ItemBuilder;
import mintychochip.genesis.container.AbstractItem;
import mintychochip.ollivanders.Ollivanders;
import mintychochip.ollivanders.wand.ComponentConfig;
import mintychochip.ollivanders.wand.Serializer;
import mintychochip.ollivanders.wand.container.ComponentData;
import mintychochip.ollivanders.wand.container.WandBoost;
import mintychochip.ollivanders.wand.enums.ComponentType;
import mintychochip.ollivanders.wand.enums.CoreType;
import mintychochip.ollivanders.wand.enums.Rarity;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.io.IOException;
import java.util.List;

public class ComponentBuilder extends ItemBuilder {
    protected final ComponentData componentData = new ComponentData();

    private final ComponentConfig cc = Ollivanders.getComponentConfig();

    public ComponentBuilder(AbstractItem abstractItem) {
        super(abstractItem);
    }

    public ComponentBuilder setTitle(String title) {
        componentData.setTitle(title);
        return this;
    }

    public ComponentBuilder setDisplayName(String displayName, boolean useColorCodes) {
        String finalString = "";
        if (useColorCodes) {
            componentData.setDisplayName(finalString += ChatColor.getByChar(
                    cc.enumFromSection(Rarity.class, "rarity").getColorCode()) + displayName);
        } else {
            componentData.setDisplayName(finalString = displayName);
        }
        return (ComponentBuilder) super.setDisplayName(finalString);
    }

    public ComponentBuilder setDisplayName(String displayName) {
        componentData.setDisplayName(displayName);
        return (ComponentBuilder) super.setDisplayName(displayName);
    }

    public ComponentBuilder setComponentType(ComponentType type) {
        componentData.setComponentType(type);
        return this;
    }

    public ComponentBuilder setWandBoost(WandBoost wandBoost) {
        componentData.setWandBoost(wandBoost);
        return this;
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

    public ComponentBuilder setCoreType(CoreType type) {
        componentData.setCoreType(type);
        return this;
    }

    public ComponentBuilder setRarity(Rarity rarity) {
        componentData.setRarity(rarity);
        return this;
    }

    //-----------------------------------------------------------
    public ItemStack defaultBuild() {
        ComponentType type = cc.enumFromSection(ComponentType.class, "type"); //should work
        ComponentBuilder componentBuilder = this.setCustomModelData(cc.getInt("model"))
                .setRarity(cc.enumFromSection(Rarity.class, "rarity"))
                .setComponentType(type)
                .setDisplayName(cc.getString("name"), true)
                .setTitle(cc.getString("title"))
                .setWandBoost(cc.getDefaultWandBoost("modifiers"))
                .addLore(cc.getStringList("lore"));
        if (type == ComponentType.CORE) {
            componentBuilder.setCoreType(Enum.valueOf(CoreType.class,cc.getItemPath().toUpperCase()));
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
