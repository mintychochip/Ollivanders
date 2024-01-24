package mintychochip.ollivanders.commands;

import mintychochip.genesis.Genesis;
import mintychochip.genesis.container.AbstractItem;
import mintychochip.ollivanders.Ollivanders;
import mintychochip.ollivanders.commands.book.PageCommand;
import mintychochip.ollivanders.commands.book.SetBookDataSize;
import mintychochip.ollivanders.items.util.OllivandersSerializer;
import mintychochip.ollivanders.spellbook.BookBuilder;
import mintychochip.ollivanders.spellbook.BookData;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class BookCommand extends GenericCommandManager {

    public BookCommand(List<SubCommand> subCommands) {
        super(subCommands);
    }
}
