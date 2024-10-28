package mintychochip.ollivanders.commands.spells;

import mintychochip.genesis.Genesis;
import mintychochip.genesis.color.GenesisTheme;
import mintychochip.genesis.commands.abstraction.GenericCommandObject;
import mintychochip.genesis.commands.abstraction.SubCommand;
import mintychochip.genesis.config.GenesisRegistry;
import mintychochip.genesis.config.abstraction.GenesisConfigurationSection;
import mintychochip.ollivanders.Ollivanders;
import mintychochip.ollivanders.util.OllivandersConfigMarker;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class SpellInfo extends GenericCommandObject implements SubCommand {
    public SpellInfo(String executor, String description) {
        super(executor, description);
    }

    @Override
    public boolean execute(String[] strings, Player player) {
        if (strings.length < depth) {
            return false;
        }
        GenesisConfigurationSection configurationSection = Ollivanders.getSpellConfig().getMechanics().getConfigurationSection(strings[depth - 1]);
        player.sendMessage(ChatColor.AQUA + strings[depth - 1] + ": " + ChatColor.GRAY + configurationSection.getString(OllivandersConfigMarker.description));
        return true;
    }
}
