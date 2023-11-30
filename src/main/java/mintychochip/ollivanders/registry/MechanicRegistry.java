package mintychochip.ollivanders.registry;

import mintychochip.ollivanders.Ollivanders;
import mintychochip.ollivanders.config.MechanicConfig;
import mintychochip.ollivanders.container.WizardMechanic;
import mintychochip.ollivanders.container.WizardMechanicSettings;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MechanicRegistry {

    private final MechanicConfig mechanicConfig;
    private static Map<String, WizardMechanic> mechanics;
    private static Map<WizardMechanic,WizardMechanicSettings> mechanicSettings; //can serialize this

    public MechanicRegistry() {
        mechanicConfig = Ollivanders.getMechanicConfig();
        registerMechanics();

    }
    public void registerMechanics() {
        for (String key : mechanicConfig.getKeys()) {
            WizardMechanic mechanic = mechanicConfig.getMechanic(key);
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
    public void registerAlias(List<String> alias, WizardMechanic mechanic) {
        for (String s : alias) {
            mechanics.put(s.toUpperCase(),mechanic);
        }
    }

    public static WizardMechanic getMechanic(String mechanic) {
        return mechanics.get(mechanic);
    }

    public static Map<String, WizardMechanic> getMechanics() {
        return mechanics;
    }

    public static Map<WizardMechanic, WizardMechanicSettings> getMechanicSettings() {
        return mechanicSettings;
    }
}
