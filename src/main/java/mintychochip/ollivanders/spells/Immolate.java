package mintychochip.ollivanders.spells;

import mintychochip.genesis.Genesis;
import mintychochip.ollivanders.container.SpellMechanic;
import mintychochip.ollivanders.enums.DamageType;
import mintychochip.ollivanders.handler.DotHandler;
import mintychochip.ollivanders.spells.shape.SpellArea;
import mintychochip.ollivanders.spells.shape.SpellSelf;

public class Immolate extends SpellMechanic implements SpellArea, SpellSelf { //can make inheritable to auto generate the handler
    private final DamageType damageType = DamageType.FIRE;

    private final DotHandler dotHandler = (DotHandler) addHandler(new DotHandler("immolate", Genesis.getMath().getRandom().nextInt(), damageType, this));

    @Override
    public boolean castArea() {
        int ceil = (int) Math.ceil(effectiveDuration());
        dotHandler.updateDamageTimers(effectiveMagnitude(), damageType, nearbyLivingEntities(true), ceil, dotHandler); //make a better damage calculation
        return true;
    }

    @Override
    public boolean castSelf() {
        int ceil = (int) Math.ceil(effectiveDuration());
        dotHandler.updateDamageTimers(effectiveMagnitude(), damageType, nearbyLivingEntities(false), ceil, dotHandler);
        return true;
    }

    public boolean genericCastMethod() {
        return true;
    }
}
