package mintychochip.ollivanders.wand.container;

import mintychochip.genesis.container.ItemData;
import mintychochip.ollivanders.wand.enums.CoreType;

import java.util.List;

public class WandData extends ItemData {

    public static final long serialVersionUID = 1234567L;
    private WandBoost wandBoost;
    private CoreType coreType;
    private List<String> wandLore;

    public WandData setDisplayName(String displayName) {
        return (WandData) super.setDisplayName(displayName);
    }
    public WandBoost getWandBoost() {
        return wandBoost;
    }

    public void setWandBoost(WandBoost wandBoost) {
        this.wandBoost = wandBoost;
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
        return String.format("%s %s %s %s", wandBoost.toString(),displayName, coreType.toString(), wandLore.toString());
    }
}
