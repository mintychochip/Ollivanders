package mintychochip.ollivanders.handler;

import mintychochip.ollivanders.Ollivanders;
import mintychochip.ollivanders.container.DamagePacket;
import mintychochip.ollivanders.container.SpellMechanic;
import mintychochip.ollivanders.enums.DamageType;
import mintychochip.ollivanders.spells.SpellHelper;
import org.bukkit.entity.LivingEntity;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DotHandler {

    private Map<LivingEntity, DamagePacket> damagePacketMap = new ConcurrentHashMap<>();

    private SpellMechanic spellMechanic;

    private DamageType damageType;

    public DotHandler(DamageType type, SpellMechanic spellMechanic) {
        this.damageType = type;
        this.spellMechanic = spellMechanic;
        new BukkitRunnable() {
            public void run() {
                if (!damagePacketMap.isEmpty()) {
                    for (LivingEntity livingEntity : damagePacketMap.keySet()) {
                        DamagePacket damagePacket = damagePacketMap.get(livingEntity);
                        PersistentDataContainer persistentDataContainer = livingEntity.getPersistentDataContainer();
                        if (!livingEntity.isDead() || persistentDataContainer.get(damageType.getGenesisKey(), PersistentDataType.LONG) <= 0) {
                            cancel();
                        }
                        Long time = persistentDataContainer.get(damageType.getGenesisKey(), PersistentDataType.LONG);
                        if (time != null && time > 0) {
                            SpellHelper.inflictSpellDamage(livingEntity, spellMechanic, damageType, damagePacket.getDamage());
                            livingEntity.getPersistentDataContainer().set(damageType.getGenesisKey(), PersistentDataType.LONG, --time);
                        }
                    }
                }
            }
        }.runTaskTimer(Ollivanders.getInstance(), type.getDelay(), type.getInterval());
    }

    public SpellMechanic getSpellMechanic() {
        return spellMechanic;
    }

    public Map<LivingEntity, DamagePacket> getDamagePacketMap() {
        return damagePacketMap;
    }
}
