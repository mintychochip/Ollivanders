package mintychochip.ollivanders.util;

import mintychochip.ollivanders.Ollivanders;
import org.bukkit.NamespacedKey;

import javax.naming.Name;

public class Keys {

    private static final NamespacedKey boost = new NamespacedKey(Ollivanders.getInstance(),"boost");

    private static final NamespacedKey wand = new NamespacedKey(Ollivanders.getInstance(), "wand");

    private static final NamespacedKey core = new NamespacedKey(Ollivanders.getInstance(), "type");

    private static final NamespacedKey unstackable = new NamespacedKey(Ollivanders.getInstance(),"unstackable");
    public static NamespacedKey getBoost() {
        return boost;
    }

    public static NamespacedKey getWand() {
        return wand;
    }

    public static NamespacedKey getCore() {
        return core;
    }

    public static NamespacedKey getUnstackable() {
        return unstackable;
    }
}
