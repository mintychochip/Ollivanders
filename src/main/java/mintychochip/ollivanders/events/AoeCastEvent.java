package mintychochip.ollivanders.events;

import mintychochip.ollivanders.container.WizardMechanic;
import mintychochip.ollivanders.shape.Shape;
import org.bukkit.event.HandlerList;

public class AoeCastEvent extends WizardEvent {

    public AoeCastEvent(Shape shape, WizardMechanic WizardMechanic) {
        super(shape, WizardMechanic);
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }


}

