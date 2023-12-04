package mintychochip.ollivanders;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class ConfigReader {

    private String currentPath;
    private final String fileName;
    private File file;
    private YamlConfiguration config;
    public ConfigReader(String fileName) {
        this.fileName = fileName;
        load();
    }

    public void load() {
        file = new File(Ollivanders.getInstance().getDataFolder(), fileName);
        if (!file.exists()) {
            Ollivanders.getInstance().saveResource(fileName, false);
        }

        config = new YamlConfiguration();
        config.options().parseComments(true);
        reload();
    }

    public void reload() { //make this less general
        try {
            config.load(file);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void save() {
        try {
            config.save(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void set(String path, Object val) {
        config.set(path, val);
        save();
    }

    public YamlConfiguration getConfig() {
        return config;
    }

    public String getString(String path) {
        return config.getString(path);
    }

    public ConfigurationSection getConfigurationSection(String header) {
        return config.getConfigurationSection(header);
    }

    public void setCurrentPath(String currentPath) {
        this.currentPath = currentPath;
    }

    public String getCurrentPath() {
        return currentPath;
    }
}
