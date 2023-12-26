package mintychochip.ollivanders.config;

import mintychochip.genesis.particle.GenesisShape;
import mintychochip.ollivanders.GenericConfig;
import mintychochip.ollivanders.container.MechanicSettings;
import mintychochip.ollivanders.container.SpellMechanic;
import mintychochip.ollivanders.enums.Keyword;
import mintychochip.ollivanders.enums.Modifier;
import mintychochip.ollivanders.enums.Shape;
import mintychochip.ollivanders.util.EnumUtil;
import org.bukkit.Bukkit;
import org.bukkit.Particle;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.EntityType;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SpellConfig extends GenericConfig {
    //add default for mechanics
    private final String mechanicsMainPath = "mechanics";

    private final String keywordsMainPath = "keywords";

    public SpellConfig(String fileName) throws IOException {
        super(fileName);
        if (!registerMechanics()) {
            throw new IOException("Mechanics failed to load, check config");
        }
        Registry.setKeywordAlias(generateRegistry(Keyword.class, "keyword"));
        Registry.setShapeAlias(generateRegistry(Shape.class, "shape"));
        Registry.setModifierAlias(generateRegistry(Modifier.class, "modifier"));
    }

    public <E extends Enum<E>> Map<String, E> generateRegistry(Class<E> enumClass, String section) throws IOException {
        ConfigurationSection configurationSection = configReader.getConfigurationSection(keywordsMainPath + "." + section);
        if (configurationSection == null) {
            throw new IOException("Failed to generate registry: " + enumClass.getName() + " at " + section);
        }
        Map<String, E> map = new HashMap<>();
        for (String key : configurationSection.getKeys(false)) {
            if (EnumUtil.isInEnum(enumClass, key.toUpperCase())) {
                E e = Enum.valueOf(enumClass, key.toUpperCase());
                for (String s : configurationSection.getStringList(key)) {
                    Bukkit.broadcastMessage(s);
                    map.put(s.toUpperCase(), e);
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
        return true;
    }

    public boolean registerMechanic(String mechanic) {
        ConfigurationSection mechanicConfiguration = configReader.getConfigurationSection(mechanicsMainPath + "." + mechanic);
        if (mechanicConfiguration == null) {
            return false;
        }
        SpellMechanic spellMechanic = getMechanic(mechanicConfiguration.getString("class"));
        if (spellMechanic == null) {
            return false;
        }
        ConfigurationSection defaults = configReader.getConfigurationSection("default.mechanic");

        MechanicSettings mechanicSettings = getMechanicsSettings(defaults);
        String key = mechanicConfiguration.getString("inherits");
        if (key != null) {
            Registry.getMechanicAlias().get(key.toUpperCase()).getMechanicSettings().copyTo(mechanicSettings);
        }
        mechanicSettings = getMechanicsSettings(mechanicConfiguration.getConfigurationSection("settings"), mechanicSettings);

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
    public MechanicSettings getMechanicsSettings(ConfigurationSection configurationSection) {
        return getMechanicsSettings(configurationSection, null);
    }

    public MechanicSettings getMechanicsSettings(ConfigurationSection configurationSection, MechanicSettings mechanicSettings) {
        if (mechanicSettings == null) {
            mechanicSettings = new MechanicSettings();
        }
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
                    case ENTITY_TYPE -> mechanicSettings.setEntityType(Enum.valueOf(EntityType.class, configurationSection.getString(key).toUpperCase()));
                    case MAGNITUDE -> mechanicSettings.setMagnitude(configurationSection.getDouble(key));
                    case COOLDOWN -> mechanicSettings.setCooldown(configurationSection.getDouble(key));
                    case GENESIS_SHAPE -> mechanicSettings.setGenesisShape(Enum.valueOf(GenesisShape.class,configurationSection.getString(key).toUpperCase()));
                    case PARTICLES -> {
                        List<Particle> particleList = new ArrayList<>();
                        for (String s : configurationSection.getStringList(key)) {
                            particleList.add(Enum.valueOf(Particle.class, s.toUpperCase()));
                        }
                        mechanicSettings.setParticleList(particleList);
                    }
                }
            }
        }
        return mechanicSettings;
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
        MAGNITUDE,
        COOLDOWN,
        GENESIS_SHAPE,
        PARTICLES
    }
}
