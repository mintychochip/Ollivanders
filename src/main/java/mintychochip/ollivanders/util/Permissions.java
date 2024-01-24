package mintychochip.ollivanders.util;

import org.bukkit.entity.Player;
public class Permissions {

    public static boolean hasPermission(Player sender, String permission) {
        return sender.hasPermission(permission);
    }
    public static boolean playerHasSpellPermission(Player sender, String spell) {
        return hasPermission(sender, "ollivanders.spells.abilities." + spell);
    }
    public static boolean cooldownBypass(Player sender) {
        return hasPermission(sender, "ollivanders.spells.bypass.cooldown");
    }
    public static boolean costBypass(Player sender) {
        return hasPermission(sender,"ollivanders.spells.bypass.cost");
    }
    public static boolean halfOfCost(Player sender) {
        return hasPermission(sender, "ollivanders.spells.perks.cooldown.double");
    }
    public static boolean oneThirdOfCost(Player sender) {
        return hasPermission(sender, "ollivanders.spells.perks.cooldown.triple");
    }
    public static boolean quarterOfCost(Player sender) {
        return hasPermission(sender, "ollivanders.spells.perks.cooldown.quadruple");
    }
}
