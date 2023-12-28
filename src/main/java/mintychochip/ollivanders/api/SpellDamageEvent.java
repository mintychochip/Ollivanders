package mintychochip.ollivanders.api;

import mintychochip.ollivanders.container.SpellMechanic;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class SpellDamageEvent extends Event {

    private static final HandlerList handlers = new HandlerList();
    private final LivingEntity inflicted;
    private final SpellMechanic spellMechanic;

    private final double damage;

    public SpellDamageEvent(LivingEntity inflicted, SpellMechanic spellMechanic, double damage) {
        this.spellMechanic = spellMechanic;
        this.inflicted = inflicted;
        this.damage = damage;
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

    public SpellMechanic getSpellMechanic() {
        return spellMechanic;
    }
}
