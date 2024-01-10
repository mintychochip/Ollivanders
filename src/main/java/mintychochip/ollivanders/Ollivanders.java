package mintychochip.ollivanders;

import mintychochip.genesis.Genesis;
import mintychochip.ollivanders.commands.CommandManager;
import mintychochip.ollivanders.commands.WandCommand;
import mintychochip.ollivanders.config.SpellConfig;
import mintychochip.ollivanders.handler.CooldownManager;
import mintychochip.ollivanders.handler.PersistentSpellManager;
import mintychochip.ollivanders.handler.ProjectileHandler;
import mintychochip.ollivanders.listener.OllivandersItemListener;
import mintychochip.ollivanders.listener.SpellDamageListener;
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

    private static CooldownManager cooldownManager;
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

    public static CooldownManager getCooldownManager() {
        return cooldownManager;
    }

    @Override
    public void onEnable() {
        instance = this;
        // Plugin startup logic
        String[] keySet = {"fire","items","wand"};
        Genesis.getKeys().generateKeys(this,keySet);
        persistentSpellManager = new PersistentSpellManager();
        projectileHandler = new ProjectileHandler();
        tokenizer = new SpellTokenizer();
        cooldownManager = new CooldownManager();
        componentConfig = new ComponentConfig("components.yml",this);
        wandConfig = new WandConfig("wand.yml",this);
        try {
            spellConfig = new SpellConfig("spells.yml",this);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        enableRegistries();
        getCommand("wand").setExecutor(new WandCommand());
        getCommand("component").setExecutor(new CommandManager());
        Bukkit.getPluginManager().registerEvents(new SpellListener(),this);
        Bukkit.getPluginManager().registerEvents(new OllivandersItemListener(),this);
        Bukkit.getPluginManager().registerEvents(new SpellDamageListener(),this);
    }

    public void enableRegistries() {
        new ComponentRegistry();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
