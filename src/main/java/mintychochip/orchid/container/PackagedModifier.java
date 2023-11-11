package mintychochip.orchid.container;

public class PackagedModifier {

    private Modifier type;
    private String value;

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
