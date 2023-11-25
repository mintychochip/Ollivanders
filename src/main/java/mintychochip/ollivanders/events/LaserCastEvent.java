package mintychochip.ollivanders.events;

import mintychochip.ollivanders.container.WizardMechanic;
import mintychochip.ollivanders.container.WizardSpell;
import mintychochip.ollivanders.shape.Shape;
import org.bukkit.event.HandlerList;

public class LaserCastEvent extends WizardEvent {

    public LaserCastEvent(Shape shape, WizardMechanic mechanic, WizardSpell spell) {
        super(shape, mechanic, spell);
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }
}
