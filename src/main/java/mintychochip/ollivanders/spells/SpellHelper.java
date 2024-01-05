package mintychochip.ollivanders.spells;

import mintychochip.ollivanders.api.SpellDamageEvent;
import mintychochip.ollivanders.container.DamagePacket;
import mintychochip.ollivanders.container.SpellMechanic;
import mintychochip.ollivanders.enums.DamageType;
import mintychochip.ollivanders.handler.DotHandler;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.List;

public class SpellHelper {

    public static void inflictSpellDamage(LivingEntity inflicted, SpellMechanic mechanic, DamageType type, double damage) {
        Bukkit.getPluginManager().callEvent(new SpellDamageEvent(inflicted, mechanic, type, damage));
        inflicted.damage(damage);
    }

    public static void updateTimer(NamespacedKey key, LivingEntity nearbyLivingEntity, int add) {
        Long l = 0L;
        PersistentDataContainer persistentDataContainer = nearbyLivingEntity.getPersistentDataContainer();
        if (persistentDataContainer.has(key, PersistentDataType.LONG)) {
            l = persistentDataContainer.get(key, PersistentDataType.LONG);
        }
        l += add;
        persistentDataContainer.set(key, PersistentDataType.LONG, l);
    }

    public static void updateDamageTimers(double damage, DamageType type, List<LivingEntity> livingEntityList, int add, DotHandler handler) {
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
}
