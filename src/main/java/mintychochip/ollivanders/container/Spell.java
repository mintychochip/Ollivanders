package mintychochip.ollivanders.container;

public class Spell {

    private SpellMechanic mechanic;

    private final SpellMeta spellMeta = new SpellMeta(System.currentTimeMillis());

    public SpellMeta getSpellMeta() {
        return spellMeta;
    }

    public void setMechanic(SpellMechanic mechanic) {
        this.mechanic = mechanic;
    }

    public SpellMechanic getMechanic() {
        return mechanic;
    }
}

