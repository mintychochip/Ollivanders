package mintychochip.ollivanders.container;

import mintychochip.ollivanders.enums.DamageType;

public class DamageSpellMechanic extends SpellMechanic {

    private final DamageType damageType;
    public DamageSpellMechanic(DamageType damageType) {
        this.damageType = damageType;
    }

    public DamageType getDamageType() {
        return damageType;
    }
}
