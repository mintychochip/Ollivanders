package mintychochip.ollivanders.handler;

import mintychochip.ollivanders.Ollivanders;
import mintychochip.ollivanders.container.Context;
import mintychochip.ollivanders.container.WizardSpell;
import mintychochip.ollivanders.spellcaster.WizardCaster;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PersistentSpellManager {

    private List<WizardSpell> currentCastingSpells = new ArrayList<>();

    public void add(WizardSpell spell,Context context) {
        run(spell,context);
        currentCastingSpells.add(spell);
    }
    public void run(WizardSpell spell, Context context) {

        new BukkitRunnable() {
            long startTime = System.currentTimeMillis();
            @Override
            public void run() {
                WizardCaster caster = new WizardCaster(spell);
                caster.persistentCast(context);
                Bukkit.broadcastMessage("tick");
                if(System.currentTimeMillis() - startTime > spell.getMechanic().getMechanicSettings().getDuration() * 1000L) {
                    currentCastingSpells.remove(spell);
                    cancel();
                }
            }
        }.runTaskTimer(Ollivanders.getInstance(),spell.getMechanic().getMechanicSettings().getInterval(),spell.getMechanic().getMechanicSettings().getInterval());
    }
}
