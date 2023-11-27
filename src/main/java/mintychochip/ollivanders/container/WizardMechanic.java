package mintychochip.ollivanders.container;

import mintychochip.ollivanders.betterwand.WandBoost;
import mintychochip.ollivanders.shape.Shape;
import mintychochip.ollivanders.shape.implementation.Implementation;

public abstract class WizardMechanic { //make all protected

    protected WandBoost wandBoost;
    protected WizardMechanicSettings mechanicSettings;
    protected Implementation implementation;
    protected WizardSpell Transition;
    protected Context context;
    protected WizardModifier wizardModifier;
    protected Shape shape;
    protected long delay;
    protected String name;

    public WandBoost getWandBoost() {
        return wandBoost;
    }

    public void setWandBoost(WandBoost wandBoost) {
        this.wandBoost = wandBoost;
    }

    public WizardSpell getTransition() {
        return Transition;
    }

    public void setTransition(WizardSpell transition) {
        Transition = transition;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public WizardModifier getWizardModifier() {
        return wizardModifier;
    }

    public void setWizardModifier(WizardModifier WizardModifier) {
        this.wizardModifier = WizardModifier;
    }

    public Shape getShape() {
        return shape;
    }

    public void setShape(Shape shape) {
        this.shape = shape;
    }

    public long getDelay() {
        return delay;
    }

    public void setDelay(long delay) {
        this.delay = delay;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Implementation getImplementation() {
        return implementation;
    }

    public void setImplementation(Implementation implementation) {
        this.implementation = implementation;
    }

    public WizardMechanicSettings getMechanicSettings() {
        return mechanicSettings;
    }

    public void setMechanicSettings(WizardMechanicSettings mechanicSettings) {
        this.mechanicSettings = mechanicSettings;
    }
}