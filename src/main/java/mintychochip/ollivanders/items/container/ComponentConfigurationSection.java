package mintychochip.ollivanders.items.container;

import mintychochip.genesis.config.abstraction.GenesisConfigurationSection;
import mintychochip.ollivanders.items.enums.WandModifier;
import mintychochip.ollivanders.util.EnumUtil;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;

public class ComponentConfigurationSection extends GenesisConfigurationSection {
    protected final String itemPath;

    public ComponentConfigurationSection(ConfigurationSection configurationSection, String itemPath) {
        super(configurationSection, itemPath);
        this.itemPath = itemPath;
    }

    public String getItemPath() {
        return itemPath;
    }

    public WandBoost getDefaultWandBoost(String marker) {
        WandBoost wandBoost = new WandBoost();
        ConfigurationSection modifiers = main.getConfigurationSection(marker);
        if (modifiers != null) {
            for (String key : modifiers.getKeys(false)) {
                if (EnumUtil.isInEnum(WandModifier.class, key.toUpperCase())) {
                    switch (Enum.valueOf(WandModifier.class, key.toUpperCase())) {
                        case RANGE -> wandBoost.setRange(wandBoost.getRange() + modifiers.getDouble(key));
                        case EFFICIENCY -> wandBoost.setEfficiency(wandBoost.getEfficiency() + modifiers.getDouble(key));
                        case DURATION -> wandBoost.setDuration(wandBoost.getDuration() + modifiers.getDouble(key));
                        case MAGNITUDE -> wandBoost.setMagnitude(wandBoost.getMagnitude() + modifiers.getDouble(key));
                        case HASTE -> wandBoost.setHaste(wandBoost.getHaste() + modifiers.getDouble(key));
                    }
                }
            }
        }
        return wandBoost;
    }
}
