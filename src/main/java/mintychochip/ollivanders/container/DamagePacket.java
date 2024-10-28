package mintychochip.ollivanders.container;

import org.bukkit.entity.LivingEntity;

public class DamagePacket {

    private final LivingEntity inflicted;
    private double damage;

    public DamagePacket(double damage, LivingEntity inflicted) {
        this.damage = damage;
        this.inflicted = inflicted;
    }

    public double getDamage() {
        return damage;
    }

    public DamagePacket setDamage(double damage) {
        this.damage = damage;
        return this;
    }

    public LivingEntity getInflicted() {
        return inflicted;
    }
}
