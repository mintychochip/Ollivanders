package mintychochip.ollivanders.util;

import mintychochip.ollivanders.Ollivanders;
import org.bukkit.NamespacedKey;

import javax.naming.Name;

public class Keys {

    private static NamespacedKey boost = new NamespacedKey(Ollivanders.getInstance(),"boost");

    private static NamespacedKey wand = new NamespacedKey(Ollivanders.getInstance(), "wand");

    private static NamespacedKey core = new NamespacedKey(Ollivanders.getInstance(), "core");
    public static NamespacedKey getBoost() {
        return boost;
    }

    public static NamespacedKey getWand() {
        return wand;
    }

    public static NamespacedKey getCore() {
        return core;
    }
}
