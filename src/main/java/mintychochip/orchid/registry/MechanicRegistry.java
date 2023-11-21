package mintychochip.orchid.registry;

import mintychochip.orchid.Orchid;
import mintychochip.orchid.config.MechanicConfig;
import mintychochip.orchid.container.OrchidMechanic;
import mintychochip.orchid.container.OrchidMechanicSettings;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MechanicRegistry {

    private final MechanicConfig mechanicConfig;
    private static Map<String, OrchidMechanic> mechanics;
    private static Map<OrchidMechanic,OrchidMechanicSettings> mechanicSettings;

    public MechanicRegistry() {
        mechanicConfig = Orchid.getMechanicConfig();
        registerMechanics();

    }
    public void registerMechanics() {
        for (String key : mechanicConfig.getKeys()) {
            OrchidMechanic mechanic = mechanicConfig.getMechanic(key);
            if(mechanic == null) {
                return;
            }
            mechanic.setMechanicSettings(mechanicConfig.getMechanicSettings(key));
            List<String> keywords = mechanicConfig.getMechanicSettings(key).getKeywords();
            if(mechanics == null) {
                mechanics = new HashMap<>();
                mechanicSettings = new HashMap<>();
            }
            mechanicSettings.put(mechanic,mechanicConfig.getMechanicSettings(key));
            registerAlias(keywords,mechanic);
        }
    }
    public void registerAlias(List<String> alias, OrchidMechanic mechanic) {
        for (String s : alias) {
            mechanics.put(s.toUpperCase(),mechanic);
        }
    }

    public static OrchidMechanic getMechanic(String mechanic) {
        return mechanics.get(mechanic);
    }

    public static Map<String, OrchidMechanic> getMechanics() {
        return mechanics;
    }

    public static Map<OrchidMechanic, OrchidMechanicSettings> getMechanicSettings() {
        return mechanicSettings;
    }
}
