package mintychochip.ollivanders.betterwand;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.io.Serializable;

public class WandBoost implements Serializable {

    private static final long serialVersionUID = 120594823L;
    private double range;
    private double power;
    private double haste;
    private double cost;
    private double duration;

    public WandBoost() {
        range = 0;
        power = 0;
        haste = 0;
        cost = 0;
        duration = 0;
    }

    public WandBoost addRange(double v) {
        range += v;
        return this;
    }

    public WandBoost addPower(double v) {
        power += v;
        return this;
    }

    public WandBoost addHaste(double v) {
        haste += v;
        return this;
    }

    public WandBoost addCost(double v) {
        cost += v;
        return this;
    }

    public WandBoost addDuration(double v) {
        duration += v;
        return this;
    }

    public double getRange() {
        return range;
    }

    public void setRange(double range) {
        this.range = range;
    }

    public double getPower() {
        return power;
    }

    public void setPower(double power) {
        this.power = power;
    }

    public double getHaste() {
        return haste;
    }

    public void setHaste(double haste) {
        this.haste = haste;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public double getDuration() {
        return duration;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

    public void addAll(WandBoost boost) {
        this.haste += boost.haste;
        this.cost += boost.cost;
        this.range += boost.range;
        this.duration += boost.duration;
        this.power += boost.power;
    }
    public String toString() {
        return String.format("%s %s %s %s %s",haste,cost,range,duration,power);
    }
}
