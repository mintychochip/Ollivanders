package mintychochip.orchid.sequencer;

import mintychochip.orchid.Orchid;
import mintychochip.orchid.container.Keyword;
import mintychochip.orchid.container.OrchidMechanic;
import mintychochip.orchid.container.OrchidSpell;
import mintychochip.orchid.registry.MechanicRegistry;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class BetterSpellSequencer {

    private OrchidSpell orchidSpell = new OrchidSpell();

    private String spell;

    private void setSpell(String spell) {
        this.spell = spell;
    }
    private void setMechanic() throws IOException {
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
        instancedMechanic.setMechanicSettings(MechanicRegistry.getMechanicSettings().get(mechanic));
        instancedMechanic.setName(mechanicName);
    }

    private void clearSpell() {
        orchidSpell = null;
    }

}
