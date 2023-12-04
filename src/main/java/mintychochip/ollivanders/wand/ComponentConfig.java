package mintychochip.ollivanders.wand;

import mintychochip.ollivanders.ConfigReader;
import mintychochip.ollivanders.wand.container.WandBoost;
import mintychochip.ollivanders.wand.enums.WandModifier;
import mintychochip.ollivanders.ConfigReader;
import mintychochip.ollivanders.wand.container.WandBoost;
import mintychochip.ollivanders.wand.enums.WandModifier;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;

import java.io.Serializable;
import java.util.List;

public class ComponentConfig implements Serializable {
    private final String materialsPath = "materials";
    private final String customComponentPath = "custom-items";
    private final String coreTypeMarker = "core-type"; //can be managed from general config
    private final ConfigReader configReader;
    private Material defaultMaterial;
    private ConfigurationSection main;

    public ComponentConfig(String fileName) {
        configReader = new ConfigReader(fileName);
    }

    public Material getDefaultMaterial() {
        return defaultMaterial;
    }

    public void setMain(String itemPath, boolean material) {
        if (material) {
            main = configReader.getConfigurationSection(materialsPath + "." + itemPath);
            defaultMaterial = Enum.valueOf(Material.class, itemPath);
            return;
        }

        main = configReader.getConfigurationSection(customComponentPath + "." + itemPath);
        Bukkit.broadcastMessage("here");
        defaultMaterial = getEnumFromSection(Material.class, "material");
    }

    public <E extends Enum<E>> E getEnumFromSection(Class<E> enumClass, String marker) {
        String unknown = main.getString(marker);
        if (unknown == null) {
            return Enum.valueOf(enumClass,"DEFAULT");
        }
        if(!isInEnum(unknown.toUpperCase(),enumClass)) {
            throw new IllegalArgumentException();
        }
        Bukkit.broadcastMessage("here");
        return Enum.valueOf(enumClass, unknown.toUpperCase());
    }

    public List<String> getDefaultComponentLore(String marker) {
        return main.getStringList(marker);
    }

    public ConfigurationSection getConfigurationSection(String path) {
        return configReader.getConfigurationSection(path);
    }

    public int getDefaultCustomModelData(String marker) {
        return main.getString(marker) != null ? main.getInt(marker) : -1;
    }

    public String getStringAtMarker(String marker) {
        return main.getString(marker);
    }

    public WandBoost getDefaultWandBoost(String marker) {
        WandBoost wandBoost = new WandBoost();

        ConfigurationSection modifiers = main.getConfigurationSection(marker);
        if (modifiers != null) {
            for (String key : modifiers.getKeys(false)) {
                if (isInEnum(key.toUpperCase(), WandModifier.class)) {
                    switch (Enum.valueOf(WandModifier.class, key.toUpperCase())) {
                        case RANGE -> wandBoost.setRange(wandBoost.getRange() + modifiers.getDouble(key));
                        case EFFICIENCY ->
                                wandBoost.setEfficiency(wandBoost.getEfficiency() + modifiers.getDouble(key));
                        case DURATION -> wandBoost.setDuration(wandBoost.getDuration() + modifiers.getDouble(key));
                        case MAGNITUDE -> wandBoost.setMagnitude(wandBoost.getMagnitude() + modifiers.getDouble(key));
                        case HASTE -> wandBoost.setHaste(wandBoost.getHaste() + modifiers.getDouble(key));
                    }
                }
            }
        }
        return wandBoost;
    }

    public <E extends Enum<E>> boolean isInEnum(String value, Class<E> enumClass) {
        for (E e : enumClass.getEnumConstants()) {
            if (e.name().equals(value)) {
                return true;
            }
        }
        return false;
    }
}
