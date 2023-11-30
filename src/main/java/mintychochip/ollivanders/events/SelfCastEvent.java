package mintychochip.ollivanders.events;

import mintychochip.ollivanders.container.WizardMechanic;
import mintychochip.ollivanders.container.WizardSpell;
import mintychochip.ollivanders.spellcaster.WizardCaster;
import org.bukkit.event.HandlerList;

public class SelfCastEvent extends WizardEvent {

    public SelfCastEvent(WizardCaster.Shape shape, WizardMechanic mechanic, WizardSpell spell) {
        super(shape, mechanic, spell);
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }
}
