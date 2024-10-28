package mintychochip.ollivanders.items.container;



import mintychochip.genesis.Genesis;
import mintychochip.genesis.container.ItemData;
import mintychochip.genesis.container.items.interfaces.Appraisable;
import mintychochip.genesis.container.items.interfaces.Embeddable;
import mintychochip.genesis.util.Rarity;
import mintychochip.genesis.util.Serializer;
import mintychochip.ollivanders.items.enums.ComponentType;
import mintychochip.ollivanders.items.enums.CoreType;
import mintychochip.ollivanders.util.OllivandersConfigMarker;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.Serial;

public class ComponentData extends ItemData implements Embeddable, Appraisable { //holds all wand related objects
    private static final NamespacedKey key = Genesis.getKey("component");
    private final String title;
    private final WandBoost wandBoost;
    private final ComponentType componentType;
    private CoreType coreType;

    private Rarity rarity;
    public ComponentData(ComponentConfigurationSection main) {
        this.componentType = main.enumFromSection(ComponentType.class, OllivandersConfigMarker.component_type);
        this.wandBoost = main.getDefaultWandBoost("modifiers");
        this.title = main.getString("title");
        if (componentType == ComponentType.CORE) {
            this.coreType = main.enumFromSection(CoreType.class, OllivandersConfigMarker.core_type.toUpperCase());
        }
    }

    public String getTitle() {
        return title;
    }

    public WandBoost getWandBoost() {
        return wandBoost;
    }

    public ComponentType getComponentType() {
        return componentType;
    }

    public CoreType getCoreType() {
        return coreType;
    }

    public String toString() {
        return String.format("%s %s %s %s", title, wandBoost.toString(), componentType.toString(),rarity);
    }
    @Override
    public NamespacedKey getKey() {
        return key;
    }
    @Override
    public void setRarity(Rarity rarity) {
        this.rarity = rarity;
    }
}
