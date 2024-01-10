package mintychochip.ollivanders.wand.config;

import mintychochip.genesis.config.GenericConfig;
import mintychochip.ollivanders.wand.container.ComponentConfigurationSection;
import mintychochip.ollivanders.wand.container.CustomComponentSection;
import mintychochip.ollivanders.wand.container.MaterialConfigurationSection;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.Serializable;

public class ComponentConfig extends GenericConfig implements Serializable {
    private final String materials = "materials";
    private final String custom = "custom-items";

    public ComponentConfig(String fileName, JavaPlugin plugin) {
        super(fileName, plugin);
    }

    public ComponentConfigurationSection getMainConfigurationSection(String itemPath, boolean material) {
        if (material) {
            return new MaterialConfigurationSection(configReader.getConfigurationSection(materials + "." + itemPath), itemPath);
        }
        return new CustomComponentSection(configReader.getConfigurationSection(custom + "." + itemPath), itemPath);
    }
}
