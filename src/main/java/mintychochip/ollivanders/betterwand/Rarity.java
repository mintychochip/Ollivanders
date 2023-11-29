package mintychochip.ollivanders.betterwand;

public enum Rarity {
    COMMON('4'),
    UNCOMMON('6'),
    RARE('5'),
    EPIC('4'),
    LEGENDARY('3');

    public final char colorCode;

    Rarity(char colorCode) {
        this.colorCode = colorCode;
    }

    public char getColorCode() {
        return colorCode;
    }

}
