package mintychochip.ollivanders.wand.container;

import mintychochip.ollivanders.wand.enums.CoreType;

import java.util.List;

public class WandData extends Data {

    private WandBoost wandBoost;
    private String wandName;
    private CoreType coreType;
    private List<String> wandLore;

    public WandBoost getWandBoost() {
        return wandBoost;
    }

    public void setWandBoost(WandBoost wandBoost) {
        this.wandBoost = wandBoost;
    }

    public String getWandName() {
        return wandName;
    }

    public void setWandName(String wandName) {
        this.wandName = wandName;
    }

    public CoreType getCoreType() {
        return coreType;
    }

    public void setCoreType(CoreType CoreType) {
        this.coreType = CoreType;
    }

    public List<String> getWandLore() {
        return wandLore;
    }

    public void setWandLore(List<String> wandLore) {
        this.wandLore = wandLore;
    }

    public String toString() {
        return String.format("%s %s %s %s", wandBoost.toString(), wandName, coreType.toString(), wandLore.toString());
    }
}
