package mintychochip.ollivanders.spells;

import mintychochip.genesis.Genesis;
import mintychochip.ollivanders.Ollivanders;
import mintychochip.ollivanders.container.SpellMechanic;
import mintychochip.ollivanders.enums.DamageType;
import mintychochip.ollivanders.handler.DotHandler;
import mintychochip.ollivanders.spells.shape.SpellArea;
import mintychochip.ollivanders.spells.shape.SpellSelf;

public class Immolate extends SpellMechanic implements SpellArea, SpellSelf { //can make inheritable to auto generate the handler

    private final DamageType damageType = DamageType.FIRE;
    private final DotHandler dotHandler = new DotHandler(damageType,this); //can be more dynamic

    @Override
    public boolean castArea() {

        int ceil = (int) Math.ceil(effectiveDuration());
        SpellHelper.updateDamageTimers(effectiveMagnitude(),damageType, nearbyLivingEntities(), effectiveDuration(),dotHandler); //make a better damage calculation
        return true;
    }

    @Override
    public boolean castSelf() {
        return genericCastMethod();
    }

    public boolean genericCastMethod() {
        return true;
    }
}
