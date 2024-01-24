package mintychochip.ollivanders.config;

import mintychochip.genesis.Genesis;
import mintychochip.genesis.config.GenericConfig;
import mintychochip.genesis.config.GenesisConfigurationSection;
import mintychochip.genesis.particle.GenesisShape;
import mintychochip.genesis.util.ConfigMarker;
import mintychochip.ollivanders.Ollivanders;
import mintychochip.ollivanders.container.MechanicSettings;
import mintychochip.ollivanders.container.SpellMechanic;
import mintychochip.ollivanders.enums.Keyword;
import mintychochip.ollivanders.enums.Modifier;
import mintychochip.ollivanders.enums.Shape;
import mintychochip.ollivanders.util.EnumUtil;
import mintychochip.ollivanders.util.OllivandersConfigMarker;
import org.bukkit.Particle;
import org.bukkit.entity.EntityType;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
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
        Genesis.getKeys().generateKeys(Ollivanders.getInstance(), getMainConfigurationSection(OllivandersConfigMarker.global_settings).getStringList(OllivandersConfigMarker.keys));
        if (!registerMechanics()) {
            //something
        }
        Registry.setKeywordAlias(generateKeywordRegistry(Keyword.class, "keyword"));
        Registry.setShapeAlias(generateKeywordRegistry(Shape.class, "shape"));
        Registry.setModifierAlias(generateKeywordRegistry(Modifier.class, "modifier"));
    }

    public void loadGenesisKeys(GenesisConfigurationSection serverSettings) { //beginning of using config marker
    }

    public <E extends Enum<E>> Map<String, E> generateKeywordRegistry(Class<E> enumClass, String section) throws IOException {

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
            if (mechanicSection == null) {
                return false;
            }
            if (!registerMechanic(mechanicSection) || !registerSettings(mechanicSection, key)) {
                return false;
            }
        }
        return true;
    }

    public MechanicSettings createMechanicSettings(GenesisConfigurationSection settings, String key) {
        GenesisConfigurationSection keySection = settings.getConfigurationSection(key);
        if (keySection != null) {
            MechanicSettings mechanicsSettings = copyMechanicSettingsFromSection(defaults, new MechanicSettings());
            String inherits = keySection.getString("inherits");
            if (inherits != null) {
                Registry.getSettingsMap().get(inherits.toUpperCase()).copyTo(mechanicsSettings);
            }
            GenesisConfigurationSection configurationSection = keySection.getConfigurationSection("modifiers");
            if (configurationSection != null) {
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
        int customModel = mechanicConfiguration.getInt(OllivandersConfigMarker.custom_model);
        for (String key : settings.getKeys(false)) {
            MechanicSettings mechanicSettings = createMechanicSettings(settings, key);
            String registryKey = mechanic + "-" + key;
            Registry.getSettingsMap().put(registryKey.toUpperCase(), mechanicSettings.setSpellInventoryCustomModel(customModel));
        }
        return true;
    }

    public boolean registerMechanic(GenesisConfigurationSection mechanicSection) {
        SpellMechanic spellMechanic = getMechanic(mechanicSection.getString("class"));
        if (spellMechanic == null) {
            return false;
        }
        List<String> keywords = mechanicSection.getStringList("keywords");
        if (keywords != null) {
            for (String keyword : keywords) {
                Registry.getMechanicAlias().put(keyword.toUpperCase(), spellMechanic);
            }
            return true;
        }
        return false;
    }

    public MechanicSettings copyMechanicSettingsFromSection(GenesisConfigurationSection mechanicSection, MechanicSettings mechanicSettings) {
        for (String key : mechanicSection.getKeys(false)) {
            if (EnumUtil.isInEnum(SpellModifiers.class, key.toUpperCase())) {
                switch (Enum.valueOf(SpellModifiers.class, key.toUpperCase())) {
                    case COST -> mechanicSettings.setCost(mechanicSection.getDouble(key));
                    case RANGE -> mechanicSettings.setRange(mechanicSection.getDouble(key));
                    case DAMAGE -> mechanicSettings.setDamage(mechanicSection.getDouble(key));
                    case DURATION -> mechanicSettings.setDuration(mechanicSection.getDouble(key));
                    case INTERVAL -> mechanicSettings.setInterval(mechanicSection.getLong(key));
                    case PERSISTENT -> mechanicSettings.setPersistent(mechanicSection.getBoolean(key));
                    case ENTITY_TYPE ->
                            mechanicSettings.setEntityType(Enum.valueOf(EntityType.class, mechanicSection.getString(key).toUpperCase()));
                    case MAGNITUDE -> mechanicSettings.setMagnitude(mechanicSection.getDouble(key));
                    case COOLDOWN -> mechanicSettings.setCooldown(mechanicSection.getDouble(key));
                    case GENESIS_SHAPE ->
                            mechanicSettings.setGenesisShape(Enum.valueOf(GenesisShape.class, mechanicSection.getString(key).toUpperCase()));
                    case PARTICLES -> {
                        List<Particle> particleList = new ArrayList<>();
                        for (String s : mechanicSection.getStringList(key)) {
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
            return (SpellMechanic) clazz.getConstructor().newInstance();
        } catch (ClassNotFoundException | NoSuchMethodException | InvocationTargetException | InstantiationException |
                 IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    enum SpellModifiers {
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
        PARTICLES,
    }
}
