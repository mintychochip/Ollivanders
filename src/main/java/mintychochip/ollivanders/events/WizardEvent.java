package mintychochip.ollivanders.events;

import mintychochip.ollivanders.container.WizardMechanic;
import mintychochip.ollivanders.shape.Shape;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class WizardEvent extends Event {
    public static final HandlerList handlers = new HandlerList();
    private final Shape shape;
    private final WizardMechanic mechanic;

    public WizardEvent(Shape shape, WizardMechanic mechanic) {
        this.shape = shape;
        this.mechanic = mechanic;
    }

    public Shape getShape() {
        return shape;
    }

    public WizardMechanic getMechanic() {
        return mechanic;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }
    public static HandlerList getHandlerList() {
        return handlers;
    }
}
