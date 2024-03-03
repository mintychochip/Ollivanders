package mintychochip.ollivanders.handler;

import mintychochip.genesis.util.MathUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CooldownManager {

    private final Map<UUID, Map<String, Long>> cooldowns = new HashMap<>();

    public boolean hasCooldown(String mechanicName, double duration, UUID player) {
        Map<String, Long> stringLongMap = cooldowns.get(player);
        if (stringLongMap == null) {
            return false;
        }
        if (stringLongMap.containsKey(mechanicName)) {
            double l = System.currentTimeMillis() - stringLongMap.get(mechanicName);
            if (l * 0.001 >= duration) {
                stringLongMap.remove(mechanicName);
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

    public double cooldownRemaining(String mechanicName, double duration, UUID player) {
        double l = System.currentTimeMillis() - cooldowns.get(player).get(mechanicName);
        return MathUtil.roundToDecimals(duration - l * 0.001, 1);
    }

    public void addCooldown(String mechanicName, UUID player) {
        Map<String, Long> stringLongMap = cooldowns.remove(player);
        if (stringLongMap == null) {
            stringLongMap = new HashMap<>();
        }
        if (stringLongMap.containsKey(mechanicName)) {
            stringLongMap.replace(mechanicName, System.currentTimeMillis());
        } else {
            stringLongMap.put(mechanicName, System.currentTimeMillis());
        }
        cooldowns.put(player, stringLongMap);
    }

    public Map<String, Long> playerCooldowns(UUID player) {
        return cooldowns.get(player);
    }

    public Map<UUID, Map<String, Long>> getCooldowns() {
        return cooldowns;
    }
}
