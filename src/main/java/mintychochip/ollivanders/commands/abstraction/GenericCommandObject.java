package mintychochip.ollivanders.commands.abstraction;

import org.jetbrains.annotations.NotNull;

public abstract class GenericCommandObject {

    @NotNull
    protected final String executor;
    @NotNull protected final String description;

    @NotNull protected int depth;
    public GenericCommandObject(String executor, String description, int depth) {
        this.executor = executor;
        this.description = description;
        this.depth = depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public String getDescription() {
        return description;
    }

    public String getExecutor() {
        return executor;
    }
}
