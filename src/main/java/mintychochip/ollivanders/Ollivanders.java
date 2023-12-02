package mintychochip.ollivanders;

import mintychochip.ollivanders.betterwand.ComponentConfig;
import mintychochip.ollivanders.betterwand.ComponentRegistry;
import mintychochip.ollivanders.commands.CommandManager;
import mintychochip.ollivanders.config.MechanicsConfig;
import mintychochip.ollivanders.handler.PersistentSpellManager;
import mintychochip.ollivanders.handler.ProjectileHandler;
import mintychochip.ollivanders.listener.PlayerListener;
import mintychochip.ollivanders.registry.MechanicRegistry;
import mintychochip.ollivanders.registry.WizardRegistry;
import mintychochip.ollivanders.sequencer.BetterSpellSequencer;
import mintychochip.ollivanders.sequencer.BookReader;
import mintychochip.ollivanders.util.BetterSpellTokenizer;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class Ollivanders extends JavaPlugin {

    private static MechanicsConfig mechanicConfig;
    private static ComponentConfig componentConfig;
    private static BetterSpellSequencer sequencer;
    private static BetterSpellTokenizer tokenizer;
    private static Ollivanders instance;
    private static BookReader reader;

    private static PersistentSpellManager persistentSpellManager;

    public static MechanicsConfig getMechanicConfig() {
        return mechanicConfig;
    }

    public static Ollivanders getInstance() {
        return instance;
    }

    public static ComponentConfig getWandConfig() {
        return componentConfig;
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    @Override
    public void onEnable() {
        instance = this;
        ProjectileHandler projectileHandler = new ProjectileHandler();
        Bukkit.getServer().getPluginManager().registerEvents(new PlayerListener(),this);
        mechanicConfig = new MechanicsConfig("mechanics.yml");
        componentConfig = new ComponentConfig("components.yml");
        MechanicRegistry mechanicRegistry = new MechanicRegistry();
        WizardRegistry registry = new WizardRegistry();
        ComponentRegistry componentRegistry = new ComponentRegistry();
        tokenizer = new BetterSpellTokenizer();
        sequencer = new BetterSpellSequencer();
        reader = new BookReader();
        persistentSpellManager = new PersistentSpellManager();
        getCommand("wand").setExecutor(new CommandManager());
    }

    public static BookReader getReader() {
        return reader;
    }

    public static BetterSpellTokenizer getTokenizer() {
        return tokenizer;
    }

    public static BetterSpellSequencer getSequencer() {
        return sequencer;
    }

    public static PersistentSpellManager getPersistentSpellManager() {
        return persistentSpellManager;
    }
}
