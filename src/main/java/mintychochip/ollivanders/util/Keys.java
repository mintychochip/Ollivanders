package mintychochip.ollivanders.util;

import mintychochip.ollivanders.Ollivanders;
import org.bukkit.NamespacedKey;

public class Keys {

    private static final NamespacedKey componentData = new NamespacedKey(Ollivanders.getInstance(),"componentData");

    private static final NamespacedKey wandData = new NamespacedKey(Ollivanders.getInstance(), "wandData");

    private static final NamespacedKey core = new NamespacedKey(Ollivanders.getInstance(), "type");

    private static final NamespacedKey unstackable = new NamespacedKey(Ollivanders.getInstance(),"unstackable");
    public static NamespacedKey getComponentData() {
        return componentData;
    }

    public static NamespacedKey getWandData() {
        return wandData;
    }

    public static NamespacedKey getCore() {
        return core;
    }

    public static NamespacedKey getUnstackable() {
        return unstackable;
    }
}
