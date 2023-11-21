package mintychochip.orchid.util;

import mintychochip.orchid.container.Keyword;
import mintychochip.orchid.container.Modifier;
import mintychochip.orchid.container.PackagedModifier;
import mintychochip.orchid.registry.OrchidRegistry;

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
            if(OrchidRegistry.getKeywordAlias().containsKey(s)) {
                if(keyword != null) {
                    tokenizedSpell.put(tokens.toString().strip(),keyword);
                }
                keyword = OrchidRegistry.getKeywordAlias().get(s);

                tokens.delete(0,tokens.length() - 1);
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
                if (!OrchidRegistry.getKeywordAlias().containsKey(s)) {
                    name.append(s).append(" ");
                }
            }
        }
        return name.toString().strip();
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
    public List<String> getCheckableStrings(Keyword keyword) {
        List<String> result = new ArrayList<>();
        for (String s : tokenizedSpell.keySet()) {
            if (tokenizedSpell.get(s) == keyword) {
                result.add(s);
            }
        }
        return result;
    }
}
