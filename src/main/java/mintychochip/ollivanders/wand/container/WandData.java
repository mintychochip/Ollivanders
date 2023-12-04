package mintychochip.ollivanders.wand.container;

import mintychochip.ollivanders.wand.enums.ComponentType;
import mintychochip.ollivanders.wand.enums.CoreType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WandData extends Data {

    private WandBoost wandBoost;
    private String wandName;
    private CoreType CoreType;
    private Map<WandBoost, ComponentType> wandBoostMap = new HashMap<>();
    private List<String> wandLore;

    public void setWandBoostMap(Map<WandBoost, ComponentType> wandBoostMap) {
        this.wandBoostMap = wandBoostMap;
    }

    public void setWandBoost(WandBoost wandBoost) {
        this.wandBoost = wandBoost;
    }
    public void setWandName(String wandName) {
        this.wandName = wandName;
    }
    public void setCoreType(CoreType CoreType) {
        this.CoreType = CoreType;
    }
    public void setWandLore(List<String> wandLore) {
        this.wandLore = wandLore;
    }

    public WandBoost getWandBoost() {
        return wandBoost;
    }

    public String getWandName() {
        return wandName;
    }

    public CoreType getCoreType() {
        return CoreType;
    }

    public Map<WandBoost, ComponentType> getWandBoostMap() {
        return wandBoostMap;
    }

    public List<String> getWandLore() {
        return wandLore;
    }
}
