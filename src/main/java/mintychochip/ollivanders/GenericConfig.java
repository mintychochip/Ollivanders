package mintychochip.ollivanders;

import org.bukkit.configuration.ConfigurationSection;

import java.util.List;

public class GenericConfig {

    protected final ConfigReader configReader;

    protected ConfigurationSection main;

    protected String path;

    public GenericConfig(String fileName) {
        configReader = new ConfigReader(fileName);
    }

    public void setMain(String path) {
        main = configReader.getConfigurationSection(path);
    }

    public <E extends Enum<E>> E enumFromSection(Class<E> enumClass, String marker) {
        String unknown = main.getString(marker);
        if (unknown == null) {
            return Enum.valueOf(enumClass, "DEFAULT");
        }
        if (!isInEnum(enumClass, unknown.toUpperCase())) {
            throw new IllegalArgumentException();
        }
        return Enum.valueOf(enumClass, unknown.toUpperCase());
    }

    public <E extends Enum<E>> boolean isInEnum(Class<E> enumClass, String value) {
        for (E e : enumClass.getEnumConstants()) {
            if (e.name().equals(value)) {
                return true;
            }
        }
        return false;
    }

    public int getInt(String marker) {
        return main.getString(marker) != null ? main.getInt(marker) : -1;
    }

    public List<String> getStringList(String marker) {
        return main.getStringList(marker);
    }

    public String getString(String marker) {
        return main.getString(marker);
    }

    public ConfigurationSection getConfigurationSection(String path) {
        return configReader.getConfigurationSection(path);
    }

    public String getPath() {
        return path;
    }
}
