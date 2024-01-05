package mintychochip.ollivanders.handler;

import mintychochip.genesis.util.MathUtil;
import mintychochip.ollivanders.Ollivanders;
import mintychochip.ollivanders.container.Spell;
import mintychochip.ollivanders.util.SpellCaster;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class PersistentSpellManager { //gotta revise this class
    private final Map<Spell, Player> currentPersistentSpells = new ConcurrentHashMap<>();

    public void add(Spell spell, Player player) {
        run(spell, player);
        currentPersistentSpells.put(spell, player);
    }

    private BukkitTask run(Spell spell, Player player) {
        return new BukkitRunnable() {
            final BukkitTask timer = timer((double) System.currentTimeMillis() / 1000, 5, player);

            @Override
            public void run() {
                SpellCaster.persistentCast(spell);
                if (timer.isCancelled()) {
                    currentPersistentSpells.remove(spell);
                    if (spell.getMechanic().getHandlers() != null) {
                        spell.getMechanic().cancelHandlers();
                    }
                    cancel();
                }
            }
        }.runTaskTimer(Ollivanders.getInstance(), spell.getMechanic().getMechanicSettings().getInterval(), spell.getMechanic().getMechanicSettings().getInterval());
    }

    public boolean containsKey(Spell key) {
        return currentPersistentSpells.containsKey(key);
    }

    private BukkitTask timer(double start, double duration, Player player) {
        return new BukkitRunnable() {
            public void run() {
                double l = ((double) System.currentTimeMillis() / 1000) - start;
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(String.format("%s", MathUtil.roundToDecimals(duration - l, 1))));
                if (l >= duration) {
                    cancel();
                }
            }
        }.runTaskTimer(Ollivanders.getInstance(), 0L, 1L);
    }
}