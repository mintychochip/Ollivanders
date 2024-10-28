package mintychochip.ollivanders.container;

import mintychochip.ollivanders.enums.Modifier;

public class PackagedModifier {

    private final Modifier modifer;

    private final String value;

    public PackagedModifier(Modifier modifier, String value) {
        this.modifer = modifier;
        this.value = value;
    }

    public Modifier getModifer() {
        return modifer;
    }

    public String getValue() {
        return value;
    }
}
