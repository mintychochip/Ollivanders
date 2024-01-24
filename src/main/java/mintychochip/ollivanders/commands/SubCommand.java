package mintychochip.ollivanders.commands;

import org.bukkit.entity.Player;

public abstract class SubCommand { //port to gen

    protected final String executor;

    protected final String description;

    public SubCommand(String executor, String description) {
        this.executor = executor;
        this.description = description;
    }
    public abstract boolean execute(String[] args, Player sender);
    public String getDescription() {
        return description;
    }

    public String getExecutor() {
        return executor;
    }
}
