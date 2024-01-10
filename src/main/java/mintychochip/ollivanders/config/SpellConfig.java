package mintychochip.ollivanders.config;

import mintychochip.genesis.config.GenericConfig;
import mintychochip.genesis.config.GenesisConfigurationSection;
import mintychochip.genesis.particle.GenesisShape;
import mintychochip.ollivanders.container.MechanicSettings;
import mintychochip.ollivanders.container.SpellMechanic;
import mintychochip.ollivanders.enums.Keyword;
import mintychochip.ollivanders.enums.Modifier;
import mintychochip.ollivanders.enums.Shape;
import mintychochip.ollivanders.util.EnumUtil;
import org.bukkit.Particle;
import org.bukkit.entity.EntityType;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SpellConfig extends GenericConfig {
    //add default for mechanics
    private final GenesisConfigurationSection mechanics = getMainConfigurationSection("mechanics");

    private final GenesisConfigurationSection keywords = getMainConfigurationSection("keywords");

    private final GenesisConfigurationSection defaults = getMainConfigurationSection("default");

    public SpellConfig(String fileName, JavaPlugin plugin) throws IOException {
        super(fileName, plugin);
        if(!registerMechanics()) {
            //something
        }
        Registry.setKeywordAlias(generateRegistry(Keyword.class, "keyword"));
        Registry.setShapeAlias(generateRegistry(Shape.class, "shape"));
        Registry.setModifierAlias(generateRegistry(Modifier.class, "modifier"));
    }

    public <E extends Enum<E>> Map<String, E> generateRegistry(Class<E> enumClass, String section) throws IOException {

        GenesisConfigurationSection configurationSection = keywords.getConfigurationSection(section);
        if (configurationSection == null) {
            throw new IOException("Failed to generate registry: " + enumClass.getName() + " at " + section);
        }
        Map<String, E> map = new HashMap<>();
        for (String key : configurationSection.getKeys(false)) {
            if (EnumUtil.isInEnum(enumClass, key.toUpperCase())) {
                E e = Enum.valueOf(enumClass, key.toUpperCase());
                for (String s : configurationSection.getStringList(key)) {
                    map.put(s.toUpperCase(), e);
                }
            }
        }
        return map;
    }

    public boolean registerMechanics() {
        for (String key : mechanics.getKeys(false)) {
            GenesisConfigurationSection mechanicSection = mechanics.getConfigurationSection(key);
            if(mechanicSection == null) {
                return false;
            }
            if (!registerMechanic(mechanicSection,key) || !registerSettings(mechanicSection,key)) {
                return false;
            }
        }
        return true;
    }
    public MechanicSettings createMechanicSettings(GenesisConfigurationSection settings, String key) {
        GenesisConfigurationSection keySection = settings.getConfigurationSection(key);
        if (keySection != null) {
            MechanicSettings mechanicsSettings = copyMechanicSettingsFromSection(defaults,new MechanicSettings());
            String inherits = keySection.getString("inherits");
            if (inherits != null) {
                Registry.getSettingsMap().get(inherits.toUpperCase()).copyTo(mechanicsSettings);
            }
            GenesisConfigurationSection configurationSection = keySection.getConfigurationSection("modifiers");
            if(configurationSection != null) {
                return copyMechanicSettingsFromSection(configurationSection, mechanicsSettings);
            }
        }
        return null;
    }
    public boolean registerSettings(GenesisConfigurationSection mechanicConfiguration, String mechanic) {
        GenesisConfigurationSection settings = mechanicConfiguration.getConfigurationSection("settings");
        if (settings == null) {
            return false;
        }
        for (String key : settings.getKeys(false)) {
            MechanicSettings mechanicSettings = createMechanicSettings(settings, key);
            String registryKey = key.equals("main") ? mechanic : mechanic + "-" + key;
            Registry.getSettingsMap().put(registryKey.toUpperCase(), mechanicSettings);
        }
        return true;
    }

    public boolean registerMechanic(GenesisConfigurationSection mechanicSection, String mechanic) {
        SpellMechanic spellMechanic = getMechanic(mechanicSection.getString("class"));
        if (spellMechanic == null) {
            return false;
        }
        MechanicSettings mechanicSettings = copyMechanicSettingsFromSection(defaults,new MechanicSettings());
        String key = mechanicSection.getString("inherits");
        if (key != null) {
            Registry.getMechanicAlias().get(key.toUpperCase()).getMechanicSettings().copyTo(mechanicSettings);
        }
        mechanicSettings = copyMechanicSettingsFromSection(mechanicSection.getConfigurationSection("settings.main.modifiers"), mechanicSettings);

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

    public MechanicSettings copyMechanicSettingsFromSection(GenesisConfigurationSection configurationSection, MechanicSettings mechanicSettings) {
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
                    case ENTITY_TYPE ->
                            mechanicSettings.setEntityType(Enum.valueOf(EntityType.class, configurationSection.getString(key).toUpperCase()));
                    case MAGNITUDE -> mechanicSettings.setMagnitude(configurationSection.getDouble(key));
                    case COOLDOWN -> mechanicSettings.setCooldown(configurationSection.getDouble(key));
                    case GENESIS_SHAPE ->
                            mechanicSettings.setGenesisShape(Enum.valueOf(GenesisShape.class, configurationSection.getString(key).toUpperCase()));
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
