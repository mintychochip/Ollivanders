package mintychochip.ollivanders.handler;

import mintychochip.genesis.Genesis;
import mintychochip.genesis.util.MathUtil;
import mintychochip.ollivanders.Ollivanders;
import mintychochip.ollivanders.container.Spell;
import mintychochip.ollivanders.util.SpellCaster;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class PersistentSpellManager { //gotta revise this class
    private final Map<Spell, Player> currentPersistentSpells = new ConcurrentHashMap<>();

    public void add(Spell spell, Player player) {
        run(spell);
        currentPersistentSpells.put(spell, player);
    }

    private BukkitTask run(Spell spell) {
        return new BukkitRunnable() {
            long start = System.currentTimeMillis() / 1000;
            @Override
            public void run() {
                SpellCaster.persistentCast(spell);
                long l = (System.currentTimeMillis() / 1000) - start; //delta T
                if (l >= spell.getMechanic().effectiveDuration()) {
                    currentPersistentSpells.remove(spell);
                    cancel();
                }
            }
        }.runTaskTimer(Ollivanders.getInstance(), spell.getMechanic().getMechanicSettings().getInterval(), spell.getMechanic().getMechanicSettings().getInterval());
    }
    public boolean containsKey(Spell key) {
        return currentPersistentSpells.containsKey(key);
    }

}