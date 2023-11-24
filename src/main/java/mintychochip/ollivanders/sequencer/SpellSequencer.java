package mintychochip.ollivanders.sequencer;

import mintychochip.ollivanders.container.WizardMechanic;
import mintychochip.ollivanders.container.WizardModifier;
import mintychochip.ollivanders.container.WizardSpell;
import mintychochip.ollivanders.container.PackagedModifier;
import mintychochip.ollivanders.registry.MechanicRegistry;
import mintychochip.ollivanders.registry.WizardRegistry;
import mintychochip.ollivanders.util.SpellTokenizer;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class SpellSequencer {

    private WizardSpell WizardSpell;
    private final SpellTokenizer tokenizer;
    private final String spell;

    public SpellSequencer(String spell) {
        this.spell = spell;
        tokenizer = new SpellTokenizer(spell);
        setup();
        if (!isValidSpell()) {
            spell = null;
        }
    }
    public void setup() {
        String name = tokenizer.getMechanicName();
        if (name != null) {
            WizardSpell = new WizardSpell();
            WizardMechanic mechanic = MechanicRegistry.getMechanics().get(name);
            try {
                WizardSpell.setMechanic(mechanic.getClass().getDeclaredConstructor().newInstance());
            } catch (InvocationTargetException | InstantiationException | IllegalAccessException |
                     NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
            WizardSpell.getMechanic().setMechanicSettings(MechanicRegistry.getMechanicSettings().get(mechanic));
            WizardSpell.getMechanic().setName(name);
        }
        List<PackagedModifier> packagedModifiers = tokenizer.getPackagedModifiers();
        if (packagedModifiers != null) {
            WizardModifier WizardModifier = new WizardModifier();
            for (PackagedModifier packagedModifier : tokenizer.getPackagedModifiers()) {
                switch (packagedModifier.getType()) {
                    case VELOCITY: {
                        float v = Float.parseFloat(packagedModifier.getValue());
                        WizardModifier.setVelocity(v);
                    }
                    case MAGNITUDE: {
                        float v = Float.parseFloat(packagedModifier.getValue());
                        WizardModifier.setMagnitude(v);
                    }
                    case ENCHANTMENT: {
                        String v = packagedModifier.getValue();
                        //add a way to grab enchant from registry
                    }
                }
            }
            if (WizardSpell.getMechanic() != null) {
                WizardSpell.getMechanic().setWizardModifier(WizardModifier);
            }
        }

        String shape = tokenizer.getShape();
        if (shape != null) {
            WizardSpell.getMechanic().setShape(WizardRegistry.getShapeAlias().get(shape));
        }
    }

    public boolean isValidSpell() {
        return WizardSpell.getMechanic() != null;
    }
    public WizardSpell getWizardSpell() {
        return WizardSpell;
    }

    public String getSpell() {
        return spell;
    }

    public SpellTokenizer getTokenizer() {
        return tokenizer;
    }
}
