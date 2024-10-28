package mintychochip.ollivanders.config;

import mintychochip.ollivanders.container.MechanicSettings;
import mintychochip.ollivanders.container.SpellMechanic;
import mintychochip.ollivanders.enums.Keyword;
import mintychochip.ollivanders.enums.Modifier;
import mintychochip.ollivanders.enums.Shape;

import java.util.HashMap;
import java.util.Map;

public class Registry { //eventually allows to do lang files
    private static Map<String, Modifier> modifierAlias = new HashMap<>();

    private static final Map<String, MechanicSettings> settingsMap = new HashMap<>();
    private static Map<String, Keyword> keywordAlias = new HashMap<>();
    private static Map<String, SpellMechanic> mechanicAlias = new HashMap<>();
    private static Map<String, Shape> shapeAlias = new HashMap<>();

    public static Map<String, Shape> getShapeAlias() {
        return shapeAlias;
    }

    public static void setShapeAlias(Map<String, Shape> shapeAlias) {
        Registry.shapeAlias = shapeAlias;
    }

    public static Map<String, Modifier> getModifierAlias() {
        return modifierAlias;
    }

    public static void setModifierAlias(Map<String, Modifier> modifierAlias) {
        Registry.modifierAlias = modifierAlias;
    }

    public static Map<String, MechanicSettings> getSettingsMap() {
        return settingsMap;
    }

    public static Map<String, Keyword> getKeywordAlias() {
        return keywordAlias;
    }

    public static void setKeywordAlias(Map<String, Keyword> keywordAlias) {
        Registry.keywordAlias = keywordAlias;
    }

    public static Map<String, SpellMechanic> getMechanicAlias() {
        return mechanicAlias;
    }

    public static void setMechanicAlias(Map<String, SpellMechanic> mechanicAlias) {
        Registry.mechanicAlias = mechanicAlias;
    }

}
