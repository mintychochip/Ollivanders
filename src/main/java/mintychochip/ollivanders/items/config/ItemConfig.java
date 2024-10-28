package mintychochip.ollivanders.items.config;

import mintychochip.genesis.config.abstraction.GenericConfig;
import mintychochip.genesis.config.abstraction.GenesisConfigurationSection;
import org.bukkit.plugin.java.JavaPlugin;

public class ItemConfig extends GenericConfig {

    private final GenesisConfigurationSection books = getMainConfigurationSection("books");

    private final GenesisConfigurationSection components = getMainConfigurationSection("components");

    public ItemConfig(String path, JavaPlugin plugin) {
        super(path, plugin);
    }

    public GenesisConfigurationSection getBooks() {
        return books;
    }

    public GenesisConfigurationSection getComponents() {
        return components;
    }
}
