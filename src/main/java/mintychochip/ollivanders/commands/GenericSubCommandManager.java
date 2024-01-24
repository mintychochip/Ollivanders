package mintychochip.ollivanders.commands;

import org.bukkit.entity.Player;

public class GenericSubCommandManager extends SubCommand { //can probs merge with super

    private final int depth;
    public GenericSubCommandManager(String executor, String description, int depth) {
        super(executor, description);
        this.depth = depth;
    }

    @Override
    public boolean execute(String[] args, Player sender) {
        if(args > depth)
        return false;
    }

    public int getDepth() {
        return depth;
    }
}
