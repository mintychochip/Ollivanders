package mintychochip.ollivanders.commands.abstraction;

import org.bukkit.entity.Player;

public interface SubCommand { //port to gen
     public boolean execute(String[] args, Player sender);
}
