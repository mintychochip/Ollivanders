package mintychochip.ollivanders.config;

import mintychochip.ollivanders.container.WizardMechanic;
import mintychochip.ollivanders.container.WizardMechanicSettings;
import mintychochip.ollivanders.util.ConfigReader;
import org.bukkit.configuration.ConfigurationSection;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Set;

public class MechanicConfig {
    enum MechanicSettings {
        KEYWORDS,
        RANGE,
        DURATION,
        COST,
        COOLDOWN,
        DAMAGE

    }

    private final ConfigReader c;

    public MechanicConfig(String file) {
        c = new ConfigReader(file); //make a manager to find all the yml files
    }
    public WizardMechanicSettings getMechanicSettings(String mechanic) {
        ConfigurationSection configurationSection = c.getConfigurationSection(mechanic);
        if (configurationSection == null) {
            return null;
        }
        WizardMechanicSettings WizardMechanicSettings = new WizardMechanicSettings();
        for (String key : configurationSection.getKeys(false)) {
            if (checkInSet(key)) {
                switch (MechanicSettings.valueOf(key.toUpperCase())) {
                    case COST -> WizardMechanicSettings.setCost(configurationSection.getInt(key));
                    case RANGE -> WizardMechanicSettings.setRange(configurationSection.getInt(key));
                    case COOLDOWN -> WizardMechanicSettings.setCooldown(configurationSection.getDouble(key));
                    case DURATION -> WizardMechanicSettings.setDuration(configurationSection.getInt(key));
                    case KEYWORDS -> WizardMechanicSettings.setKeywords(configurationSection.getStringList(key));
                    case DAMAGE -> WizardMechanicSettings.setDamage(configurationSection.getInt(key));
                }
            }
        }
        return WizardMechanicSettings;
    }

    public WizardMechanic getMechanic(String mechanic) {
        try {
            Class<?> clazz = Class.forName("mintychochip.ollivanders.spells." + mechanic);
            Constructor<?> constructor = clazz.getConstructor(new Class[]{});
            Object o = constructor.newInstance();
            if(o instanceof WizardMechanic) {
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
        return c.getConfig().getKeys(false);
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
}
