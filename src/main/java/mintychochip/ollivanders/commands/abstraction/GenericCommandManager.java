package mintychochip.ollivanders.commands.abstraction;

import java.util.List;

public class GenericCommandManager extends GenericCommandObject { //command managers at 0
    protected List<SubCommand> subCommands;

    public GenericCommandManager(String executor, String description, List<SubCommand> subCommands, int depth) {
        super(executor, description, depth);
        this.subCommands = subCommands;
        for (SubCommand subCommand : subCommands) {
            if (subCommand instanceof GenericCommandObject gco) {
                gco.setDepth(depth++);
            }
        }
    }

    public GenericCommandManager(String executor, String description, List<SubCommand> subCommands) {
        this(executor, description, subCommands, 1);
    }

    public List<SubCommand> getSubCommands() {
        return subCommands;
    }

    public boolean instantiateSubCommandManager(String executor, String description, List<SubCommand> subCommands) {
        subCommands.add(new GenericSubCommandManager(executor, description, subCommands, depth++));
        return false;
    }
}
