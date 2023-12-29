package mintychochip.ollivanders.container;

import mintychochip.ollivanders.enums.DamageType;
import org.bukkit.entity.LivingEntity;

public class DamagePacket {

    private final double damage;

    private final LivingEntity inflicted;

    public DamagePacket(double damage, LivingEntity inflicted) {
        this.damage = damage;
        this.inflicted = inflicted;
    }
    public double getDamage() {
        return damage;
    }
    public LivingEntity getInflicted() {
        return inflicted;
    }
}
