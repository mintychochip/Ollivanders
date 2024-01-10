package mintychochip.ollivanders.container;

import org.bukkit.entity.LivingEntity;

public class DamagePacket {

    private double damage;

    private final LivingEntity inflicted;

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
