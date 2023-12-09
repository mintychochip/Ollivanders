package mintychochip.ollivanders.handler;

import mintychochip.genesis.util.Timer;
import mintychochip.ollivanders.Ollivanders;
import mintychochip.ollivanders.container.Context;
import mintychochip.ollivanders.container.Spell;
import mintychochip.ollivanders.util.SpellCaster;
import mintychochip.ollivanders.wand.container.WandBoost;
import mintychochip.ollivanders.wand.container.WandData;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PersistentSpellManager {
    private final Map<Spell, Player> currentCastingSpells = new HashMap<>();

    public void add(Spell spell, Context context, WandData wandData) {
        run(spell, context);
        currentCastingSpells.put(spell,context.getPlayer());
    }

    private void run(Spell spell, Context context) {
        new BukkitRunnable() {
            final BukkitTask bukkitTask = startTimer(spell, context);
            @Override
            public void run() {
                SpellCaster.persistentCast(spell,context);
                if (bukkitTask.isCancelled()) {
                    currentCastingSpells.remove(spell);
                    cancel();
                }
            }
        }.runTaskTimer(Ollivanders.getInstance(), spell.getMechanic().getMechanicSettings().getInterval(), spell.getMechanic().getMechanicSettings().getInterval());
    }

    private BukkitTask startTimer(Spell spell, Context context) {
        return new BukkitRunnable() {

            Timer timer = new Timer(spell.getMechanic().getEffectiveDuration());
            @Override
            public void run() {
                if(timer.remainingSeconds() <= 0) {
                    cancel();
                }
            }
        }.runTaskTimer(Ollivanders.getInstance(), 0L, 1L);
    }
}