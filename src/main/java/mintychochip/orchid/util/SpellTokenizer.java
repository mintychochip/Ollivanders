package mintychochip.orchid.util;

import mintychochip.orchid.container.Keyword;
import mintychochip.orchid.container.Modifier;
import mintychochip.orchid.container.PackagedModifier;
import mintychochip.orchid.registry.OrchidRegistry;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SpellTokenizer {
    private final Map<String, Keyword> tokenizedSpell = new HashMap<>();
    private final List<PackagedModifier> packagedModifiers;
    private final String mechanicName;
    private final String shape;
    private final String wait;

    public SpellTokenizer(String spell) {
        StringBuilder string = new StringBuilder();
        Keyword current = null;
        for (String s : spell.toUpperCase().split(" ")) {
            if (OrchidRegistry.getKeywordAlias().containsKey(s)) {
                tokenizedSpell.put(string.toString().strip(), current);
                current = OrchidRegistry.getKeywordAlias().get(s);
                string = new StringBuilder();
            }
            string.append(s).append(" ");
        }
        tokenizedSpell.put(string.toString(), current);

        packagedModifiers = makePackagedModifiers();
        mechanicName = getElement(Keyword.MECHANIC);
        shape = getElement(Keyword.SHAPE);
        wait = getElement(Keyword.WAIT);
    }

    public List<PackagedModifier> makePackagedModifiers() {
        List<PackagedModifier> modifiers = new ArrayList<>();
        for (String modifier : getCheckableStrings(Keyword.MODIFIER)) {
            Modifier modifierType = null;
            String value = null;
            for (String token : modifier.split(" ")) { //could opt for string builder to have two words but idk
                if (OrchidRegistry.getNumericalAlias().containsKey(token)) {
                    value = OrchidRegistry.getNumericalAlias().get(token);
                }
                if (OrchidRegistry.getModifierAlias().containsKey(token)) {
                    modifierType = OrchidRegistry.getModifierAlias().get(token);
                }
            }
            modifiers.add(new PackagedModifier(modifierType, value));
        }
        return modifiers;
    }

    public String getElement(Keyword keyword) {
        String holder = null;
        for (String s : tokenizedSpell.keySet()) {
            if (tokenizedSpell.get(s) == keyword) {
                holder = s;
                break;
            }
        }
        StringBuilder name = new StringBuilder();
        if (holder != null) {
            for (String s : holder.split(" ")) {
                if (!OrchidRegistry.getKeywordAlias().containsKey(s)) {
                    name.append(s).append(" ");
                }
            }
        }
        return name.toString().strip();
    }


    public List<String> getCheckableStrings(Keyword keyword) {
        List<String> result = new ArrayList<>();
        for (String s : tokenizedSpell.keySet()) {
            if (tokenizedSpell.get(s) == keyword) {
                result.add(s);
            }
        }
        return result;
    }



    public List<PackagedModifier> getPackagedModifiers() {
        return packagedModifiers;
    }

    public Map<String, Keyword> getTokenizedSpell() {
        return tokenizedSpell;
    }

    public String getMechanicName() {
        return mechanicName;
    }

    public String getShape() {
        return shape;
    }
}
