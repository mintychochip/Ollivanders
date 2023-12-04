package mintychochip.ollivanders.wand.container;

import java.io.Serializable;

public class WandBoost implements Serializable {

    private static final long serialVersionUID = 120594823L;
    private double range;
    private double magnitude;
    private double haste;
    private double efficiency;
    private double duration;

    public WandBoost() {
        range = 0;
        magnitude = 0;
        haste = 0;
        efficiency = 0;
        duration = 0;
    }
    public WandBoost addAll(double v) {
        efficiency += v;
        range += v;
        magnitude += v;
        duration += v;
        haste += v;
        return this;
    }
    public WandBoost multiplyAllIf(double w, double y) {
        efficiency *= (efficiency < 0 ? w : y);
        range *= (range < 0 ? w : y);
        magnitude *= (magnitude < 0 ? w : y);
        duration *= (duration < 0 ? w : y);
        haste *= (haste < 0 ? w : y);
        return this;
    }

    public WandBoost multiplyRange(double v) {
        range *= v;
        return this;
    }

    public WandBoost multiplyPower(double v) {
        magnitude *= v;
        return this;
    }

    public WandBoost multiplyHaste(double v) {
        haste *= v;
        return this;
    }

    public WandBoost multiplyCost(double v) {
        efficiency *= v;
        return this;
    }

    public WandBoost multiplyDuration(double v) {
        duration *= v;
        return this;
    }

    public WandBoost addRange(double v) {
        range += v;
        return this;
    }

    public WandBoost addPower(double v) {
        magnitude += v;
        return this;
    }

    public WandBoost addHaste(double v) {
        haste += v;
        return this;
    }

    public WandBoost addCost(double v) {
        efficiency += v;
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

    public double getMagnitude() {
        return magnitude;
    }

    public void setMagnitude(double magnitude) {
        this.magnitude = magnitude;
    }

    public double getHaste() {
        return haste;
    }

    public void setHaste(double haste) {
        this.haste = haste;
    }

    public double getEfficiency() {
        return efficiency;
    }

    public void setEfficiency(double efficiency) {
        this.efficiency = efficiency;
    }

    public double getDuration() {
        return duration;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

    public void addAll(WandBoost boost) {
        this.haste += boost.haste;
        this.efficiency += boost.efficiency;
        this.range += boost.range;
        this.duration += boost.duration;
        this.magnitude += boost.magnitude;
    }

    public String toString() {
        return String.format("%s %s %s %s %s", haste, efficiency, range, duration, magnitude);
    }
}
