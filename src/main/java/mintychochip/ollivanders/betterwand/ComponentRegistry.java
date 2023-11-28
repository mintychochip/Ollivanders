package mintychochip.ollivanders.betterwand;

import mintychochip.ollivanders.Ollivanders;
import mintychochip.ollivanders.betterwand.core.Core;
import org.bukkit.Material;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ComponentRegistry {

    private static final Map<Material, ComponentType> materialComponentType = new HashMap<>();
    private static final Map<Material, WandBoost> materialBoostMap = new HashMap<>();

    private static final Map<Core, List<String>> defaultLoreMap = new HashMap<>();

    private static final Map<Material,Core> coreTranslationMap = new HashMap<>();

    private static final Map<Material,String> materialTitleMap = new HashMap<>();

    public ComponentRegistry() {
        Ollivanders.getWandConfig().registerMaterials();
        Ollivanders.getWandConfig().registerLore();
    }
    public static Map<Material, ComponentType> getMaterialComponentType() {
        return materialComponentType;
    }

    public static Map<Material, WandBoost> getMaterialBoostMap() {
        return materialBoostMap;
    }

    public static Map<Core, List<String>> getDefaultLoreMap() {
        return defaultLoreMap;
    }

    public static Map<Material, String> getMaterialTitleMap() {
        return materialTitleMap;
    }

    public static Map<Material, Core> getCoreTranslationMap() {
        return coreTranslationMap;
    }
}
