package mintychochip.ollivanders.enums;

import mintychochip.genesis.Genesis;
import mintychochip.ollivanders.Ollivanders;
import org.bukkit.NamespacedKey;

public enum DamageType {

    CURSE(true,3L,1L,false,"curse",true,true),

    FIRE(true,3L,1L,true,"fire",true,true),

    AIR(false,3L,1L,false,"air",false,false),

    WATER(false,3L,1L,false,"water", false, false);

    private final boolean damageOverTime;

    private final long delay;

    private final long interval;

    private final boolean visualFire;

    private final NamespacedKey genesisKey;

    private final boolean ignoresArmor;

    private final boolean ignoresResistance;

    DamageType(boolean damageOverTime, long delay, long interval, boolean visualFire, String key, boolean ignoresArmor, boolean ignoresResistance) {
        this.damageOverTime = damageOverTime;
        this.delay = delay;
        this.interval = interval;
        this.visualFire = visualFire;
        this.genesisKey = Genesis.getKeys().addKey(Ollivanders.getInstance(),key);
        //can use this for mythic hook
        this.ignoresArmor = ignoresArmor;
        this.ignoresResistance = ignoresResistance;
    }

    public boolean isIgnoresArmor() {
        return ignoresArmor;
    }

    public boolean isIgnoresResistance() {
        return ignoresResistance;
    }

    public boolean isDamageOverTime() {
        return damageOverTime;
    }

    public long getDelay() {
        return delay;
    }

    public long getInterval() {
        return interval;
    }

    public boolean isVisualFire() {
        return visualFire;
    }

    public NamespacedKey getGenesisKey() {
        return genesisKey;
    }
}
