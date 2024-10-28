package mintychochip.ollivanders.container;

import mintychochip.genesis.particle.GenesisShape;
import mintychochip.ollivanders.enums.DamageType;
import org.bukkit.Particle;
import org.bukkit.entity.EntityType;

import java.util.List;

public class MechanicSettings { //list of settings set by config
    private List<String> keywords;
    private double duration;
    private double cooldown;
    private double cost;
    private double range;
    private boolean persistent;
    private double magnitude;
    private EntityType entityType;
    private double damage;
    private long interval;
    private GenesisShape genesisShape;
    private List<Particle> particleList;
    private DamageType damageType;
    private int spellInventoryCustomModel;

    public int getSpellInventoryCustomModel() {
        return spellInventoryCustomModel;
    }

    public MechanicSettings setSpellInventoryCustomModel(int spellInventoryCustomModel) {
        this.spellInventoryCustomModel = spellInventoryCustomModel;
        return this;
    }

    public List<Particle> getParticleList() {
        return particleList;
    }

    public MechanicSettings setParticleList(List<Particle> particleList) {
        this.particleList = particleList;
        return this;
    }

    public GenesisShape getGenesisShape() {
        return genesisShape;
    }

    public MechanicSettings setGenesisShape(GenesisShape genesisShape) {
        this.genesisShape = genesisShape;
        return this;
    }

    public List<String> getKeywords() {
        return keywords;
    }

    public MechanicSettings setKeywords(List<String> keywords) {
        this.keywords = keywords;
        return this;
    }

    public double getDuration() {
        return duration;
    }

    public MechanicSettings setDuration(double duration) {
        this.duration = duration;
        return this;
    }

    public double getCooldown() {
        return cooldown;
    }

    public MechanicSettings setCooldown(double cooldown) {
        this.cooldown = cooldown;
        return this;
    }

    public double getCost() {
        return cost;
    }

    public MechanicSettings setCost(double cost) {
        this.cost = cost;
        return this;
    }

    public double getRange() {
        return range;
    }

    public MechanicSettings setRange(double range) {
        this.range = range;
        return this;
    }

    public boolean isPersistent() {
        return persistent;
    }

    public MechanicSettings setPersistent(boolean persistent) {
        this.persistent = persistent;
        return this;
    }

    public double getMagnitude() {
        return magnitude;
    }

    public MechanicSettings setMagnitude(double magnitude) {
        this.magnitude = magnitude;
        return this;
    }

    public DamageType getDamageType() {
        return damageType;
    }

    public void setDamageType(DamageType damageType) {
        this.damageType = damageType;
    }

    public EntityType getEntityType() {
        return entityType;
    }

    public MechanicSettings setEntityType(EntityType entityType) {
        this.entityType = entityType;
        return this;
    }

    public double getDamage() {
        return damage;
    }

    public MechanicSettings setDamage(double damage) {
        this.damage = damage;
        return this;
    }

    public long getInterval() {
        return interval;
    }

    public MechanicSettings setInterval(long interval) {
        this.interval = interval;
        return this;
    }

    public void copyTo(MechanicSettings settings) {
        settings.setRange(range)
                .setDuration(duration)
                .setPersistent(persistent)
                .setMagnitude(magnitude)
                .setCooldown(cooldown)
                .setCost(cost)
                .setDamage(damage)
                .setEntityType(entityType);
    }

    public String toString() {
        return String.format("duration%s cooldown%s cost%s range%s persistent%s magnitude%s damage%s entityType%s", duration, cooldown, cost, range, persistent, magnitude, damage, entityType.toString());
    }
}
