package mintychochip.ollivanders.container;

import java.util.List;

public class SpellMechanic {

    protected Context context;
    protected List<String> alias;
    protected MechanicSettings mechanicSettings;
    protected MechanicModifier mechanicModifier;
    protected Spell transition;

    public SpellMechanic(MechanicSettings mechanicSettings) {
        this.mechanicSettings = mechanicSettings;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public List<String> getAlias() {
        return alias;
    }

    public void setAlias(List<String> alias) {
        this.alias = alias;
    }

    public MechanicModifier getMechanicModifier() {
        return mechanicModifier;
    }

    public void setMechanicModifier(MechanicModifier mechanicModifier) {
        this.mechanicModifier = mechanicModifier;
    }

    public Spell getTransition() {
        return transition;
    }

    public void setTransition(Spell transition) {
        this.transition = transition;
    }
}
