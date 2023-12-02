package mintychochip.ollivanders.config;

import mintychochip.ollivanders.container.WizardMechanic;
import mintychochip.ollivanders.container.WizardMechanicSettings;
import mintychochip.ollivanders.util.ConfigReader;
import org.bukkit.configuration.ConfigurationSection;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MechanicsConfig {
    private final ConfigReader c;
    private final String mechanicsMainPath = "mechanics";
    private final String keywordsMainPath = "keywords";
    public MechanicsConfig(String file) {
        c = new ConfigReader(file);
    }

    public WizardMechanicSettings getMechanicSettings(String mechanic) {
        ConfigurationSection configurationSection = c.getConfigurationSection(mechanicsMainPath + "." + mechanic);
        if (configurationSection == null) {
            return null;
        }
        WizardMechanicSettings WizardMechanicSettings = new WizardMechanicSettings();
        for (String key : configurationSection.getKeys(false)) {
            if (checkInSet(key)) {
                switch (MechanicSettings.valueOf(key.toUpperCase())) {
                    case COST -> WizardMechanicSettings.setCost(configurationSection.getInt(key));
                    case RANGE -> WizardMechanicSettings.setRange(configurationSection.getInt(key));
                    case HASTE -> WizardMechanicSettings.sethaste(configurationSection.getDouble(key));
                    case DURATION -> WizardMechanicSettings.setDuration(configurationSection.getInt(key));
                    case KEYWORDS -> WizardMechanicSettings.setKeywords(configurationSection.getStringList(key));
                    case DAMAGE -> WizardMechanicSettings.setDamage(configurationSection.getInt(key));
                    case PERSISTENT -> WizardMechanicSettings.setPersistent(configurationSection.getBoolean(key));
                    case INTERVAL -> WizardMechanicSettings.setInterval(configurationSection.getLong(key));
                }
            }
        }
        return WizardMechanicSettings;
    }

    public WizardMechanic getMechanic(String mechanic) {
        try {
            Class<?> clazz = Class.forName("mintychochip.ollivanders.spells." + mechanic);
            Constructor<?> constructor = clazz.getConstructor();
            Object o = constructor.newInstance();
            if (o instanceof WizardMechanic) {
                return (WizardMechanic) o;
            }
            return null;
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public Set<String> getKeys() {
        return c.getConfigurationSection(mechanicsMainPath).getKeys(false);
    }

    public boolean checkInSet(String value) {
        MechanicSettings mechanicSettings = MechanicSettings.valueOf(value.toUpperCase());
        for (MechanicSettings val : MechanicSettings.values()) {
            if (val == mechanicSettings) {
                return true;
            }
        }
        return false;
    }

    public <E extends Enum<E>> Map<String, E> getMap(String path, Class<E> enumClass) {
        ConfigurationSection configurationSection = c.getConfigurationSection(keywordsMainPath + "." + path);
        Map<String, E> map = new HashMap<>();
        for (String key : configurationSection.getKeys(false)) {
            E e = Enum.valueOf(enumClass, key);
            for (String s : configurationSection.getStringList(key)) {
                map.put(s.toUpperCase(), e);
            }
        }
        return map;
    }

    public enum MechanicSettings {
        KEYWORDS,
        RANGE,
        DURATION,
        COST,
        HASTE,
        DAMAGE,
        PERSISTENT,
        INTERVAL

    }
}
