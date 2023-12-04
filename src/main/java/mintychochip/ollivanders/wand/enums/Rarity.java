package mintychochip.ollivanders.wand.enums;

public enum Rarity {
    COMMON('7'),
    UNCOMMON('a'),
    RARE('9'),
    EPIC('5'),
    LEGENDARY('6'),
    DEFAULT('f');

    public final char colorCode;
    Rarity(char colorCode) {
        this.colorCode = colorCode;
    }
    public char getColorCode() {
        return colorCode;
    }

}
