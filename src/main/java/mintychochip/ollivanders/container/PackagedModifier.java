package mintychochip.ollivanders.container;

public class PackagedModifier {

    private final Modifier type;
    private final String value;

    public PackagedModifier(Modifier type) {
        this(type,null);
    }
    public PackagedModifier(Modifier type, String value) {
        this.type = type;
        this.value = value;
    }

    public Modifier getType() {
        return type;
    }
    public String getValue() {
        return value;
    }

}
