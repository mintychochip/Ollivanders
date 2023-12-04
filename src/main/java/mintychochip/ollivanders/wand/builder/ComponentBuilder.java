package mintychochip.ollivanders.wand.builder;

import mintychochip.genesis.Genesis;
import mintychochip.genesis.builder.ItemBuilder;
import mintychochip.genesis.container.AbstractItem;
import mintychochip.ollivanders.Ollivanders;
import mintychochip.ollivanders.wand.ComponentConfig;
import mintychochip.ollivanders.wand.Serializer;
import mintychochip.ollivanders.wand.container.ComponentData;
import mintychochip.ollivanders.wand.enums.ComponentType;
import mintychochip.ollivanders.wand.container.WandBoost;
import mintychochip.ollivanders.wand.enums.CoreType;
import mintychochip.ollivanders.wand.enums.Rarity;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.io.IOException;
import java.util.List;

public class ComponentBuilder extends ItemBuilder {
    protected final ComponentData componentData = new ComponentData();

    private ComponentConfig cc = Ollivanders.getComponentConfig();

    public ComponentBuilder(AbstractItem abstractItem) {
        super(abstractItem);
    }
    public ComponentBuilder setTitle(String title) {
        componentData.setTitle(title);
        return this;
    }
    public ComponentBuilder setDisplayName(String displayName, boolean useColorCodes) {
        String finalString = "";
        if(useColorCodes) {
            componentData.setDisplayName(finalString += ChatColor.getByChar(
                    cc.getEnumFromSection(Rarity.class, "rarity").getColorCode()) + displayName);
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
        ComponentType type = cc.getEnumFromSection(ComponentType.class, "type"); //should work
        ComponentBuilder componentBuilder = this.setCustomModelData(cc.getDefaultCustomModelData("model"))
                .setRarity(cc.getEnumFromSection(Rarity.class,"rarity"))
                .setComponentType(type)
                .setDisplayName(cc.getStringAtMarker("display-name"),true)
                .setTitle(cc.getStringAtMarker("title"))
                .setWandBoost(cc.getDefaultWandBoost("modifiers"))
                .addLore(cc.getDefaultComponentLore("lore"));
        if(type == ComponentType.CORE) {
            componentBuilder.setCoreType(cc.getEnumFromSection(CoreType.class,"core"));
        }
        return componentBuilder.build();
    }

    @Override
    public ItemStack build() {
        ItemStack itemStack = abstractItem.getItemStack();
        try {
            abstractItem.getItemMeta().getPersistentDataContainer().set(Genesis.getKeys()
                    .getMap()
                    .get("items"), PersistentDataType.BYTE_ARRAY, Serializer.<ComponentData>serialize(componentData));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        itemStack.setItemMeta(abstractItem.getItemMeta());
        return itemStack;
    }
}
