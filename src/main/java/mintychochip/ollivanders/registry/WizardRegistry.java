package mintychochip.ollivanders.registry;

import mintychochip.ollivanders.Ollivanders;
import mintychochip.ollivanders.config.MechanicsConfig;
import mintychochip.ollivanders.container.Keyword;
import mintychochip.ollivanders.container.Modifier;
import mintychochip.ollivanders.shape.Shape;
import org.bukkit.entity.EntityType;

import java.util.HashMap;
import java.util.Map;

public class WizardRegistry {

    private static final Map<String, EntityType> projectileAlias = new HashMap<>(); //make this mutable, add way to register ints or floats
    private static final Map<String, Keyword> keywordAlias = Ollivanders.getMechanicConfig().getMap("keyword", Keyword.class);
    private static Map<String, Modifier> modifierAlias;
    private static final Map<String, String> numericalAlias = new HashMap<>();
    private static Map<String, Shape> shapeAlias;

    @SuppressWarnings("unchecked")
    public WizardRegistry() {

        MechanicsConfig mechanicsConfig = Ollivanders.getMechanicConfig();
        modifierAlias = mechanicsConfig.getMap("modifier", Modifier.class);
        shapeAlias = mechanicsConfig.getMap("shape", Shape.class);
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

    public static Map<String, Shape> getShapeAlias() {
        return shapeAlias;
    }

    public static Map<String, EntityType> getProjectileAlias() {
        return projectileAlias;
    }
}
