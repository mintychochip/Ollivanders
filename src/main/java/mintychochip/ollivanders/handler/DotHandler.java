package mintychochip.ollivanders.handler;

import mintychochip.genesis.manager.GenesisHandler;
import mintychochip.ollivanders.Ollivanders;
import mintychochip.ollivanders.container.DamagePacket;
import mintychochip.ollivanders.container.SpellMechanic;
import mintychochip.ollivanders.enums.DamageType;
import mintychochip.ollivanders.spells.SpellHelper;
import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DotHandler extends GenesisHandler {

    private Map<LivingEntity, DamagePacket> damagePacketMap = new ConcurrentHashMap<>();
    private DamageType damageType;
    private BukkitTask task;

    private int id;
    private boolean cancellable = false;

    private SpellMechanic mechanic;


    public DotHandler(String name, int id, DamageType type, SpellMechanic mechanic) {
        super(name,id);
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
                        if(livingEntity.isDead() || time <= 0) {
                            damagePacketMap.remove(livingEntity);
                            livingEntity.setVisualFire(false);
                        }
                        else {
                            Bukkit.broadcastMessage(time + "");

                            if (time != null && time > 0) {
                                if(type.isVisualFire()) {
                                    livingEntity.setVisualFire(true);
                                }
                                SpellHelper.inflictSpellDamage(livingEntity,mechanic,damageType,damagePacket.getDamage());
                                livingEntity.getPersistentDataContainer().set(damageType.getGenesisKey(), PersistentDataType.LONG, --time);
                            }
                        }
                    }
                } else {
                    if(cancellable) {
                        Bukkit.broadcastMessage(damagePacketMap.toString());
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

    @Override
    public void cancel() {
        cancellable = true;
    }
}
