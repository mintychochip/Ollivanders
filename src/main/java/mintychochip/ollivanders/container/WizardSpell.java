package mintychochip.ollivanders.container;

public class WizardSpell {

    private WizardMechanic mechanic;
    private WizardSpellSettings WizardSpellSettings;

    public WizardMechanic getMechanic() {
        return mechanic;
    }

    public void setMechanic(WizardMechanic mechanic) {
        this.mechanic = mechanic;
    }

    public WizardSpellSettings getWizardSpellSettings() {
        return WizardSpellSettings;
    }

    public void setWizardSpellSettings(WizardSpellSettings WizardSpellSettings) {
        this.WizardSpellSettings = WizardSpellSettings;
    }

}
