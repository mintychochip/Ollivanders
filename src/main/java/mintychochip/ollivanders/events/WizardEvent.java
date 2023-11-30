package mintychochip.ollivanders.events;

import mintychochip.ollivanders.container.WizardMechanic;
import mintychochip.ollivanders.container.WizardSpell;
import mintychochip.ollivanders.spellcaster.WizardCaster;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class WizardEvent extends Event {
    public static final HandlerList handlers = new HandlerList();
    private final WizardCaster.Shape shape;
    private final WizardMechanic mechanic;

    private final WizardSpell spell;

    public WizardEvent(WizardCaster.Shape shape, WizardMechanic mechanic, WizardSpell spell) {
        this.shape = shape;
        this.mechanic = mechanic;
        this.spell = spell;
    }


    public WizardCaster.Shape getShape() {
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

    public WizardSpell getSpell() {
        return spell;
    }
}
