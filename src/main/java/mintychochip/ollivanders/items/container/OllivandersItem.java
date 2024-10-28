package mintychochip.ollivanders.items.container;

import com.google.gson.Gson;
import mintychochip.genesis.Genesis;
import mintychochip.genesis.config.abstraction.GenesisConfigurationSection;
import mintychochip.genesis.container.items.AbstractItem;
import mintychochip.genesis.container.items.interfaces.Embeddable;
import mintychochip.genesis.util.Rarity;
import mintychochip.ollivanders.items.enums.ComponentType;
import mintychochip.ollivanders.items.util.ComponentUtil;
import mintychochip.ollivanders.util.OllivandersConfigMarker;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class OllivandersItem extends AbstractItem {
    protected OllivandersItem(ItemBuilder itemBuilder) {
        super(itemBuilder);
    }

    public static class WandBuilder extends EmbeddedDataBuilder {
        private final Map<ComponentType, ItemStack> mappedMaterials;

        public WandBuilder(JavaPlugin instance, Material material, GenesisConfigurationSection main, boolean boundOnCraft, String genesisTheme, Embeddable embeddable, List<ItemStack> materials) {
            super(instance, material, main, boundOnCraft, genesisTheme, embeddable);
            mappedMaterials = materials.stream().collect(Collectors.toMap(x -> ComponentUtil.componentDataFromItemStack(x).getComponentType(), m -> m));
        }

        public String generateWandName() {
            return String.format("%s %s of the %s",
                    dataFromType(ComponentType.CRYSTAL).getTitle(),
                    dataFromType(ComponentType.STICK).getTitle(),
                    dataFromType(ComponentType.CORE).getTitle());
        }

        public ComponentData dataFromType(ComponentType componentType) {
            return ComponentUtil.componentDataFromItemStack(mappedMaterials.get(componentType));
        }

        public AbstractItem defaultBuild() {
            Rarity rarity = main.enumFromSection(Rarity.class, OllivandersConfigMarker.rarity);
            this.setRarity(rarity)
                    .setDisplayName(rarity.getLegacyColor() + generateWandName())
                    .setCustomModelData(main.getInt(OllivandersConfigMarker.custom_model))
                    .setUnstackable(true)
                    .addLore(main.getStringList(OllivandersConfigMarker.lore));
            if (embeddableList != null) {
                for (Embeddable embeddable : embeddableList) {
                    itemMeta.getPersistentDataContainer().set(embeddable.getKey(), PersistentDataType.STRING,new Gson().toJson(embeddable));
                }
            }
            AbstractItem build = this.build();

            return build;
        }
    }
}
