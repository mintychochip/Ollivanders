package mintychochip.ollivanders.betterwand;

import mintychochip.ollivanders.betterwand.core.Core;
import mintychochip.ollivanders.util.ConfigReader;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ComponentConfig {

    private final String customComponentPlaceHolder = "custom-items";
    private final String materialsPlaceHolder = "materials";
    private final ConfigReader c;
    private Material material;
    private ConfigurationSection currentConfigurationSection;
    private boolean currentIsMaterial;

    public ComponentConfig(String file) {
        c = new ConfigReader(file);
        currentIsMaterial = false;
    }

    public void setConfigurationPath(@NotNull Material material) {
        this.material = material;
        setConfigurationPath(material.toString(), true);
    }

    public void setConfigurationPath(String itemPath, boolean isMaterial) {
        if (isMaterial) {
            currentConfigurationSection = c.getConfigurationSection(materialsPlaceHolder + "." + material.toString());
        } else {
            currentConfigurationSection = c.getConfigurationSection(customComponentPlaceHolder + "." + itemPath);
        }
        currentIsMaterial = isMaterial;
    }

    public WandBoost getWandBoost() {
        WandBoost wandBoost = new WandBoost();

        ConfigurationSection modifiers = currentConfigurationSection.getConfigurationSection("modifiers");
        if (modifiers != null) {
            for (String key : modifiers.getKeys(false)) {
                switch (getWandModifier(key)) {
                    case RANGE -> wandBoost.setRange(wandBoost.getRange() + modifiers.getDouble(key));
                    case EFFICIENCY -> wandBoost.setCost(wandBoost.getCost() + modifiers.getDouble(key));
                    case DURATION -> wandBoost.setDuration(wandBoost.getDuration() + modifiers.getDouble(key));
                    case POWER -> wandBoost.setPower(wandBoost.getPower() + modifiers.getDouble(key));
                    case HASTE -> wandBoost.setHaste(wandBoost.getHaste() + modifiers.getDouble(key));
                }
            }
        }
        return wandBoost;
    }

    public ComponentType getComponentType() {

        String type = currentConfigurationSection.getString("type");
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

    public List<String> getWandLore() {
        if (getComponentType() == ComponentType.CORE) {
            String path = material.toString();
            if (!currentIsMaterial) {
                path = currentConfigurationSection.getString("core");
            }
            ConfigurationSection configurationSection = c.getConfigurationSection("core." + path);
            List<String> lore = configurationSection.getStringList("lore");
            if (!lore.isEmpty()) {
                return lore;
            }
        }
        return null;
    }

    public List<String> getItemLore() {
        return currentConfigurationSection.getStringList("lore");
    }

    public String getComponentName() {
        String path = "lore-name";
        if (!currentIsMaterial) {
            path = "name";
        }
        return currentConfigurationSection.getString(path, "default");
    }

    public String getComponentTitle() {
        return currentConfigurationSection.getString("title", "default");
    }

    public Rarity getRarity() {
        if (getComponentType() == ComponentType.CORE) {
            return Rarity.valueOf(currentConfigurationSection.getString("rarity", "COMMON"));
        }
        return Rarity.COMMON;
    }

    public Core getCore() {
        if (getComponentType() == ComponentType.CORE) {
            return Core.valueOf(currentConfigurationSection.getString("core"));
        }
        return null;
    }

    public Material getCustomComponentMaterial() {
        if (!currentIsMaterial) {
            String string = currentConfigurationSection.getString("material");
            if (string != null) {
                try {
                    return Material.valueOf(string.toUpperCase());
                } catch (IllegalArgumentException e) {
                    throw new IllegalArgumentException(e);
                }
            }
        }
        return null;
    }

    //-------------------------------------------------------------------------------------

    public ConfigurationSection getConfigurationSection(String path) {
        return c.getConfigurationSection(path);
    }

    public WandModifier getWandModifier(String key) {
        return WandModifier.valueOf(key.toUpperCase());
    }

}
