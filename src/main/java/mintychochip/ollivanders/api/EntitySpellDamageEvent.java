package mintychochip.ollivanders.api;

import mintychochip.genesis.manager.GenesisHandler;
import mintychochip.ollivanders.container.DamagePacket;
import mintychochip.ollivanders.enums.DamageType;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.entity.EntityDamageEvent;
import org.jetbrains.annotations.NotNull;

public class EntitySpellDamageEvent extends EntityDamageEvent {
    private final LivingEntity inflicted;
    private final GenesisHandler process;
    private final DamagePacket damagePacket;
    public EntitySpellDamageEvent(@NotNull LivingEntity damagee, @NotNull EntityDamageEvent.DamageCause cause, GenesisHandler process, DamagePacket damagePacket) {
        super(damagee, cause, damagePacket.getDamage());
        this.inflicted = damagee;
        this.process = process;
        this.damagePacket = damagePacket;
    }


    public DamagePacket getDamagePacket() {
        return damagePacket;
    }

    public GenesisHandler getProcess() {
        return process;
    }
    public LivingEntity getInflicted() {
        return inflicted;
    }
}
