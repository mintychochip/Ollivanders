package mintychochip.ollivanders.spells;

import mintychochip.genesis.Genesis;
import mintychochip.genesis.manager.GenesisHandler;
import mintychochip.ollivanders.Ollivanders;
import mintychochip.ollivanders.container.SpellMechanic;
import mintychochip.ollivanders.enums.DamageType;
import mintychochip.ollivanders.handler.DotHandler;
import mintychochip.ollivanders.spells.shape.SpellArea;
import mintychochip.ollivanders.spells.shape.SpellSelf;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

public class Immolate extends SpellMechanic implements SpellArea, SpellSelf { //can make inheritable to auto generate the handler
    private final DamageType damageType = DamageType.FIRE;

    private final DotHandler dotHandler;

    public Immolate() {

        dotHandler = (DotHandler) addHandler(new DotHandler("immolate", Genesis.getMath().getRandom().nextInt(), damageType, this));
        new BukkitRunnable() {
            public void run() {
                boolean cancelled = dotHandler.getTask().isCancelled();
                if(cancelled) {
                    Bukkit.broadcastMessage("cancelled");
                    cancel();
                }
            }
        }.runTaskTimer(Ollivanders.getInstance(),0L,1L);
    }

    @Override
    public boolean castArea() {

        int ceil = (int) Math.ceil(effectiveDuration());
        SpellHelper.updateDamageTimers(effectiveMagnitude(),damageType, nearbyLivingEntities(), ceil,dotHandler); //make a better damage calculation
        return true;
    }

    @Override
    public boolean castSelf() {
        return genericCastMethod();
    }

    public boolean genericCastMethod() {
        return true;
    }
}
