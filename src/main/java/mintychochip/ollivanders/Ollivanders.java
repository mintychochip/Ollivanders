package mintychochip.ollivanders;

import mintychochip.genesis.Genesis;
import mintychochip.ollivanders.commands.CommandManager;
import mintychochip.ollivanders.commands.TestingWandCommand;
import mintychochip.ollivanders.config.SpellConfig;
import mintychochip.ollivanders.handler.PersistentSpellManager;
import mintychochip.ollivanders.handler.ProjectileHandler;
import mintychochip.ollivanders.listener.OllivandersItemListener;
import mintychochip.ollivanders.listener.SpellListener;
import mintychochip.ollivanders.util.SpellTokenizer;
import mintychochip.ollivanders.wand.config.ComponentConfig;
import mintychochip.ollivanders.wand.config.ComponentRegistry;
import mintychochip.ollivanders.wand.config.WandConfig;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

public final class Ollivanders extends JavaPlugin {

    private static ComponentConfig componentConfig;
    private static WandConfig wandConfig;

    private static SpellConfig spellConfig;

    private static SpellTokenizer tokenizer;

    private static ProjectileHandler projectileHandler;

    private static PersistentSpellManager persistentSpellManager;
    private static Ollivanders instance;

    public static PersistentSpellManager getPersistentSpellManager() {
        return persistentSpellManager;
    }

    public static WandConfig getWandConfig() {
        return wandConfig;
    }

    public static ComponentConfig getComponentConfig() {
        return componentConfig;
    }

    public static Ollivanders getInstance() {
        return instance;
    }

    public static SpellTokenizer getTokenizer() {
        return tokenizer;
    }

    public static SpellConfig getSpellConfig() {
        return spellConfig;
    }

    public static ProjectileHandler getProjectileHandler() {
        return projectileHandler;
    }

    @Override
    public void onEnable() {
        instance = this;
        // Plugin startup logic
        Genesis.getKeys().generateKey(this, "items");
        Genesis.getKeys().generateKey(this, "wand");
        persistentSpellManager = new PersistentSpellManager();
        projectileHandler = new ProjectileHandler();
        tokenizer = new SpellTokenizer();
        componentConfig = new ComponentConfig("components.yml");
        wandConfig = new WandConfig("wand.yml");
        try {
            spellConfig = new SpellConfig("spells.yml");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        enableRegistries();
        getCommand("component").setExecutor(new CommandManager());
        getCommand("wand").setExecutor(new TestingWandCommand());
        Bukkit.getPluginManager().registerEvents(new SpellListener(),this);
        Bukkit.getPluginManager().registerEvents(new OllivandersItemListener(),this);
    }

    public void enableRegistries() {
        new ComponentRegistry();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
