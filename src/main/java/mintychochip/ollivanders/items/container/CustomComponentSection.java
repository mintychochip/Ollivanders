package mintychochip.ollivanders.items.container;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;

public class CustomComponentSection extends ComponentConfigurationSection {
    public CustomComponentSection(ConfigurationSection configurationSection, String itemPath) {
        super(configurationSection, itemPath);
        defaultMaterial = enumFromSection(Material.class, "material");
    }
}
