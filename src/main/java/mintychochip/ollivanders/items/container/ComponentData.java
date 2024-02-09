package mintychochip.ollivanders.items.container;


import mintychochip.genesis.container.ItemData;
import mintychochip.genesis.util.Rarity;
import mintychochip.ollivanders.items.enums.ComponentType;
import mintychochip.ollivanders.items.enums.CoreType;
import mintychochip.ollivanders.util.OllivandersConfigMarker;

import java.io.Serial;

public class ComponentData extends ItemData { //holds all wand related objects
    @Serial
    private static final long serialVersionUID = 120594823L;
    private String title;
    private WandBoost wandBoost;
    private ComponentType componentType;
    private CoreType coreType;
    public ComponentData(ComponentConfigurationSection main) {
        super("items");
        this.componentType = main.enumFromSection(ComponentType.class, OllivandersConfigMarker.component_type);
        this.wandBoost = main.getDefaultWandBoost("modifiers");
        this.title = main.getString("title");
        if (componentType == ComponentType.CORE) {
            this.coreType = main.enumFromSection(CoreType.class, OllivandersConfigMarker.core_type.toUpperCase());
        }
    }
    public String getTitle() {
        return title;
    }
    public WandBoost getWandBoost() {
        return wandBoost;
    }
    public ComponentType getComponentType() {
        return componentType;
    }
    public CoreType getCoreType() {
        return coreType;
    }
    public String toString() {
        return String.format("%s %s %s", title,wandBoost.toString(),componentType.toString());
    }

}
