package mintychochip.ollivanders.config;

import mintychochip.ollivanders.ConfigReader;
import mintychochip.ollivanders.GenericConfig;
import mintychochip.ollivanders.container.MechanicSettings;
import mintychochip.ollivanders.container.SpellMechanic;
import mintychochip.ollivanders.enums.Keyword;
import mintychochip.ollivanders.enums.Modifier;
import mintychochip.ollivanders.enums.Shape;
import mintychochip.ollivanders.util.EnumUtil;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.EntityType;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SpellConfig extends GenericConfig {
    //add default for mechanics
    private final String mechanicsMainPath = "mechanics";

    private final String keywordsMainPath = "keywords";

    public SpellConfig(String fileName) throws IOException {
        super(fileName);
        if (!registerMechanics()) {
            throw new IOException("Mechanics failed to load, check config");
        }
        Registry.setKeywordAlias(generateRegistry(Keyword.class,"keyword"));
        Registry.setShapeAlias(generateRegistry(Shape.class,"shape"));
        Registry.setModifierAlias(generateRegistry(Modifier.class,"modifier"));
    }
    public<E extends Enum<E>> Map<String,E> generateRegistry(Class<E> enumClass,String section) throws IOException {
        ConfigurationSection configurationSection = configReader.getConfigurationSection(keywordsMainPath + "." + section);
        if(configurationSection == null) {
            throw new IOException("Failed to generate registry: " + enumClass.getName() + " at " + section);
        }
        Map<String,E> map = new HashMap<>();
        for (String key : configurationSection.getKeys(false)) {
            if(EnumUtil.isInEnum(enumClass,key.toUpperCase())) {
                E e = Enum.valueOf(enumClass, key.toUpperCase());
                for (String s : configurationSection.getStringList(key)) {
                    map.put(s.toUpperCase(),e);
                }
            }
        }
        return map;
    }
    public boolean registerMechanics() {
        ConfigurationSection configurationSection = configReader.getConfigurationSection(mechanicsMainPath);

        for (String key : configurationSection.getKeys(false)) {
            if (!registerMechanic(key)) {
                return false;
            }
        }
        Bukkit.broadcastMessage("outsideregistermechanics");
        return true;
    }

    public boolean registerMechanic(String mechanic) {
        ConfigurationSection configurationSection = configReader.getConfigurationSection(mechanicsMainPath + "." + mechanic);
        if (configurationSection == null) {
            return false;
        }
        SpellMechanic spellMechanic = getMechanic(mechanic);
        if (spellMechanic == null) {
            return false;
        }
        MechanicSettings mechanicSettings = new MechanicSettings();

        for (String key : configurationSection.getKeys(false)) {
            if (EnumUtil.isInEnum(Settings.class, key.toUpperCase())) {
                switch (Enum.valueOf(Settings.class, key.toUpperCase())) {
                    case COST -> mechanicSettings.setCost(configurationSection.getDouble(key));
                    case RANGE -> mechanicSettings.setRange(configurationSection.getDouble(key));
                    case DAMAGE -> mechanicSettings.setDamage(configurationSection.getDouble(key));
                    case DURATION -> mechanicSettings.setDuration(configurationSection.getDouble(key));
                    case INTERVAL -> mechanicSettings.setInterval(configurationSection.getLong(key));
                    case PERSISTENT -> mechanicSettings.setPersistent(configurationSection.getBoolean(key));
                    case KEYWORDS -> mechanicSettings.setKeywords(configurationSection.getStringList(key));
                    case ENTITY_TYPE -> mechanicSettings.setEntityType(Enum.valueOf(EntityType.class,configurationSection.getString(key).toUpperCase()));
                    case MAGNITUDE -> mechanicSettings.setMagnitude(configurationSection.getDouble(key));
                }
            }
        }
        spellMechanic.setMechanicSettings(mechanicSettings);

        List<String> keywords = spellMechanic.getMechanicSettings().getKeywords();
        if (keywords != null) {
            for (String keyword : keywords) {
                Registry.getMechanicAlias().put(keyword.toUpperCase(), spellMechanic);
            }
        } else {
            return false;
        }
        return true;
    }

    public SpellMechanic getMechanic(String mechanic) {
        try {
            Class<?> clazz = Class.forName("mintychochip.ollivanders.spells." + mechanic);
            Constructor<?> constructor = clazz.getConstructor();
            return (SpellMechanic) constructor.newInstance();
        } catch (ClassNotFoundException | NoSuchMethodException | InvocationTargetException | InstantiationException |
                 IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    enum Settings {
        KEYWORDS,
        RANGE,
        DURATION,
        COST,
        DAMAGE,
        PERSISTENT,
        INTERVAL,
        ENTITY_TYPE,
        MAGNITUDE
    }
}
