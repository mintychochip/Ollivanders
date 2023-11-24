package mintychochip.ollivanders.handler;

import mintychochip.ollivanders.container.WizardSpell;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ProjectileHandler {

    private static ProjectileHandler instance;


    private final Map<Integer, WizardSpell> hitMap = new HashMap<>(); //have to add projectile deletion in here

    private final Map<Integer, UUID> playerProjectileLaunch = new HashMap<>();

    public ProjectileHandler() {
        instance = this;
    }

    public Map<Integer, WizardSpell> getHitMap() {
        return hitMap;
    }

    public static ProjectileHandler getInstance() {
        return instance;
    }
}
