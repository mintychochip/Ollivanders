package mintychochip.ollivanders.items.config;

import mintychochip.ollivanders.Ollivanders;
import mintychochip.ollivanders.items.container.ComponentData;
import mintychochip.ollivanders.util.EnumUtil;
import org.bukkit.Material;

import java.util.HashMap;
import java.util.Map;

public class ComponentRegistry {

    private static final Map<Material, ComponentData> componentData = new HashMap<>();

    public ComponentRegistry() {
        ComponentConfig cc = Ollivanders.getComponentConfig();
        for (String key : cc.getConfigReader().getConfigurationSection("materials").getKeys(false)) {
            if (EnumUtil.isInEnum(Material.class, key)) {
                Material material = Enum.valueOf(Material.class, key);
                componentData.put(material, new ComponentData(cc.getMainConfigurationSection(key, true)));
            }
        }
    }

    public static Map<Material, ComponentData> getComponentData() {
        return componentData;
    }
}
