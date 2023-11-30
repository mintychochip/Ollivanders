package mintychochip.ollivanders.handler;

import mintychochip.ollivanders.Ollivanders;
import mintychochip.ollivanders.betterwand.WandBoost;
import mintychochip.ollivanders.container.Context;
import mintychochip.ollivanders.container.WizardSpell;
import mintychochip.ollivanders.spellcaster.WizardCaster;
import mintychochip.ollivanders.util.ActionBarUtil;
import mintychochip.ollivanders.util.ActionBarUtil.ProgressBar;
import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.List;

public class PersistentSpellManager {

    private final List<WizardSpell> currentCastingSpells = new ArrayList<>();

    public void add(WizardSpell spell, Context context, WandBoost wandBoost) {
        run(spell, context, wandBoost);
        currentCastingSpells.add(spell);
    }

    public void run(WizardSpell spell, Context context, WandBoost wandBoost) {
        new BukkitRunnable() {
            final BukkitTask bukkitTask = startTimer(spell, context);
            @Override
            public void run() {
                WizardCaster caster = new WizardCaster(spell);
                caster.persistentCast(context, wandBoost);
                if (bukkitTask.isCancelled()) {
                    currentCastingSpells.remove(spell);
                    cancel();
                }
            }
        }.runTaskTimerAsynchronously(Ollivanders.getInstance(), spell.getMechanic().getMechanicSettings().getInterval(), spell.getMechanic().getMechanicSettings().getInterval());
    }

    public BukkitTask startTimer(WizardSpell spell, Context context) {
        return new BukkitRunnable() {
            final ProgressBar progressBar = new ProgressBar(40, spell.getMechanic().getMechanicSettings().getDuration(), "|");

            @Override
            public void run() {
                ActionBarUtil.sendActionBarMessage(context.getPlayer(), ChatColor.DARK_GRAY + "Casting: "
                        + ChatColor.GOLD
                        + spell.getMechanic().getName()
                        + " "
                        + ActionBarUtil.roundToDecimals(progressBar.getRemainingSeconds(),1)
                        + ChatColor.DARK_GRAY
                        + " seconds remaining");
                if(progressBar.getRemainingSeconds() < 0) {
                    cancel();
                }
            }
        }.runTaskTimer(Ollivanders.getInstance(), 0L, 1L);
    }
}
