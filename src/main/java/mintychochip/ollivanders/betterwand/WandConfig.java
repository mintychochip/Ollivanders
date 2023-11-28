package mintychochip.ollivanders.betterwand;

import mintychochip.ollivanders.betterwand.core.Core;
import mintychochip.ollivanders.util.ConfigReader;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;

import java.io.IOException;

public class WandConfig {

    private final ConfigReader c;

    public WandConfig(String file) {
        c = new ConfigReader(file);
        registerMaterials();
        registerLore();
    }

    public void registerMaterials() { //this one too
        ConfigurationSection configurationSection = c.getConfigurationSection("materials");
        for (String key : configurationSection.getKeys(false)) {
            try {
                registerMaterial(Material.valueOf(key));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public void registerLore() {
        ConfigurationSection configurationSection = c.getConfigurationSection("core");
        for (String key : configurationSection.getKeys(false)) {
            ComponentRegistry.getDefaultLoreMap().put(Core.valueOf(key.toUpperCase()),configurationSection.getStringList(key + ".lore"));
        }
    }

    public void registerMaterial(Material material) throws IOException { //move these methods to the registry
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
        String componentType = configurationSection.getString("type");
        String string = configurationSection.getString("title");
        if(componentType == null) {
            throw new IOException("Invalid component type in config: materials");
        }
        ComponentType type = ComponentType.valueOf(componentType.toUpperCase().strip());
        ComponentRegistry.getMaterialComponentType().put(material,type);
        ComponentRegistry.getMaterialBoostMap().put(material,wandBoost);
        ComponentRegistry.getMaterialTitleMap().put(material,string);
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
