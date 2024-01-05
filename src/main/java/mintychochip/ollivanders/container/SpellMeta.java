package mintychochip.ollivanders.container;

public class SpellMeta {

    private final long creationTime; //in systemmilli

    private int spellUID;

    private int UID;

    private long castingStartTime;

    public SpellMeta(long creationTime) {
        this.creationTime = creationTime;
    }

    public SpellMeta setCastingStartTime(long castingStartTime) {
        this.castingStartTime = castingStartTime;
        return this;
    }

    public long getCastingStartTime() {
        return castingStartTime;
    }

    public long getCreationTime() {
        return creationTime;
    }
}
