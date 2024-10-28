package mintychochip.ollivanders.handler;

import mintychochip.genesis.manager.GenesisHandler;
import mintychochip.ollivanders.Ollivanders;
import mintychochip.ollivanders.api.EntitySpellDamageEvent;
import mintychochip.ollivanders.container.DamagePacket;
import mintychochip.ollivanders.container.SpellMechanic;
import mintychochip.ollivanders.enums.DamageType;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DotHandler extends GenesisHandler {

    private final Map<LivingEntity, DamagePacket> damagePacketMap = new ConcurrentHashMap<>();
    private final DamageType damageType;
    private final BukkitTask task;

    private int id;
    private boolean cancellable = false;

    private final SpellMechanic mechanic;

    public DotHandler(String name, int id, DamageType type, SpellMechanic mechanic) {
        super(name, id);
        this.damageType = type;
        this.mechanic = mechanic;
        this.task = new BukkitRunnable() {
            public void run() {
                if (!damagePacketMap.isEmpty()) {
                    for (LivingEntity livingEntity : damagePacketMap.keySet()) {
                        DamagePacket damagePacket = damagePacketMap.get(livingEntity);
                        PersistentDataContainer persistentDataContainer = livingEntity.getPersistentDataContainer();
                        Long time = persistentDataContainer.get(damageType.getGenesisKey(), PersistentDataType.LONG);
                        if (time == null) {
                            return;
                        }
                        if (livingEntity.isDead() || time <= 0) {
                            damagePacketMap.remove(livingEntity);
                            livingEntity.setVisualFire(false);
                        } else {
                            if (time != null && time > 0) {
                                if (type.isVisualFire()) {
                                    livingEntity.setVisualFire(true);
                                }
                                inflictSpellDamage(damagePacket.getInflicted(), damagePacket);
                                livingEntity.getPersistentDataContainer().set(damageType.getGenesisKey(), PersistentDataType.LONG, --time);
                            }
                        }
                    }
                } else {
                    if (cancellable) {
                        cancel();
                    }
                }
            }
        }.runTaskTimer(Ollivanders.getInstance(), type.getDelay(), type.getInterval());
    }

    public SpellMechanic getMechanic() {
        return mechanic;
    }

    public Map<LivingEntity, DamagePacket> getDamagePacketMap() {
        return damagePacketMap;
    }

    public BukkitTask getTask() {
        return task;
    }

    public void inflictSpellDamage(LivingEntity inflicted, DamagePacket packet) {
        Bukkit.getPluginManager().callEvent(new EntitySpellDamageEvent(packet.getInflicted(), EntityDamageEvent.DamageCause.CUSTOM, this, packet));
    }

    public void updateDamageTimers(double damage, DamageType type, List<LivingEntity> livingEntityList, int add, DotHandler handler) {
        if (!type.isDamageOverTime()) {
            return;
        }
        Long totalTime = 0L;
        NamespacedKey genesisKey = type.getGenesisKey();

        for (LivingEntity livingEntity : livingEntityList) {
            PersistentDataContainer persistentDataContainer = livingEntity.getPersistentDataContainer();
            if (persistentDataContainer.has(genesisKey, PersistentDataType.LONG)) {
                totalTime = persistentDataContainer.get(genesisKey, PersistentDataType.LONG);
            }
            totalTime += add;
            persistentDataContainer.set(genesisKey, PersistentDataType.LONG, totalTime / type.getInterval()); //updates the dot time for each livingEntity
            if (!handler.getDamagePacketMap().containsKey(livingEntity) && add > 0) {
                handler.getDamagePacketMap().put(livingEntity, new DamagePacket(damage, livingEntity));
            }
        }
    }

    @Override
    public void cancel() {
        cancellable = true;
    }
}
