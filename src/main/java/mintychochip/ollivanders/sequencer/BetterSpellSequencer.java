package mintychochip.ollivanders.sequencer;

import mintychochip.ollivanders.Ollivanders;
import mintychochip.ollivanders.container.*;
import mintychochip.ollivanders.registry.MechanicRegistry;
import mintychochip.ollivanders.registry.WizardRegistry;
import org.bukkit.Bukkit;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class BetterSpellSequencer {

    private WizardSpell wizardSpell = new WizardSpell();

    private String spell;

    public BetterSpellSequencer setSpell(String spell) {
        this.spell = spell;
        try {
            Ollivanders.getTokenizer().setTokenizedSpell(spell);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return this;
    }
    public void setMechanic() throws IOException {
        String mechanicName = Ollivanders.getTokenizer().getElement(Keyword.MECHANIC);
        if (mechanicName == null) {
            throw new IOException("Could not find a tokenized mechanic");
        }

        WizardMechanic mechanic = MechanicRegistry.getMechanic(mechanicName);
        try {
            wizardSpell = new WizardSpell();
            wizardSpell.setMechanic(mechanic.getClass().getDeclaredConstructor().newInstance());
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException |
                 NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
        WizardMechanic instancedMechanic = wizardSpell.getMechanic();
        instancedMechanic.setMechanicSettings(MechanicRegistry.getMechanicSettings().get(mechanic));
        instancedMechanic.setName(mechanicName);
    }
    
    public void setModifiers() {
        List<PackagedModifier> packagedModifiers = Ollivanders.getTokenizer().getPackagedModifiers();
        if (packagedModifiers != null) {
            WizardModifier WizardModifier = new WizardModifier();
            for (PackagedModifier packagedModifier : packagedModifiers) {
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
            if (wizardSpell.getMechanic() != null) {
                wizardSpell.getMechanic().setWizardModifier(WizardModifier);
            }
        }
    }
    public void setShape() {
        String shape = Ollivanders.getTokenizer().getElement(Keyword.SHAPE);
        if (shape != null) {
            wizardSpell.getMechanic().setShape(WizardRegistry.getShapeAlias().get(shape));
        }
    }
    public void clearSpell() {
        wizardSpell = null;
    }

    public WizardSpell build() {
        try {
            setMechanic();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        setModifiers();
        setShape();
        return wizardSpell;
    }

}
