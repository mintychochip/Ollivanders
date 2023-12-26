package mintychochip.ollivanders.spells;

import mintychochip.ollivanders.container.SpellMechanic;
import mintychochip.ollivanders.spells.shape.SpellArea;
import mintychochip.ollivanders.spells.shape.SpellSelf;

public class GravityWell extends SpellMechanic implements SpellArea, SpellSelf {
    @Override
    public boolean castArea() {
        return false;
    }

    @Override
    public boolean castSelf() {
        return false;
    }
}
