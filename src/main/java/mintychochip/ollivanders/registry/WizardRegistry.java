package mintychochip.ollivanders.registry;

import mintychochip.ollivanders.Ollivanders;
import mintychochip.ollivanders.config.KeywordConfig;
import mintychochip.ollivanders.container.Keyword;
import mintychochip.ollivanders.container.Modifier;
import mintychochip.ollivanders.spellcaster.WizardCaster;
import org.bukkit.entity.EntityType;

import java.util.HashMap;
import java.util.Map;

public class WizardRegistry {

    private static final Map<String, EntityType> projectileAlias = new HashMap<>(); //make this mutable, add way to register ints or floats
    private static Map<String, Keyword> keywordAlias;
    private static Map<String, Modifier> modifierAlias;
    private static final Map<String, String> numericalAlias = new HashMap<>();
    private static Map<String, WizardCaster.Shape> shapeAlias;
    public WizardRegistry() {

        KeywordConfig keywordConfig = Ollivanders.getKeywordConfig();

        keywordAlias = keywordConfig.keywords();
        modifierAlias = keywordConfig.modifiers();
        shapeAlias = keywordConfig.shapes();
        manual();
    }

    public void manual() {
        numericalAlias.put("FIVE","5.0f");
        projectileAlias.put("FIREBALL",EntityType.FIREBALL);
        numericalAlias.put("SEVEN","7.0f");
        numericalAlias.put("TWENTY-FIVE","25.0f");
    }

    public static Map<String, Modifier> getModifierAlias() {
        return modifierAlias;
    }

    public static Map<String, Keyword> getKeywordAlias() {
        return keywordAlias;
    }

    public static Map<String, String> getNumericalAlias() {
        return numericalAlias;
    }

    public static Map<String, WizardCaster.Shape> getShapeAlias() {
        return shapeAlias;
    }

    public static Map<String, EntityType> getProjectileAlias() {
        return projectileAlias;
    }
}
