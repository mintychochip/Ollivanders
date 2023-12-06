package mintychochip.ollivanders.wand;

import mintychochip.ollivanders.GenericConfig;
import mintychochip.ollivanders.wand.container.WandBoost;
import mintychochip.ollivanders.wand.enums.WandModifier;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;

import java.io.Serializable;

public class ComponentConfig extends GenericConfig implements Serializable {
    private final String materialsPath = "materials";
    private final String customComponentPath = "custom-items";
    private Material defaultMaterial;

    private String itemPath;
    public ComponentConfig(String fileName) {
        super(fileName);
    }

    public Material getDefaultMaterial() {
        return defaultMaterial;
    }

    public void setMain(String itemPath, boolean material) {
        if (material) {
            main = configReader.getConfigurationSection(materialsPath + "." + itemPath);
            defaultMaterial = Enum.valueOf(Material.class, itemPath.toUpperCase());
            return;
        }
        this.itemPath = itemPath;
        main = configReader.getConfigurationSection(customComponentPath + "." + itemPath);
        defaultMaterial = enumFromSection(Material.class, "material");
    }

    public WandBoost getDefaultWandBoost(String marker) {
        WandBoost wandBoost = new WandBoost();

        ConfigurationSection modifiers = main.getConfigurationSection(marker);
        if (modifiers != null) {
            for (String key : modifiers.getKeys(false)) {
                if (isInEnum(WandModifier.class, key.toUpperCase())) {
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

    public String getItemPath() {
        return itemPath;
    }
}
