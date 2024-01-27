package mintychochip.ollivanders.items.container;


import mintychochip.genesis.container.ItemData;
import mintychochip.genesis.util.Rarity;
import mintychochip.ollivanders.items.enums.ComponentType;
import mintychochip.ollivanders.items.enums.CoreType;
import mintychochip.ollivanders.util.OllivandersConfigMarker;

public class ComponentData extends ItemData { //holds all wand related objects
    private static final long serialVersionUID = 120594823L;
    private String title;
    private WandBoost wandBoost;
    private ComponentType componentType;
    private CoreType coreType;
    public ComponentData(ComponentConfigurationSection main) {
        super("items");
        this.setComponentType(main.enumFromSection(ComponentType.class,"component-type"))
                .setWandBoost(main.getDefaultWandBoost("modifiers"))
                .setTitle(main.getString("title"));
        if (componentType == ComponentType.CORE) {
            this.setCoreType(main.enumFromSection(CoreType.class, OllivandersConfigMarker.core_type.toUpperCase()));
        }
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
    public String toString() {
        return String.format("%s %s %s", title,wandBoost.toString(),componentType.toString());
    }

}
