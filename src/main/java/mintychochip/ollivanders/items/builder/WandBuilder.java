package mintychochip.ollivanders.items.builder;

import mintychochip.genesis.Genesis;
import mintychochip.genesis.builder.ItemBuilder;
import mintychochip.genesis.config.GenesisConfigurationSection;
import mintychochip.genesis.container.AbstractItem;
import mintychochip.genesis.util.Serializer;
import mintychochip.ollivanders.Ollivanders;
import mintychochip.ollivanders.items.container.ComponentData;
import mintychochip.ollivanders.items.container.WandBoost;
import mintychochip.ollivanders.items.container.WandData;
import mintychochip.ollivanders.items.enums.ComponentType;
import mintychochip.ollivanders.items.util.ComponentUtil;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class WandBuilder extends ItemBuilder {
    private final WandData wandData;
    private final GenesisConfigurationSection main;
    private Map<ComponentType, ItemStack> mappedMaterials;

    public WandBuilder(AbstractItem abstractItem, String genesisTheme, GenesisConfigurationSection main, List<ItemStack> materials) throws IOException {
        super(abstractItem, genesisTheme);
        if (materials == null) {
            throw new IOException("Wand builder materials cannot be null!");
        }
        this.wandData = new WandData(main, materials);
        this.main = main;
        mappedMaterials = materials.stream().collect(Collectors.toMap(x -> ComponentUtil.componentDataFromItemStack(x).getComponentType(), material -> material));

    }
    public WandBuilder(String genesisTheme, GenesisConfigurationSection main, List<ItemStack> materials) throws IOException {
        this(new AbstractItem(Ollivanders.getInstance(), Material.BLAZE_ROD),genesisTheme,main,materials);
    }
    public ComponentData dataFromType(ComponentType componentType) {
        return ComponentUtil.componentDataFromItemStack(mappedMaterials.get(componentType));
    }

    public String generateWandName() {
        return String.format("%s %s of the %s",
                dataFromType(ComponentType.CRYSTAL).getTitle(),
                dataFromType(ComponentType.STICK).getTitle(),
                dataFromType(ComponentType.CORE).getTitle());
    }

    public WandBuilder setCustomModelData(int model) {
        return (WandBuilder) super.setCustomModelData(model);
    }

    public WandBuilder setDisplayName(String displayName, ChatColor color) {
        wandData.setDisplayName(displayName);
        return (WandBuilder) super.setDisplayName(displayName, color);
    }

    public WandBuilder addWandLore(List<String> wandLore) {
        return (WandBuilder) super.addLore(wandLore);
    }

    public WandBuilder addBulletedLore(String term, String text) {
        return (WandBuilder) super.addBulletedLore(term, text);
    }

    public WandBuilder addStatLore() {
        WandBoost wandBoost = wandData.getWandBoost();
        addBulletedLore("Magnitude:", wandBoost.getMagnitude() + "");
        addBulletedLore("Duration:", wandBoost.getDuration() + "");
        addBulletedLore("Range:", wandBoost.getRange() + "");
        addBulletedLore("Efficiency:", wandBoost.getEfficiency() + "");
        addBulletedLore("Haste:", wandBoost.getHaste() + "");
        return this;
    }

    public WandBuilder setUnstackable(boolean unstackable) {
        return (WandBuilder) super.setUnstackable(unstackable);
    }

    public ItemStack defaultBuild() {
        return this.addWandLore(main.getStringList("lore"))
                .addStatLore()
                .setCustomModelData(main.getInt("model"))
                .setDisplayName(generateWandName(), ChatColor.getByChar(dataFromType(ComponentType.CORE).getRarity().colorCode))
                .setUnstackable(true).build();
    }

    public WandData getWandData() {
        return wandData;
    }

    @Override
    public ItemStack build() {
        ItemStack itemStack = abstractItem.getItemStack();
        try {
            abstractItem.getItemMeta().getPersistentDataContainer().set(
                    Genesis.getKeys().getMap().get("wand"),
                    PersistentDataType.BYTE_ARRAY, Serializer.serialize(wandData));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        itemStack.setItemMeta(abstractItem.getItemMeta());
        return itemStack;
    }
}
