package mintychochip.orchid;

import mintychochip.orchid.config.KeywordConfig;
import mintychochip.orchid.config.MechanicConfig;
import mintychochip.orchid.handler.ProjectileHandler;
import mintychochip.orchid.listener.PlayerListener;
import mintychochip.orchid.registry.MechanicRegistry;
import mintychochip.orchid.registry.OrchidRegistry;
import mintychochip.orchid.util.BetterSpellTokenizer;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class Orchid extends JavaPlugin {

    private static KeywordConfig keywordConfig;
    private static MechanicConfig mechanicConfig;

    private static BetterSpellTokenizer tokenizer;
    private static Orchid instance;


    public static KeywordConfig getKeywordConfig() {
        return keywordConfig;
    }

    public static MechanicConfig getMechanicConfig() {
        return mechanicConfig;
    }

    public static Orchid getInstance() {
        return instance;
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
        keywordConfig = new KeywordConfig("keywords.yml");
        mechanicConfig = new MechanicConfig("spells.yml");
        MechanicRegistry mechanicRegistry = new MechanicRegistry();
        OrchidRegistry registry = new OrchidRegistry();
        BetterSpellTokenizer tokenizer = new BetterSpellTokenizer();
    }

    public static BetterSpellTokenizer getTokenizer() {
        return tokenizer;
    }
}
