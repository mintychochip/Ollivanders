package mintychochip.ollivanders.handler;

import mintychochip.ollivanders.Ollivanders;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class ExperienceHandler {

    private static final int factor = 8;

    public static BukkitTask reducePlayerLevel(int points, Player player) {
        return new BukkitRunnable() {
            final int remainder = points % factor;
            final long start = System.currentTimeMillis();
            final int ticks = points / factor;
            int count = 0;

            public void run() {
                if (count++ <= ticks) {
                    player.giveExp(-factor);
                }
                if (count == ticks) {
                    if (remainder != 0) {
                        player.giveExp(-remainder);
                    }
                    cancel();
                }
            }
        }.runTaskTimer(Ollivanders.getInstance(), 2L, 1L);

    }
}
