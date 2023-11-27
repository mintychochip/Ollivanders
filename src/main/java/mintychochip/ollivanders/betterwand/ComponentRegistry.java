package mintychochip.ollivanders.betterwand;

import mintychochip.ollivanders.Ollivanders;
import org.bukkit.Material;

import java.util.HashMap;
import java.util.Map;

public class ComponentRegistry {

    private static final Map<Material, ComponentType> materialComponentType = new HashMap<>();
    private static final Map<Material, WandBoost> materialBoostMap = new HashMap<>();

    public ComponentRegistry() {
        Ollivanders.getWandConfig().registerMaterials();
    }
    public static Map<Material, ComponentType> getMaterialComponentType() {
        return materialComponentType;
    }

    public static Map<Material, WandBoost> getMaterialBoostMap() {
        return materialBoostMap;
    }
}
