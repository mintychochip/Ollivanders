package mintychochip.ollivanders.wand.container;


import mintychochip.genesis.container.ItemData;
import mintychochip.ollivanders.wand.enums.ComponentType;
import mintychochip.ollivanders.wand.enums.CoreType;
import mintychochip.ollivanders.wand.enums.Rarity;

public class ComponentData extends ItemData { //holds all wand related objects
    private static final long serialVersionUID = 120594823L;
    private String title;
    private WandBoost wandBoost;
    private ComponentType componentType;
    private CoreType coreType;
    private Rarity rarity;
    public ComponentData(ComponentConfigurationSection main) {
        super("items");
        this.setComponentType(main.enumFromSection(ComponentType.class,"type"))
                .setRarity(main.enumFromSection(Rarity.class,"rarity"))
                .setWandBoost(main.getDefaultWandBoost("modifiers"))
                .setTitle(main.getString("title"));
    }
    public String getTitle() {
        return title;
    }

    public ComponentData setTitle(String title) {
        this.title = title;
        return this;
    }
    public ComponentData setDisplayName(String displayName) {
        return (ComponentData) super.setDisplayName(displayName);
    }

    public WandBoost getWandBoost() {
        return wandBoost;
    }

    public ComponentData setWandBoost(WandBoost wandBoost) {
        this.wandBoost = wandBoost;
        return this;
    }

    public ComponentType getComponentType() {
        return componentType;
    }

    public ComponentData setComponentType(ComponentType componentType) {
        this.componentType = componentType;
        return this;
    }

    public CoreType getCoreType() {
        return coreType;
    }

    public ComponentData setCoreType(CoreType coreType) {
        this.coreType = coreType;
        return this;
    }

    public Rarity getRarity() {
        return rarity;
    }

    public ComponentData setRarity(Rarity rarity) {
        this.rarity = rarity;
        return this;
    }
    public String toString() {
        return String.format("%s %s %s %s", title,wandBoost.toString(),componentType.toString(),rarity.toString());
    }

}
