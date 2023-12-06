package mintychochip.ollivanders.wand;

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
            if (cc.isInEnum(Material.class, key)) {
                Material material = Enum.valueOf(Material.class, key);
                cc.setMain(material.toString(), true);
                componentData.put(material, new ComponentData()
                        .setComponentType(cc.enumFromSection(ComponentType.class, "type"))
                        .setCoreType(cc.enumFromSection(CoreType.class, "core"))
                        .setDisplayName(cc.getString("name"))
                        .setTitle(cc.getString("title"))
                        .setWandBoost(cc.getDefaultWandBoost("modifiers"))
                        .setRarity(cc.enumFromSection(Rarity.class, "rarity")));
            }
        }
    }

    public static Map<Material, ComponentData> getComponentData() {
        return componentData;
    }
}
