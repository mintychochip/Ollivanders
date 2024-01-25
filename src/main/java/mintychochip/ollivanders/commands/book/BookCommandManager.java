package mintychochip.ollivanders.commands.book;

import mintychochip.ollivanders.commands.abstraction.GenericMainCommandManager;
import mintychochip.ollivanders.commands.abstraction.SubCommand;

import java.util.List;

public class BookCommandManager extends GenericMainCommandManager {
    public BookCommandManager(String executor, String description, List<SubCommand> subCommands) {
        super(executor, description, subCommands);
    }
}
