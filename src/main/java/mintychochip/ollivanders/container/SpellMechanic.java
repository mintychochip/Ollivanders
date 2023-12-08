package mintychochip.ollivanders.container;

import mintychochip.ollivanders.enums.Shape;

public abstract class SpellMechanic implements Cloneable {

    protected Context context;
    protected MechanicSettings mechanicSettings;
    protected MechanicModifier mechanicModifier;
    protected Spell transition;

    protected Shape shape;

    public void setShape(Shape shape) {
        this.shape = shape;
    }

    public Shape getShape() {
        return shape;
    }

    public void setMechanicSettings(MechanicSettings mechanicSettings) {
        this.mechanicSettings = mechanicSettings;
    }

    public MechanicSettings getMechanicSettings() {
        return mechanicSettings;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
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

    @Override
    public SpellMechanic clone() throws CloneNotSupportedException {
        return (SpellMechanic) super.clone();
    }
}
