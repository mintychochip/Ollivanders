package mintychochip.ollivanders.util;

import mintychochip.ollivanders.container.Keyword;
import mintychochip.ollivanders.container.Modifier;
import mintychochip.ollivanders.container.PackagedModifier;
import mintychochip.ollivanders.registry.WizardRegistry;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BetterSpellTokenizer {

    private Map<String,Keyword> tokenizedSpell = new HashMap<>();

    public void setTokenizedSpell(String spell) throws IOException {
        if(spell == null) {
            throw new IOException();
        }
        tokenizedSpell.clear();

        StringBuilder tokens = new StringBuilder();
        Keyword keyword = null;
        for (String s : spell.toUpperCase().split(" ")) {
            if(WizardRegistry.getKeywordAlias().containsKey(s)) {
                if(keyword != null) {
                    tokenizedSpell.put(tokens.toString().strip(),keyword);
                }
                keyword = WizardRegistry.getKeywordAlias().get(s);

                tokens.delete(0,tokens.length());
            }
            tokens.append(s).append(" ");
        }
        tokenizedSpell.put(tokens.toString().strip(),keyword);
    }
    public String getElement(Keyword element) { // can optimize this better
        String holder = null;
        for (String s : tokenizedSpell.keySet()) {
            if (tokenizedSpell.get(s) == element) {
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
    public List<PackagedModifier> getPackagedModifiers() {
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
    public List<String> getCheckableStrings(Keyword keyword) {
        List<String> result = new ArrayList<>();
        for (String s : tokenizedSpell.keySet()) {
            if (tokenizedSpell.get(s) == keyword) {
                result.add(s);
            }
        }
        return result;
    }

    public Map<String, Keyword> getTokenizedSpell() {
        return tokenizedSpell;
    }
}
