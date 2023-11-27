package mintychochip.ollivanders.betterwand.builder;

import mintychochip.ollivanders.util.Keys;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ItemBuilder { //to be moved to the library plugin for later
    //pull the unstackable key from the library plugin aswell dont forget

    protected final ItemStack itemStack;

    protected ItemMeta itemMeta;

    protected List<String> lore;

    public ItemBuilder(Material material) {
        itemStack = new ItemStack(material);
        itemMeta = itemStack.getItemMeta();
    }

    public ItemBuilder setDisplayName(String displayName) {
        itemMeta.setDisplayName(displayName);
        return this;
    }

    public ItemBuilder setUnstackable() {
        Random random = new Random();
        String string = Long.toString(random.nextLong());
        itemMeta.getPersistentDataContainer().set(Keys.getUnstackable(), PersistentDataType.STRING, string);
        return this;
    }

    public ItemBuilder setCustomModelData(int integer) {
        itemMeta.setCustomModelData(integer);
        return this;
    }

    public ItemBuilder addLore(List<String> description) {
        if (lore == null) {
            lore = description != null ? new ArrayList<>(description) : new ArrayList<>();
        } else {
            if (description != null) {
                lore.addAll(description);
            }
        }
        return this;
    }

    public ItemStack build() {
        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }
}
