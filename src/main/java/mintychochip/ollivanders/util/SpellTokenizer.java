package mintychochip.ollivanders.util;

import mintychochip.ollivanders.config.Registry;
import mintychochip.ollivanders.container.*;
import mintychochip.ollivanders.enums.Keyword;
import mintychochip.ollivanders.enums.Modifier;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SpellTokenizer { //can make this binary

    private final StringBuilder stringBuilder = new StringBuilder();
    private Map<String, Keyword> tokenizedSpell;
    private Spell spell;

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
                    tokenizedSpell.put(stringBuild().toUpperCase(), keyword);
                }
                keyword = Registry.getKeywordAlias().get(s);
            }
            stringBuilder.append(s).append(" ");
        }
        tokenizedSpell.put(stringBuild().toUpperCase(), keyword);
        return this;
    }

    public SpellTokenizer setSpellMechanic(String keyword) throws IOException {
        SpellMechanic spellMechanic = Registry.getMechanicAlias().get(keyword);
        if (spellMechanic == null) {
            return null;
        }
        try {
            spell.setMechanic(spellMechanic.getClass().getConstructor().newInstance()
                    .setMechanicName(spellMechanic.getClass().getSimpleName()));
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException |
                 NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
        return this;
    }

    public SpellTokenizer setSpellMechanicSettings(String mechanicName) {
        String key = mechanicName + "-" + spell.getMechanic().getShape().toString();
        MechanicSettings settings = Registry.getSettingsMap().get(key.toUpperCase());
        if (settings == null) {
            settings = Registry.getSettingsMap().get(mechanicName.toUpperCase() + "-MAIN");
        }
        spell.setMechanic(spell.getMechanic().setMechanicSettings(settings));
        return this;
    }

    public SpellTokenizer setModifiers(List<PackagedModifier> packagedModifiers) throws IOException { //can generalize this function
        MechanicModifier mechanicModifier = new MechanicModifier();
        for (PackagedModifier packagedModifier : packagedModifiers) {
            switch (packagedModifier.getModifer()) {
                case VELOCITY -> mechanicModifier.setVelocity(Float.parseFloat(packagedModifier.getValue()));
                case MAGNITUDE -> mechanicModifier.setMagnitude(Float.parseFloat(packagedModifier.getValue()));
            }
        }
        spell.getMechanic().setMechanicModifier(mechanicModifier);
        return this;
    }

    public SpellTokenizer setShape(String shape) {
        if (Registry.getShapeAlias().containsKey(shape)) {
            spell.getMechanic().setShape(Registry.getShapeAlias().get(shape));
        }
        return this;
    }

    public String firstInstanceOfKeyword(Keyword keyword) { // can optimize this better
        //Returns the first instance of an element, and appends all content other than the keyword to a string
        String firstInstanceOfElement = null;

        for (String s : tokenizedSpell.keySet()) {
            if (tokenizedSpell.get(s) == keyword) {
                firstInstanceOfElement = s;
                break;
            }
        }

        if (firstInstanceOfElement != null && Registry.getKeywordAlias() != null) {
            elementAddToStringBuilder(firstInstanceOfElement);
            return stringBuild();
        }
        return null;
    }

    public void elementAddToStringBuilder(String element) {
        for (String s : element.split(" ")) {
            if (!Registry.getKeywordAlias().containsKey(s)) {
                stringBuilder.append(s).append(" ");
            }
        }
    }

    public List<String> allInstancesofKeyword(Keyword keyword) {
        List<String> allInstances = new ArrayList<>();
        if (keyword != null) {
            for (String s : tokenizedSpell.keySet()) {
                if (tokenizedSpell.get(s) == keyword) {
                    allInstances.add(s);
                }
            }
        }
        int index = 0;
        for (String allInstance : allInstances) {
            for (String s : allInstance.split(" ")) {
                if (!Registry.getKeywordAlias().containsKey(s)) {
                    stringBuilder.append(s).append(" ");
                }
            }
            allInstances.set(index++, stringBuild());
        }
        return allInstances;
    }

    public List<PackagedModifier> getPackagedModifiers(List<String> elements) {
        List<PackagedModifier> packagedModifiers = null;
        if (elements != null) {
            packagedModifiers = new ArrayList<>();
            for (String element : elements) {
                String[] s = element.split(" ");
                if (s.length > 1) {
                    packagedModifiers.add(new PackagedModifier(Enum.valueOf(Modifier.class, s[0]), s[1]));
                }
            }
        }
        return packagedModifiers;


    }

    public Spell defaultBuild(String spell) {

        try {
            SpellTokenizer spellTokenizer = this.setTokenizedSpell(spell)
                    .setSpellMechanic(firstInstanceOfKeyword(Keyword.MECHANIC));
            if (spellTokenizer == null) {
                return null;
            } else {
                return spellTokenizer.setShape(firstInstanceOfKeyword(Keyword.SHAPE))
                        .setSpellMechanicSettings(this.spell.getMechanic().getName())
                        .setModifiers(getPackagedModifiers(allInstancesofKeyword(Keyword.MODIFIER)))
                        .build();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Spell build() {
        return spell;
    }

    public String stringBuild() {
        String strip = stringBuilder.toString().strip();
        stringBuilder.delete(0, stringBuilder.length());
        return strip;
    }
}
