package mintychochip.ollivanders.container;

import org.bukkit.enchantments.Enchantment;

import java.util.List;

public class WizardModifier {


    private float velocity;
    private List<Enchantment> enchantmentList;
    private boolean split;
    private float magnitude;

    public float getVelocity() {
        return velocity;
    }

    public void setVelocity(float velocity) {
        this.velocity = velocity;
    }

    public List<Enchantment> getEnchantmentList() {
        return enchantmentList;
    }

    public void setEnchantmentList(List<Enchantment> enchantmentList) {
        this.enchantmentList = enchantmentList;
    }

    public boolean isSplit() {
        return split;
    }

    public void setSplit(boolean split) {
        this.split = split;
    }

    public void setMagnitude(float magnitude) {
        this.magnitude = magnitude;
    }

    public float getMagnitude() {
        return magnitude;
    }

}
