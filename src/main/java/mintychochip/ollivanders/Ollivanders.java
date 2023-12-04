package mintychochip.ollivanders;

import mintychochip.genesis.Genesis;
import mintychochip.ollivanders.wand.ComponentConfig;
import mintychochip.ollivanders.commands.CommandManager;
import mintychochip.ollivanders.wand.ComponentConfig;
import org.bukkit.plugin.java.JavaPlugin;

public final class Ollivanders extends JavaPlugin {

    private static ComponentConfig componentConfig;
    private static Ollivanders instance;
    @Override
    public void onEnable() {
        instance = this;
        // Plugin startup logic
        Genesis.getKeys().generateKey(this,"items");
        componentConfig = new ComponentConfig("components.yml");
        getCommand("component").setExecutor(new CommandManager());
    }

    public static ComponentConfig getComponentConfig() {
        return componentConfig;
    }

    public static Ollivanders getInstance() {
        return instance;
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
