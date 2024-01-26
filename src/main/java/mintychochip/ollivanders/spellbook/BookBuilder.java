package mintychochip.ollivanders.spellbook;

import mintychochip.genesis.Genesis;
import mintychochip.genesis.builder.ConfigurationItemBuilder;
import mintychochip.genesis.config.GenesisConfigurationSection;
import mintychochip.genesis.container.AbstractItem;
import mintychochip.genesis.util.Serializer;
import mintychochip.ollivanders.Ollivanders;
import mintychochip.ollivanders.util.OllivandersConfigMarker;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.io.IOException;
public class BookBuilder extends ConfigurationItemBuilder {

    private final BookData bookData;
    public BookBuilder(AbstractItem abstractItem, String genesisTheme, GenesisConfigurationSection main) {
        super(abstractItem,genesisTheme,main);
        try {
            bookData = new BookData(main.getInt(OllivandersConfigMarker.spell_slot_size));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public BookBuilder(String genesisTheme, GenesisConfigurationSection main) {
        this(new AbstractItem(Ollivanders.getInstance(), Material.WRITABLE_BOOK),genesisTheme,main);
    }
    public BookBuilder setDisplayName(String displayName, char color) {
        return (BookBuilder) super.setDisplayName(displayName,color);
    }
    public BookBuilder setCustomModelData(int model) {
        return (BookBuilder) super.setCustomModelData(model);
    }

    public BookData getSpellBook() {
        return bookData;
    }

    public BookBuilder addLore(String text) {
        return (BookBuilder) super.addLore(text);
    }

    public BookBuilder defaultBuilder() {
        return (BookBuilder) super.defaultBuilder();
    }
    @Override
    public ItemStack defaultBuild() {
        return this.defaultBuilder().build();
    }
    @Override
    public ItemStack build() {
        ItemStack itemStack = abstractItem.getItemStack();
        try {
            abstractItem.getItemMeta().getPersistentDataContainer().set(
                    Genesis.getKeys().getMap().get("book"),
                    PersistentDataType.BYTE_ARRAY, Serializer.serialize(bookData));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        itemStack.setItemMeta(abstractItem.getItemMeta());
        return itemStack;
    }
}
