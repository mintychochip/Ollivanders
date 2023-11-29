package mintychochip.ollivanders.betterwand;

import mintychochip.ollivanders.Ollivanders;
import mintychochip.ollivanders.betterwand.container.MaterialComponentData;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;

import java.util.HashMap;
import java.util.Map;

public class ComponentRegistry {

    private static final Map<Material, MaterialComponentData> materialComponentData = new HashMap<>();
    private final ComponentConfig wc;

    public ComponentRegistry() {
        wc = Ollivanders.getWandConfig();
        registerMaterialComponents();
    }

    public static Map<Material, MaterialComponentData> getMaterialComponentData() {
        return materialComponentData;
    }

    public void registerMaterialComponents() {
        ConfigurationSection materials = wc.getConfigurationSection("materials");
        for (String key : materials.getKeys(false)) {
            wc.setConfigurationPath(Material.valueOf(key.toUpperCase()));
            materialComponentData.put(Material.valueOf(key.toUpperCase()), new MaterialComponentData()
                    .setWandBoost(wc.getWandBoost())
                    .setType(wc.getComponentType())
                    .setCore(wc.getCore())
                    .setRarity(wc.getRarity())
                    .setLore(wc.getWandLore())
                    .setLoreName(wc.getComponentName())
                    .setTitle(wc.getComponentTitle()));
        }
    }
}
