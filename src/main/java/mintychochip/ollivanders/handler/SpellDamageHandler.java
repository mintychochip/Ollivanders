package mintychochip.ollivanders.handler;

import mintychochip.ollivanders.Ollivanders;
import mintychochip.ollivanders.container.SpellMechanic;
import mintychochip.ollivanders.spells.SpellHelper;
import org.bukkit.entity.LivingEntity;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class SpellDamageHandler {

    private HashMap<LivingEntity, SpellMechanic> immolate = new HashMap<>();
    public SpellDamageHandler() {
        new BukkitRunnable() {
            public void run() {
                for (LivingEntity livingEntity : immolate.keySet()) {
                    if(livingEntity.isDead()) {
                        immolate.remove(livingEntity);
                    }
                    livingEntity.setVisualFire(true);
                    SpellHelper.inflictSpellDamage(livingEntity,immolate.get(livingEntity),1);
                }
            }
        }.runTaskTimer(Ollivanders.getInstance(),3L,1L);
    }

    public HashMap<LivingEntity, SpellMechanic> getImmolate() {
        return immolate;
    }
}
