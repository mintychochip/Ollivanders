package mintychochip.ollivanders.spellbook;

import mintychochip.ollivanders.wand.enums.Rarity;

public enum BookType {

    LESSER_TOME(9, "Apprentice's Spellbook", Rarity.UNCOMMON),
    REGULAR_TOME(18, "Mage's Spellbook", Rarity.RARE),
    GREATER_TOME(27, "Grand Wizard's Tome", Rarity.LEGENDARY);

    private int size;

    private String displayName;

    private Rarity rarity;

    BookType(int size, String displayName, Rarity rarity) {
        this.size = size;
        this.displayName = displayName;
        this.rarity = rarity;
    }

    public int getSize() {
        return size;
    }

    public Rarity getRarity() {
        return rarity;
    }

    public String getDisplayName() {
        return displayName;
    }
}
