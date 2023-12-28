package mintychochip.ollivanders.handler;

import mintychochip.genesis.Genesis;
import mintychochip.genesis.util.MathUtil;
import mintychochip.ollivanders.Ollivanders;
import mintychochip.ollivanders.container.Context;
import mintychochip.ollivanders.container.Spell;
import mintychochip.ollivanders.util.SpellCaster;
import mintychochip.ollivanders.wand.container.WandData;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.Map;

public class PersistentSpellManager {
    private final Map<Spell, Player> currentCastingSpells = new HashMap<>();

    public void add(Spell spell, WandData wandData, Context context) {
        run(spell, wandData, context);
        currentCastingSpells.put(spell, context.getPlayer());
    }

    private void run(Spell spell, WandData wandData, Context context) {
        new BukkitRunnable() {
            final BukkitTask bukkitTask = startTimer(spell, context);

            @Override
            public void run() {
                SpellCaster.cast(spell, wandData, context, true);
                if (bukkitTask.isCancelled()) {
                    currentCastingSpells.remove(spell);
                    cancel();
                }
            }
        }.runTaskTimer(Ollivanders.getInstance(), spell.getMechanic().getMechanicSettings().getInterval(), spell.getMechanic().getMechanicSettings().getInterval());
    }
    public boolean containsKey(Spell key) {
        return currentCastingSpells.containsKey(key);
    }

    private BukkitTask startTimer(Spell spell, Context context) {

        double duration = spell.getMechanic().getEffectiveDuration();
        long start = System.currentTimeMillis();
        return new BukkitRunnable() {

            @Override
            public void run() {
                double v = (double) (System.currentTimeMillis() - start) / 1000;
                context.getPlayer().spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText("Remaining " + MathUtil.roundToDecimals(duration - v, 1)));
                if (v >= duration) {
                    cancel();
                }
            }
        }.runTaskTimer(Ollivanders.getInstance(), 0L, 1L);
    }
}