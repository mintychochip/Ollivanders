package mintychochip.ollivanders.util;

import mintychochip.ollivanders.config.Registry;
import mintychochip.ollivanders.container.MechanicModifier;
import mintychochip.ollivanders.container.PackagedModifier;
import mintychochip.ollivanders.container.Spell;
import mintychochip.ollivanders.container.SpellMechanic;
import mintychochip.ollivanders.enums.Keyword;
import mintychochip.ollivanders.enums.Modifier;
import mintychochip.ollivanders.enums.Shape;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class SpellTokenizer {

    private Map<String, Keyword> tokenizedSpell;

    private Spell spell;

    private final StringBuilder stringBuilder = new StringBuilder();

    enum Word {
        KEYWORD,
        SHAPE,
        MODIFIER
    }

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

    public SpellTokenizer setModifiers(List<PackagedModifier> packagedModifiers) throws IOException { //can generalize this function
        MechanicModifier mechanicModifier = new MechanicModifier();
        for (PackagedModifier packagedModifier : packagedModifiers) {
            switch(packagedModifier.getModifer()) {
                case VELOCITY -> mechanicModifier.setVelocity(Float.parseFloat(packagedModifier.getValue()));
                case MAGNITUDE -> mechanicModifier.setMagnitude(Float.parseFloat(packagedModifier.getValue()));
            }
        }
        spell.getMechanic().setMechanicModifier(mechanicModifier);
        return this;
    }
    public SpellTokenizer setShape(String shape) {
        if(EnumUtil.isInEnum(Shape.class,shape)) {
            spell.getMechanic().setShape(Enum.valueOf(Shape.class,shape));
        }
        return this;
    }
    public String firstInstanceOfKeyword(String keyword) { // can optimize this better
        //Returns the first instance of an element, and appends all content other than the keyword to a string
        String firstInstanceOfElement = null;

        for (String s : tokenizedSpell.keySet()) {
            if (tokenizedSpell.get(s) == Enum.valueOf(Keyword.class,keyword.toUpperCase())) {
                firstInstanceOfElement = s;
                break;
            }
        }

        if(firstInstanceOfElement != null && Registry.getKeywordAlias() != null) {
            elementAddToStringBuilder(firstInstanceOfElement);
            return stringBuild();
        }
        return null;
    }
    public void elementAddToStringBuilder(String element) {
        for (String s : element.split(" ")) {
            if(!Registry.getKeywordAlias().containsKey(s)) {
                stringBuilder.append(s).append(" ");
            }
        }
    }
    public List<String> allInstancesofKeyword(String keyword) {
        List<String> allInstances;
        if(keyword != null) {
            allInstances = tokenizedSpell
                    .keySet()
                    .stream()
                    .filter(key -> tokenizedSpell.get(key) == Enum.valueOf(Keyword.class, key))
                    .collect(Collectors.toList());
        } else {
            return null;
        }
        allInstances.forEach(instance -> {
          elementAddToStringBuilder(instance);
          allInstances.set(allInstances.indexOf(instance),stringBuild());
        });
        return allInstances;
    }
    public List<PackagedModifier> getPackagedModifiers(List<String> elements) {
        List<PackagedModifier> packagedModifiers = null;
        if(elements != null) {
            packagedModifiers = new ArrayList<>();
            for (String element : elements) {
                String[] s = element.split(" ");
                if(s.length > 1) {
                    packagedModifiers.add(new PackagedModifier(Enum.valueOf(Modifier.class,s[0]),s[1]));
                }
            }
        }
        return packagedModifiers;



    }
    public Spell defaultBuild(String spell) {
        try {
            return this.setTokenizedSpell(spell)
                    .setSpellMechanic(firstInstanceOfKeyword("mechanic"))
                    .setShape(firstInstanceOfKeyword("shape"))
                    .setModifiers(getPackagedModifiers(allInstancesofKeyword("modifier")))
                    .build();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Spell build() {
        return spell;
    }

    public String stringBuild() {
        String strip = stringBuilder.toString().strip();
        stringBuilder.delete(0,stringBuilder.length());
        return strip;
    }
}
