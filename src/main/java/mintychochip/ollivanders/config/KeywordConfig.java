package mintychochip.ollivanders.config;

import mintychochip.ollivanders.container.Keyword;
import mintychochip.ollivanders.container.Modifier;
import mintychochip.ollivanders.shape.Shape;
import mintychochip.ollivanders.util.ConfigReader;
import org.bukkit.configuration.ConfigurationSection;

import java.util.HashMap;
import java.util.Map;

public class KeywordConfig { //will add mechanics config,cost,haste, and projectile alias
    //can add numerical, probably want to rename it though
    private final ConfigReader c;

    public KeywordConfig(String fileName) {
        c = new ConfigReader(fileName);
    }

    public Map<String, Modifier> modifiers() {
        ConfigurationSection configurationSection = getConfigurationSection("modifier");
        Map<String, Modifier> modifiers = new HashMap<>();
        for (String key : configurationSection.getKeys(false)) {
            Modifier modifier = Modifier.valueOf(key.toUpperCase());
            for (String s : configurationSection.getStringList(key)) {
                modifiers.put(s.toUpperCase(), modifier);
            }
        }
        return modifiers;
    }

    public Map<String, Keyword> keywords() {
        ConfigurationSection configurationSection = getConfigurationSection("keyword");
        Map<String, Keyword> keywords = new HashMap<>();
        for (String key : configurationSection.getKeys(false)) {
            Keyword keyword = Keyword.valueOf(key.toUpperCase());
            for (String s : configurationSection.getStringList(key)) {
                keywords.put(s.toUpperCase(), keyword);
            }
        }
        return keywords;
    }

    public Map<String, Shape> shapes() {
        ConfigurationSection configurationSection = getConfigurationSection("shape");
        Map<String, Shape> shapes = new HashMap<>();
        for (String key : configurationSection.getKeys(false)) {
            Shape shape = Shape.valueOf(key.toUpperCase());
            for (String s : configurationSection.getStringList(key)) {
                shapes.put(s.toUpperCase(), shape);
            }
        }
        return shapes;
    }

    public ConfigurationSection getConfigurationSection(String header) {
        return c.getConfig().getConfigurationSection(header);
    }
}
