package mintychochip.ollivanders.wand.builder;

import mintychochip.genesis.builder.ItemBuilder;
import mintychochip.genesis.container.AbstractItem;
import mintychochip.ollivanders.wand.container.WandData;
import mintychochip.ollivanders.wand.enums.ComponentType;
import mintychochip.ollivanders.wand.container.WandBoost;
import mintychochip.ollivanders.wand.enums.CoreType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WandBuilder extends ItemBuilder {

    private WandBoost wandBoost;
    private String wandName;
    private CoreType CoreType;
    private Map<WandBoost, ComponentType> wandBoostMap = new HashMap<>();
    private List<String> wandLore;
    private WandData wandData;
    public WandBuilder(AbstractItem abstractItem) {
        super(abstractItem);
    }

    public WandBoost getWandBoost() {
        return wandBoost;
    }

    public WandBuilder setWandBoost(WandBoost wandBoost) {
        this.wandBoost = wandBoost;
        return this;
    }

    public String getWandName() {
        return wandName;
    }

    public WandBuilder setWandName(String wandName) {
        this.wandName = wandName;
        return this;
    }

    public CoreType getCoreType() {
        return CoreType;
    }

    public WandBuilder setCoreType(CoreType CoreType) {
        this.CoreType = CoreType;
        return this;
    }

    public Map<WandBoost, ComponentType> getWandBoostMap() {
        return wandBoostMap;
    }

    public WandBuilder setWandBoostMap(Map<WandBoost, ComponentType> wandBoostMap) {
        this.wandBoostMap = wandBoostMap;
        return this;
    }

    public List<String> getWandLore() {
        return wandLore;
    }

    public WandBuilder setWandLore(List<String> wandLore) {
        this.wandLore = wandLore;
        return this;
    }

    public WandData getWandData() {
        return wandData;
    }

    public WandBuilder setWandData(WandData wandData) {
        this.wandData = wandData;
        return this;
    }
}
