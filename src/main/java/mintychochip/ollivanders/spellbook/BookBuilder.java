package mintychochip.ollivanders.spellbook;

import mintychochip.genesis.Genesis;
import mintychochip.genesis.builder.ItemBuilder;
import mintychochip.genesis.container.AbstractItem;
import mintychochip.genesis.util.Serializer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.io.IOException;

public class BookBuilder extends ItemBuilder {

    private final SpellBook spellBook;

    public BookBuilder(AbstractItem abstractItem, String genesisTheme, BookType bookType) {
        super(abstractItem,genesisTheme);
        try {
            spellBook = new SpellBook(bookType);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public BookBuilder addDisplayName() {
        BookType bookType = spellBook.getBookType();

        return (BookBuilder) super.setDisplayName(bookType.getDisplayName(), bookType.getRarity().getColorCode());
    }
    public SpellBook getSpellBook() {
        return spellBook;
    }
    public BookBuilder addLore(String text) {
        return (BookBuilder) super.addLore(text);
    }

    public ItemStack defaultBuild() {
        return this.addDisplayName().addLore("A dusty wizard's book, you wonder what can be in it...").build();
    }
    @Override
    public ItemStack build() {
        ItemStack itemStack = abstractItem.getItemStack();
        try {
            abstractItem.getItemMeta().getPersistentDataContainer().set(
                    Genesis.getKeys().getMap().get("book"),
                    PersistentDataType.BYTE_ARRAY, Serializer.serialize(spellBook));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        itemStack.setItemMeta(abstractItem.getItemMeta());
        return itemStack;
    }
}
