package mintychochip.ollivanders.util;

import mintychochip.ollivanders.Ollivanders;
import mintychochip.ollivanders.betterwand.WandBoost;
import mintychochip.ollivanders.container.Context;
import mintychochip.ollivanders.container.WizardSpell;
import mintychochip.ollivanders.spellcaster.WizardCaster;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Date;

public class Diagnostics {
    int step = 0;
    public static double test(WizardSpell spell, Player player, WandBoost wandBoost) {
        long l = System.currentTimeMillis();
        new BukkitRunnable() {
            int count = 0;
            final int iterations = 300;
            @Override
            public void run() {
                if(count++ > iterations) {
                    cancel();
                }
                WizardCaster caster = new WizardCaster(spell);
                caster.cast(new Context(player,player.getLocation()), wandBoost);
            }
        }.runTaskTimer(Ollivanders.getInstance(),30L,1L);
        return (System.currentTimeMillis() - l) * 0.001;
    }
}
