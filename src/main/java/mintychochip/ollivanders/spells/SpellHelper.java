package mintychochip.ollivanders.spells;

import mintychochip.ollivanders.api.SpellDamageEvent;
import mintychochip.ollivanders.container.DamagePacket;
import mintychochip.ollivanders.container.SpellMechanic;
import mintychochip.ollivanders.enums.DamageType;
import mintychochip.ollivanders.handler.DotHandler;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitTask;

import java.util.List;
import java.util.Map;

public class SpellHelper {

    public static void inflictSpellDamage(LivingEntity inflicted, SpellMechanic mechanic, DamageType damageType, double damage) {
        Bukkit.getPluginManager().callEvent(new SpellDamageEvent(inflicted, mechanic, damageType, damage));
        inflicted.damage(damage);
    }

    public static void updateDamageTimers(double damage, DamageType type, List<LivingEntity> livingEntityList, double add, DotHandler handler) {
        if(!type.isDamageOverTime()) {
            return;
        }
        Long totalTime = 0L;
        NamespacedKey genesisKey = type.getGenesisKey();

        for (LivingEntity livingEntity : livingEntityList) {
            PersistentDataContainer persistentDataContainer = livingEntity.getPersistentDataContainer();
            if (persistentDataContainer.has(genesisKey, PersistentDataType.LONG)) {
                totalTime = persistentDataContainer.get(genesisKey, PersistentDataType.LONG);
            }
            totalTime += Math.round((float) Math.ceil(add));
            persistentDataContainer.set(genesisKey, PersistentDataType.LONG, totalTime / type.getInterval()); //updates the dot time for each livingEntity

            if (!handler.getDamagePacketMap().containsKey(livingEntity) && add > 0) {
                if (livingEntity.getHealth() > damage) {
                    handler.getDamagePacketMap().put(livingEntity, new DamagePacket(damage, livingEntity));
                }
            }
        }
    }
}
