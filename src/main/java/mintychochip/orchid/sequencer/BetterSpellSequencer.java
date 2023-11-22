package mintychochip.orchid.sequencer;

import mintychochip.orchid.Orchid;
import mintychochip.orchid.container.*;
import mintychochip.orchid.registry.MechanicRegistry;
import mintychochip.orchid.registry.OrchidRegistry;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class BetterSpellSequencer {

    private OrchidSpell orchidSpell = new OrchidSpell();

    private String spell;

    public BetterSpellSequencer setSpell(String spell) {
        this.spell = spell;
        try {
            Orchid.getTokenizer().setTokenizedSpell(spell);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return this;
    }
    public void setMechanic() throws IOException {
        String mechanicName = Orchid.getTokenizer().getElement(Keyword.MECHANIC);
        if (mechanicName == null) {
            throw new IOException("Could not find a tokenized mechanic");
        }

        OrchidMechanic mechanic = MechanicRegistry.getMechanic(mechanicName);
        try {
            orchidSpell.setMechanic(mechanic.getClass().getDeclaredConstructor().newInstance());
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException |
                 NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
        OrchidMechanic instancedMechanic = orchidSpell.getMechanic();
        //instancedMechanic.setMechanicSettings(MechanicRegistry.getMechanicSettings().get(mechanic));
        instancedMechanic.setName(mechanicName);
    }
    
    public void setModifiers() {
        List<PackagedModifier> packagedModifiers = Orchid.getTokenizer().getPackagedModifiers();
        if (packagedModifiers != null) {
            OrchidModifier orchidModifier = new OrchidModifier();
            for (PackagedModifier packagedModifier : packagedModifiers) {
                switch (packagedModifier.getType()) {
                    case VELOCITY: {
                        float v = Float.parseFloat(packagedModifier.getValue());
                        orchidModifier.setVelocity(v);
                    }
                    case MAGNITUDE: {
                        float v = Float.parseFloat(packagedModifier.getValue());
                        orchidModifier.setMagnitude(v);
                    }
                    case ENCHANTMENT: {
                        String v = packagedModifier.getValue();
                        //add a way to grab enchant from registry
                    }
                }
            }
            if (orchidSpell.getMechanic() != null) {
                orchidSpell.getMechanic().setOrchidModifier(orchidModifier);
            }
        }
    }
    public void setShape() {
        String shape = Orchid.getTokenizer().getElement(Keyword.SHAPE);
        if (shape != null) {
            orchidSpell.getMechanic().setShape(OrchidRegistry.getShapeAlias().get(shape));
        }
    }
    public void clearSpell() {
        orchidSpell = null;
    }

    public OrchidSpell build() {
        try {
            setMechanic();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        setModifiers();
        setShape();
        return orchidSpell;
    }

}
