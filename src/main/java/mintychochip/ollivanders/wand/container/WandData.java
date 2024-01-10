package mintychochip.ollivanders.wand.container;

import mintychochip.genesis.config.GenesisConfigurationSection;
import mintychochip.genesis.container.ItemData;
import mintychochip.ollivanders.wand.enums.CoreType;

import java.util.List;

public class WandData extends ItemData {

    public static final long serialVersionUID = 1234567L;
    private WandBoost wandBoost;
    private CoreType coreType;
    public WandData(GenesisConfigurationSection main) {
        this.setCoreType(Enum.valueOf(CoreType.class,main.getPath().toUpperCase()));
    }

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
    public String toString() {
        return String.format("%s %s %s", wandBoost.toString(),displayName, coreType.toString());
    }
}
