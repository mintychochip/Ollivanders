package mintychochip.ollivanders.wand.config;

import mintychochip.genesis.config.GenericConfig;
import mintychochip.ollivanders.wand.container.ComponentConfigurationSection;
import mintychochip.ollivanders.wand.container.CustomComponentSection;
import mintychochip.ollivanders.wand.container.MaterialConfigurationSection;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.Serializable;

public class ComponentConfig extends GenericConfig implements Serializable {
    private final ConfigurationSection materials = configReader.getConfigurationSection("materials");
    private final ConfigurationSection custom = configReader.getConfigurationSection("custom-items");

    public ComponentConfig(String fileName, JavaPlugin plugin) {
        super(fileName, plugin);
    }

    public ComponentConfigurationSection getMainConfigurationSection(String itemPath, boolean material) {
        if (material) {
            return new MaterialConfigurationSection(materials.getConfigurationSection(itemPath.toUpperCase()), itemPath.toUpperCase());
        }
        return new CustomComponentSection(custom.getConfigurationSection(itemPath), itemPath);
    }

    public ConfigurationSection getMaterials() {
        return materials;
    }

    public ConfigurationSection getCustom() {
        return custom;
    }
}
