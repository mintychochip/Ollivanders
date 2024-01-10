package mintychochip.ollivanders.container;

public class Spell {

    private SpellMechanic mechanic;
    private SpellSettings spellSettings;

    public void setMechanic(SpellMechanic mechanic) {
        this.mechanic = mechanic;
    }

    public SpellMechanic getMechanic() {
        return mechanic;
    }
}

