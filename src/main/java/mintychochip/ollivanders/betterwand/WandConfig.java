package mintychochip.ollivanders.betterwand;

import mintychochip.ollivanders.util.ConfigReader;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;

public class WandConfig {

    private final ConfigReader c;

    public WandConfig(String file) {
        c = new ConfigReader(file);
        registerMaterials();
    }

    public void registerMaterials() { //this one too
        ConfigurationSection configurationSection = c.getConfigurationSection("materials");
        for (String key : configurationSection.getKeys(false)) {
            registerMaterial(Material.valueOf(key));
        }
    }

    public void registerMaterial(Material material) { //move these methods to the registry
        WandBoost wandBoost = new WandBoost();
        ConfigurationSection configurationSection = c.getConfigurationSection("materials." + material.toString());
        ConfigurationSection modifiers = configurationSection.getConfigurationSection("modifiers");
        if(modifiers != null) {
            for (String key : modifiers.getKeys(false)) {
                switch (getWandModifier(key)) {
                    case RANGE -> wandBoost.setRange(wandBoost.getRange() + modifiers.getDouble(key));
                    case COST -> wandBoost.setCost(wandBoost.getCost() + modifiers.getDouble(key));
                    case DURATION -> wandBoost.setDuration(wandBoost.getDuration() + modifiers.getDouble(key));
                    case POWER -> wandBoost.setPower(wandBoost.getPower() + modifiers.getDouble(key));
                    case HASTE -> wandBoost.setHaste(wandBoost.getHaste() + modifiers.getDouble(key));
                }
            }
        }
        String component = configurationSection.getString("type");
        ComponentRegistry.getMaterialComponentType().put(material,ComponentType.valueOf(component.toUpperCase().strip()));
        ComponentRegistry.getMaterialBoostMap().put(material,wandBoost);
    }
    public ConfigurationSection getConfigurationSection(String path) {
        return c.getConfigurationSection(path);
    }

    public WandModifier getWandModifier(String key) {
        return WandModifier.valueOf(key.toUpperCase());
    }

    public ConfigReader getC() {
        return c;
    }
}
