package mintychochip.ollivanders.util;

import mintychochip.ollivanders.config.Registry;
import mintychochip.ollivanders.container.Spell;
import mintychochip.ollivanders.container.SpellMechanic;
import mintychochip.ollivanders.enums.Keyword;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class SpellTokenizer {

    private Map<String, Keyword> tokenizedSpell;

    private Spell spell;

    public SpellTokenizer() {

    }

    public SpellTokenizer setTokenizedSpell(String spell) throws IOException {
        if (spell == null) {
            throw new IOException();
        }
        this.spell = new Spell();
        tokenizedSpell = new HashMap<>();
        Keyword keyword = null;
        StringBuilder tokens = new StringBuilder();
        for (String s : spell.toUpperCase().split(" ")) {
            if (Registry.getKeywordAlias().containsKey(s)) {
                if (keyword != null) {
                    tokenizedSpell.put(tokens.toString().strip(), keyword);
                }
                keyword = Registry.getKeywordAlias().get(s);

                tokens.delete(0, tokens.length());
            }
            tokens.append(s).append(" ");
        }
        tokenizedSpell.put(tokens.toString().strip(), keyword);
        return this;
    }

    public SpellTokenizer setSpellMechanic(String mechanic) throws IOException {
        SpellMechanic spellMechanic = Registry.getMechanicAlias().get(mechanic);
        if (spellMechanic == null) {
            throw new IOException("Not a valid mechanic");
        }
        try {
            spell.setMechanic(spellMechanic.getClass().getDeclaredConstructor().newInstance());
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                 NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
        return this;
    }
    public List<String> element(Keyword keyword) {
        for (String s : tokenizedSpell.keySet()) {
            if (Enum.valueOf(Keyword.class, s.toUpperCase().strip()) == keyword) {
                ArrayList<String> strings = new ArrayList<>(Arrays.asList(s.split(" ")));
                if (strings.size() > 1) {
                    strings.remove(0);
                    return strings;
                }
                break;
            }
        }
        return null;
    }

    public Spell defaultBuild(String spell) {
    }

}
