package mintychochip.ollivanders.util;

import mintychochip.ollivanders.config.Registry;
import mintychochip.ollivanders.container.MechanicModifier;
import mintychochip.ollivanders.container.Spell;
import mintychochip.ollivanders.container.SpellMechanic;
import mintychochip.ollivanders.enums.Keyword;
import mintychochip.ollivanders.enums.Modifier;
import mintychochip.ollivanders.enums.Shape;
import org.bukkit.Bukkit;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class SpellTokenizer {

    private Map<String, Keyword> tokenizedSpell;

    private Spell spell;

    private final StringBuilder stringBuilder = new StringBuilder();
    public SpellTokenizer setTokenizedSpell(String spell) throws IOException {
        if (spell == null) {
            throw new IOException();
        }
        this.spell = new Spell();
        tokenizedSpell = new HashMap<>();
        Keyword keyword = null;

        for (String s : spell.toUpperCase().split(" ")) {
            if (Registry.getKeywordAlias().containsKey(s)) {
                if (keyword != null) {
                    tokenizedSpell.put(stringBuilder.toString().strip(), keyword);
                }
                keyword = Registry.getKeywordAlias().get(s);

                stringBuilder.delete(0, stringBuilder.length());
            }
            stringBuilder.append(s).append(" ");
        }
        tokenizedSpell.put(stringBuilder.toString().strip(), keyword);
        stringBuilder.delete(0, stringBuilder.length());
        return this;
    }

    public SpellTokenizer setSpellMechanic(String mechanic) throws IOException {
        SpellMechanic spellMechanic = Registry.getMechanicAlias().get(mechanic);
        if (spellMechanic == null) {
            throw new IOException("Not a valid mechanic");
        }
        try {
            spell.setMechanic(spellMechanic.clone());
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
        return this;
    }

    public SpellTokenizer setModifiers(List<String> modifiers) throws IOException { //can generalize this function
        Modifier current = null;
        MechanicModifier mechanicModifier = new MechanicModifier();
        for (String modifier : modifiers) {
            if (EnumUtil.isInEnum(Modifier.class, modifier.toUpperCase())) {
                if (current != null) {
                    String value = stringBuilder.toString().strip();
                    switch (current) {
                        case VELOCITY -> mechanicModifier.setVelocity(Float.parseFloat(value));
                        case MAGNITUDE -> mechanicModifier.setMagnitude(Float.parseFloat(value));
                    }
                }
                current = Enum.valueOf(Modifier.class, modifier.toUpperCase());
                stringBuilder.delete(0, stringBuilder.length());
            } else {
                stringBuilder.append(modifier).append(" ");
            }
        }
        stringBuilder.delete(0, stringBuilder.length());
        spell.getMechanic().setMechanicModifier(mechanicModifier);
        return this;
    }
    public SpellTokenizer setShape(Shape shape) {
        spell.getMechanic().setShape(shape);
        return this;
    }
    public<E extends Enum<E>> String element(Class<E> enumClass,String element) { // can optimize this better
        String holder = null;
        for (String s : tokenizedSpell.keySet()) {
            if (tokenizedSpell.get(s) == Enum.valueOf(enumClass,element.toUpperCase())) {
                holder = s;
                break;
            }
        }
        StringBuilder name = new StringBuilder();
        if (holder != null) {
            for (String s : holder.split(" ")) {
                if (!Registry.getKeywordAlias().containsKey(s)) {
                    name.append(s).append(" ");
                }
            }
        }
        return name.toString().strip();
    }
    public Spell defaultBuild(String spell) {
        try {
            return this.setTokenizedSpell(spell).setSpellMechanic(element(Keyword.class,"mechanic")).setShape().build();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Spell build() {
        return spell;
    }

}
