package mintychochip.ollivanders.container;

import org.bukkit.enchantments.Enchantment;

import java.util.List;

public class MechanicModifier {

    private float velocity;

    private float magnitude;

    private List<Enchantment> enchantmentList;

    public float getVelocity() {
        return velocity;
    }

    public void setVelocity(float velocity) {
        this.velocity = velocity;
    }

    public float getMagnitude() {
        return magnitude;
    }

    public void setMagnitude(float magnitude) {
        this.magnitude = magnitude;
    }

    public List<Enchantment> getEnchantmentList() {
        return enchantmentList;
    }

    public void setEnchantmentList(List<Enchantment> enchantmentList) {
        this.enchantmentList = enchantmentList;
    }
}
