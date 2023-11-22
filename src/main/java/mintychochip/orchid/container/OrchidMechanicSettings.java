package mintychochip.orchid.container;

import java.util.List;

public class OrchidMechanicSettings {
    private boolean isMagnitudeAffectable;
    private int range;
    private int duration;
    private int cost;
    private double cooldown;
    private boolean isCantrip;
    public boolean isMagnitudeAffectable() {
        return isMagnitudeAffectable;
    }
    private List<String> keywords;

    private boolean bendable;

   private int damage;

    public void setMagnitudeAffectable(boolean magnitudeAffectable) {
        isMagnitudeAffectable = magnitudeAffectable;
    }

    public int getRange() {
        return range;
    }

    public void setRange(int range) {
        this.range = range;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public double getCooldown() {
        return cooldown;
    }

    public void setCooldown(double cooldown) {
        this.cooldown = cooldown;
    }

    public boolean isCantrip() {
        return isCantrip;
    }

    public void setCantrip(boolean cantrip) {
        isCantrip = cantrip;
    }

    public List<String> getKeywords() {
        return keywords;
    }

    public void setKeywords(List<String> keywords) {
        this.keywords = keywords;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }
}
