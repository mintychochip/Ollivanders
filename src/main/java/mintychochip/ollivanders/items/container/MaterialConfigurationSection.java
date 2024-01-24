package mintychochip.ollivanders.items.container;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;

public class MaterialConfigurationSection extends ComponentConfigurationSection {
    public MaterialConfigurationSection(ConfigurationSection configurationSection, String itemPath) {
        super(configurationSection, itemPath);
        defaultMaterial = Enum.valueOf(Material.class,itemPath.toUpperCase());
    }
}
