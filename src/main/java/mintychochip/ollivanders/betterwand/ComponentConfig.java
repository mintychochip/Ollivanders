package mintychochip.ollivanders.betterwand;

import mintychochip.ollivanders.betterwand.core.Core;
import mintychochip.ollivanders.util.ConfigReader;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;

import java.util.List;

public class ComponentConfig {

    private final String customComponentPlaceHolder = "custom-items";
    private final String materialsPlaceHolder = "materials";
    private final ConfigReader c;
    private Material material;
    private String customComponentPath;

    private String materialsPath = "materials."; //use placeholders instead, so that the placeholders can be mutable 

    public ComponentConfig(String file) {
        c = new ConfigReader(file);
    }

    public void setCustomComponentPath(String itemPath) {
        customComponentPath = customComponentPlaceHolder + "." + itemPath;
    }

    public void setMaterialConfigurationPath(Material material) {
        this.material = material;
        materialsPath = materialsPlaceHolder + "." + material.toString();
    }

    public WandBoost getMaterialWandBoost() {
        WandBoost wandBoost = new WandBoost();
        ConfigurationSection configurationSection = c.getConfigurationSection(materialsPath);
        ConfigurationSection modifiers = configurationSection.getConfigurationSection("modifiers");
        if (modifiers != null) {
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
        return wandBoost;
    }

    public ComponentType getMaterialComponentType() {
        ConfigurationSection configurationSection = c.getConfigurationSection(materialsPath);
        String type = configurationSection.getString("type");
        if (type != null) {
            try {
                return ComponentType.valueOf(type.toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException();
            } catch (NullPointerException e) {
                throw new NullPointerException();
            }
        }
        return null;
    }

    public List<String> getMaterialCoreLore() {
        if (getMaterialComponentType() == ComponentType.CORE) {
            ConfigurationSection configurationSection = c.getConfigurationSection("core" + material.toString());
            List<String> lore = configurationSection.getStringList("lore");
            if (lore.isEmpty()) {
                throw new NullPointerException();
            }
            return lore;
        }
        return null;
    }

    public String getMaterialLoreName() {
        ConfigurationSection configurationSection = c.getConfigurationSection(materialsPath);
        return configurationSection.getString("lore-name", "default");
    }

    public String getMaterialTitle() {
        ConfigurationSection configurationSection = c.getConfigurationSection(materialsPath);
        return configurationSection.getString("title", "default");
    }

    public Core getCore() {
        String string = material.toString();
        return Core.valueOf(string);
    }

    //-------------------------------------------------------------------------------------
    public ComponentType getCustomComponentType() {
        ConfigurationSection configurationSection = c.getConfigurationSection(customComponentPath);
        String string = configurationSection.getString("type");
        if (string != null) {
            try {
                return ComponentType.valueOf(string.toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException();
            } catch (NullPointerException e) {
                throw new NullPointerException();
            }
        }
        return null;
    }

    public ConfigurationSection getConfigurationSection(String path) {
        return c.getConfigurationSection(path);
    }

    public WandModifier getWandModifier(String key) {
        return WandModifier.valueOf(key.toUpperCase());
    }

}
