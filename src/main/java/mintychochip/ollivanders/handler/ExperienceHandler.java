package mintychochip.ollivanders.handler;

import mintychochip.ollivanders.Ollivanders;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class ExperienceHandler {

    private static int factor = 8;

    public static BukkitTask reducePlayerLevel(int points, Player player) {
        return new BukkitRunnable() {
            long start = System.currentTimeMillis();
            int ticks = points / factor;
            final int remainder = points % factor;
            int count = 0;
            public void run() {
                if(count++ <= ticks) {
                    player.giveExp(-factor);
                }
                if(count == ticks) {
                    if(remainder != 0) {
                        player.giveExp(-remainder);
                    }
                    cancel();
                }
            }
        }.runTaskTimer(Ollivanders.getInstance(),2L,1L);

    }
}
