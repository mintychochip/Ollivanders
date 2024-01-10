package mintychochip.ollivanders.api;

import mintychochip.genesis.manager.GenesisHandler;
import mintychochip.ollivanders.enums.DamageType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class SpellDamageEvent extends Event {

    private static final HandlerList handlers = new HandlerList();
    private final LivingEntity inflicted;
    private final GenesisHandler process;
    private final DamageType damageType;
    private final double damage;

    public SpellDamageEvent(LivingEntity inflicted, GenesisHandler process, DamageType damageType, double damage) {
        this.inflicted = inflicted;
        this.process = process;
        this.damageType = damageType;
        this.damage = damage;
    }

    public GenesisHandler getProcess() {
        return process;
    }

    @NotNull
    @Override
    public HandlerList getHandlers() {
        return handlers;
    }
    public static HandlerList getHandlerList() {
        return handlers;
    }

    public LivingEntity getInflicted() {
        return inflicted;
    }

    public double getDamage() {
        return damage;
    }


    public DamageType getDamageType() {
        return damageType;
    }
}
