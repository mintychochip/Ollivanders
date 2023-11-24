package mintychochip.ollivanders.shape.implementation;

import mintychochip.ollivanders.container.Context;
import mintychochip.ollivanders.container.WizardMechanic;
import org.bukkit.entity.Player;

public abstract class Implementation {

    protected final WizardMechanic mechanic; // can generalize this, make a base class
    protected Context context;
    protected Player player;

    protected Implementation(WizardMechanic mechanic) {
        this.mechanic = mechanic;
        Context context = null;
        if (mechanic != null) {
            mechanic.setImplementation(this);
            context = mechanic.getContext();
        }
        if (context != null) {
            this.context = context;
            this.player = context.getPlayer();
        }
    }
}
