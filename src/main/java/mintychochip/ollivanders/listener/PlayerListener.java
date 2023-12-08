package mintychochip.ollivanders.listener;

import mintychochip.ollivanders.Ollivanders;
import mintychochip.ollivanders.config.Registry;
import mintychochip.ollivanders.container.Context;
import mintychochip.ollivanders.container.MechanicModifier;
import mintychochip.ollivanders.container.Spell;
import mintychochip.ollivanders.enums.Keyword;
import mintychochip.ollivanders.enums.Shape;
import mintychochip.ollivanders.util.SpellCaster;
import mintychochip.ollivanders.util.SpellTokenizer;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.IOException;

public class PlayerListener implements Listener {

    @EventHandler
    public void onPlayerCast(final PlayerInteractEvent event) {
        if(event.getPlayer().getInventory().getItemInMainHand().getType() == Material.WRITABLE_BOOK) {
            Bukkit.broadcastMessage("hello");
            ItemMeta itemMeta = event.getPlayer().getInventory().getItemInMainHand().getItemMeta();
            if(itemMeta instanceof BookMeta bookMeta) {
                String page = bookMeta.getPage(1);
                Spell spell = new SpellTokenizer().defaultBuild(page);
                spell.getMechanic().setMechanicModifier(new MechanicModifier());
                spell.getMechanic().setContext(new Context(event.getPlayer()));
                double range = spell.getMechanic().getMechanicSettings().getRange();
                Bukkit.broadcastMessage(range+"");
                SpellCaster.cast(spell, Shape.PROJECTILE);
                String string = Registry.getShapeAlias().toString();
                Bukkit.broadcastMessage(string);
            }
        }
    }
}
