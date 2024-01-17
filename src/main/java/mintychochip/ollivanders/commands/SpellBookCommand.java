package mintychochip.ollivanders.commands;

import mintychochip.genesis.Genesis;
import mintychochip.genesis.container.AbstractItem;
import mintychochip.ollivanders.Ollivanders;
import mintychochip.ollivanders.spellbook.BookBuilder;
import mintychochip.ollivanders.spellbook.BookType;
import mintychochip.ollivanders.util.EnumUtil;
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

import java.util.List;

public class SpellBookCommand implements CommandExecutor {


    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (commandSender instanceof Player player) {
            if (EnumUtil.isInEnum(BookType.class, strings[0].toUpperCase())) {
                BookType bookType = Enum.valueOf(BookType.class, strings[0].toUpperCase());
                ItemStack itemStack = new BookBuilder(new AbstractItem(Ollivanders.getInstance(), Material.WRITABLE_BOOK, true), "CLASSIC", bookType).defaultBuild();
                ItemMeta itemMeta = itemStack.getItemMeta();
                itemMeta.getPersistentDataContainer().set(Genesis.getKey("player"), PersistentDataType.STRING, strings[1]);
                List<String> lore = itemMeta.getLore();
                lore.add(ChatColor.DARK_GRAY + "This item belongs to: " + ChatColor.GOLD + strings[1]);
                itemMeta.setLore(lore);
                itemStack.setItemMeta(itemMeta);
                player.getInventory().addItem(itemStack);

            }
        }
        return true;
    }
}
