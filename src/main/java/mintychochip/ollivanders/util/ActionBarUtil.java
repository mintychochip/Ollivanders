package mintychochip.ollivanders.util;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class ActionBarUtil {

    double spellDuration;

    public static void sendActionBarMessage(Player player, String text) {
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(text));
    }

    public static void sendActionBarMessage(Player player, String text, ProgressBar progressBar) {
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(text + " " + progressBar.print()));
    }

    public static class ProgressBar {

        double spellDuration;
        long start;
        int bars;
        String character;

        public ProgressBar(int bars, int spellDuration, String character, long start) {
            this.bars = bars;
            this.spellDuration = spellDuration;
            this.character = character;
            this.start = start;
        }
        public ProgressBar(int bars, int spellDuration, String character) {
            this(bars,spellDuration,character,System.currentTimeMillis());
        }

        public double getRemainingSeconds() {
            return spellDuration - (double) (System.currentTimeMillis() - start) / 1000L;
        }
        private double getProportionComplete() {
            long secondsElapsed = (System.currentTimeMillis() - start) / 1000L;
            return secondsElapsed / spellDuration;
        }
        public String print() {
            int complete = (int) (getProportionComplete() * bars); //completed bars;
            int incomplete = bars - complete;
            return ChatColor.GREEN + character.repeat(complete) + ChatColor.RED + character.repeat(incomplete);
        }
    }


    public static double roundToDecimals(double d, int c) //move this to math utils
    {
        int temp = (int)(d * Math.pow(10 , c));
        return ((double)temp)/Math.pow(10 , c);
    }
}
