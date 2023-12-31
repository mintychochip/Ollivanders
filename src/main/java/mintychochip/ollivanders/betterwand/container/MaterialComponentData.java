package mintychochip.ollivanders.betterwand.container;

import mintychochip.ollivanders.betterwand.ComponentType;
import mintychochip.ollivanders.betterwand.Rarity;
import mintychochip.ollivanders.betterwand.WandBoost;
import mintychochip.ollivanders.betterwand.core.Core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MaterialComponentData implements Serializable {

    private ComponentType type; //lens focus etc
    private WandBoost wandBoost; //modifier bonus to spell casting
    private List<String> lore = new ArrayList<>(); //lore enscribed on wand if it is a core
    private String loreName; //lore name, so we dont input material names in lore
    private String title; // prefix in the display name or suffix
    private Core core; //if the componentType is a core, then this is set to an enum value

    private Rarity rarity;

    public Rarity getRarity() {
        return rarity;
    }

    public MaterialComponentData setRarity(Rarity rarity) {
        this.rarity = rarity;
        return this;
    }

    public ComponentType getType() {
        return type;
    }

    public MaterialComponentData setType(ComponentType type) {
        this.type = type;
        return this;
    }

    public WandBoost getWandBoost() {
        return wandBoost;
    }

    public MaterialComponentData setWandBoost(WandBoost wandBoost) {
        this.wandBoost = wandBoost;
        return this;
    }

    public List<String> getLore() {
        return lore;
    }

    public MaterialComponentData setLore(List<String> lore) {
        this.lore = lore;
        return this;
    }

    public String getLoreName() {
        return loreName;
    }

    public MaterialComponentData setLoreName(String loreName) {
        this.loreName = loreName;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public MaterialComponentData setTitle(String title) {
        this.title = title;
        return this;
    }

    public Core getCore() {
        return core;
    }

    public MaterialComponentData setCore(Core core) {
        this.core = core;
        return this;
    }
    public String toString() {
        return String.format("%s %s %s %s %s",type.toString(),wandBoost.toString(),lore.toString(),core.toString(),rarity.toString());
    }
}
