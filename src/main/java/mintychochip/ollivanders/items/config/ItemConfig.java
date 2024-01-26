package mintychochip.ollivanders.items.config;

import mintychochip.genesis.config.GenericConfig;
import mintychochip.genesis.config.GenesisConfigurationSection;
import org.bukkit.plugin.java.JavaPlugin;

public class ItemConfig extends GenericConfig {

    private final GenesisConfigurationSection books = getMainConfigurationSection("books");
    public ItemConfig(String path, JavaPlugin plugin) {
        super(path, plugin);
    }
    public GenesisConfigurationSection getBooks() {
        return books;
    }
}
