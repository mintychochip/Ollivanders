package mintychochip.ollivanders.wand;

import mintychochip.ollivanders.Ollivanders;
import mintychochip.ollivanders.wand.container.ComponentData;
import mintychochip.ollivanders.wand.enums.ComponentType;
import mintychochip.ollivanders.wand.enums.CoreType;
import mintychochip.ollivanders.wand.enums.Rarity;
import mintychochip.ollivanders.Ollivanders;
import mintychochip.ollivanders.wand.container.ComponentData;
import mintychochip.ollivanders.wand.enums.ComponentType;
import mintychochip.ollivanders.wand.enums.CoreType;
import mintychochip.ollivanders.wand.enums.Rarity;
import org.bukkit.Material;

import java.util.HashMap;
import java.util.Map;

public class ComponentRegistry {

    private static final Map<Material, ComponentData> componentData = new HashMap<>();
    public ComponentRegistry() {
        ComponentConfig cc = Ollivanders.getComponentConfig();

        for (String key : cc.getConfigurationSection("materials").getKeys(false)) {
            if(cc.isInEnum(key,Material.class)) {
                Material material = Enum.valueOf(Material.class, key);
                cc.setMain(material.toString(),true);
                componentData.put(material,new ComponentData()
                        .setComponentType(cc.getEnumFromSection(ComponentType.class, "type"))
                        .setCoreType(cc.getEnumFromSection(CoreType.class, "core"))
                        .setDisplayName(cc.getStringAtMarker("name"))
                        .setTitle(cc.getStringAtMarker("title"))
                        .setWandBoost(cc.getDefaultWandBoost("modifiers"))
                        .setRarity(cc.getEnumFromSection(Rarity.class,"rarity")));
            }
        }
    }
    public static Map<Material, ComponentData> getComponentData() {
        return componentData;
    }
}
