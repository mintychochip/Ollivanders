package mintychochip.ollivanders.betterwand;

import mintychochip.ollivanders.util.ConfigReader;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;

public class WandConfig {


    private final ConfigReader c;

    public WandConfig(String file) {
        c = new ConfigReader(file);
        registerMaterials();
    }

    public void registerMaterials() {
        ConfigurationSection configurationSection = c.getConfigurationSection("materials");
        for (String key : configurationSection.getKeys(false)) {
            registerMaterial(Material.valueOf(key));
        }
    }

    public void registerMaterial(Material material) {
        WandBoost wandBoost = new WandBoost();
        ConfigurationSection configurationSection = c.getConfigurationSection("materials." + material.toString());
        ConfigurationSection modifiers = configurationSection.getConfigurationSection("modifiers");
        if(modifiers != null) {
            for (String key : modifiers.getKeys(false)) {
                WandModifier wandModifier = WandModifier.valueOf(key.toUpperCase());
                switch (wandModifier) {
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
}
