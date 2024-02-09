package mintychochip.ollivanders.items.config;

import mintychochip.genesis.config.abstraction.GenericConfig;
import mintychochip.ollivanders.items.container.ComponentConfigurationSection;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.java.JavaPlugin;

public class ComponentConfig extends GenericConfig {
    private final ConfigurationSection materials = configReader.getConfigurationSection("materials");
    private final ConfigurationSection custom = configReader.getConfigurationSection("custom-items");

    public ComponentConfig(String fileName, JavaPlugin plugin) {
        super(fileName, plugin);
    }

    public ComponentConfigurationSection getMainConfigurationSection(String itemPath, boolean material) {
        if (material) {
            return new ComponentConfigurationSection(materials.getConfigurationSection(itemPath.toUpperCase()), itemPath.toUpperCase());
        }
        return new ComponentConfigurationSection(custom.getConfigurationSection(itemPath), itemPath);
    }

    public ConfigurationSection getMaterials() {
        return materials;
    }

    public ConfigurationSection getCustom() {
        return custom;
    }
}
