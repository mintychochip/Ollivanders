package mintychochip.ollivanders.betterwand.container;

import mintychochip.ollivanders.betterwand.WandBoost;
import mintychochip.ollivanders.betterwand.core.Core;
import org.bukkit.Material;

import java.io.Serializable;

public class WandData implements Serializable {

    private WandBoost wandBoost;
    private Material material;
    private String wandName;
    private Core coreInstance;

    public WandBoost getWandBoost() {
        return wandBoost;
    }

    public void setWandBoost(WandBoost wandBoost) {
        this.wandBoost = wandBoost;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public String getWandName() {
        return wandName;
    }

    public void setWandName(String wandName) {
        this.wandName = wandName;
    }

    public Core getCoreInstance() {
        return coreInstance;
    }

    public void setCoreInstance(Core coreInstance) {
        this.coreInstance = coreInstance;
    }
}
