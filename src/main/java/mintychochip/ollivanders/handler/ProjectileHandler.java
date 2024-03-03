package mintychochip.ollivanders.handler;

import mintychochip.ollivanders.container.SpellMechanic;

import java.util.HashMap;
import java.util.Map;

public class ProjectileHandler {
    private final Map<Integer, SpellMechanic> projectileMap = new HashMap<>();

    public Map<Integer, SpellMechanic> getProjectileMap() {
        return projectileMap;
    }
}
