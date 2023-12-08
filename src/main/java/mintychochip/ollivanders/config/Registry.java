package mintychochip.ollivanders.config;

import mintychochip.ollivanders.container.SpellMechanic;
import mintychochip.ollivanders.enums.Keyword;

import java.util.HashMap;
import java.util.Map;

public class Registry {

    private static Map<String, Keyword> keywordAlias = new HashMap<>();

    private static Map<String, SpellMechanic> mechanicAlias = new HashMap<>();

    public static Map<String, Keyword> getKeywordAlias() {
        return keywordAlias;
    }

    public static Map<String, SpellMechanic> getMechanicAlias() {
        return mechanicAlias;
    }
}
