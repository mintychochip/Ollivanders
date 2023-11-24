package mintychochip.ollivanders.util;

import mintychochip.ollivanders.container.Keyword;
import mintychochip.ollivanders.container.Modifier;
import mintychochip.ollivanders.container.PackagedModifier;
import mintychochip.ollivanders.registry.WizardRegistry;

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
            if (WizardRegistry.getKeywordAlias().containsKey(s)) {
                tokenizedSpell.put(string.toString().strip(), current);
                current = WizardRegistry.getKeywordAlias().get(s);
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
                if (WizardRegistry.getNumericalAlias().containsKey(token)) {
                    value = WizardRegistry.getNumericalAlias().get(token);
                }
                if (WizardRegistry.getModifierAlias().containsKey(token)) {
                    modifierType = WizardRegistry.getModifierAlias().get(token);
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
                if (!WizardRegistry.getKeywordAlias().containsKey(s)) {
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
