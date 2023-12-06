package mintychochip.ollivanders;

import mintychochip.genesis.Genesis;
import mintychochip.ollivanders.commands.CommandManager;
import mintychochip.ollivanders.commands.TestingWandCommand;
import mintychochip.ollivanders.wand.ComponentConfig;
import mintychochip.ollivanders.wand.ComponentRegistry;
import org.bukkit.plugin.java.JavaPlugin;

public final class Ollivanders extends JavaPlugin {

    private static ComponentConfig componentConfig;
    private static WandConfig wandConfig;
    private static Ollivanders instance;

    public static WandConfig getWandConfig() {
        return wandConfig;
    }

    public static ComponentConfig getComponentConfig() {
        return componentConfig;
    }

    public static Ollivanders getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;
        // Plugin startup logic
        Genesis.getKeys().generateKey(this, "items");
        Genesis.getKeys().generateKey(this, "wand");
        componentConfig = new ComponentConfig("components.yml");
        wandConfig = new WandConfig("wand.yml");
        enableRegistries();
        getCommand("component").setExecutor(new CommandManager());
        getCommand("wand").setExecutor(new TestingWandCommand());
    }

    public void enableRegistries() {
        new ComponentRegistry();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
