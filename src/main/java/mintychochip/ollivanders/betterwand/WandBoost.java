package mintychochip.ollivanders.betterwand;

import java.io.Serializable;

public class WandBoost implements Serializable {

    private double range;
    private double power;
    private double cooldown;
    private double cost;
    private double duration;

    public WandBoost addRange(double v) {
        range += v;
        return this;
    }

    public WandBoost addPower(double v) {
        power += v;
        return this;
    }

    public WandBoost addCooldown(double v) {
        cooldown += v;
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

    public double getCooldown() {
        return cooldown;
    }

    public void setCooldown(double cooldown) {
        this.cooldown = cooldown;
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
        this.cooldown += boost.cooldown;
        this.cost += boost.cost;
        this.range += boost.range;
        this.duration += boost.duration;
        this.power += boost.power;
    }
}
