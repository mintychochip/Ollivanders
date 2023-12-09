package mintychochip.ollivanders.util;

import mintychochip.ollivanders.Ollivanders;
import mintychochip.ollivanders.api.AoeCastEvent;
import mintychochip.ollivanders.container.Context;
import mintychochip.ollivanders.container.Spell;
import mintychochip.ollivanders.container.SpellMechanic;
import mintychochip.ollivanders.spells.shape.SpellArea;
import mintychochip.ollivanders.spells.shape.SpellProjectile;
import mintychochip.ollivanders.wand.container.WandData;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class SpellCaster {
    public static void cast(Spell spell, WandData wandData, Context context) {
        if(spell == null) {
            return;
        }
        SpellMechanic mechanic = spell.getMechanic();
        mechanic.setContext(context);
        mechanic.setWandData(wandData);
        Player player = spell.getMechanic().getContext().getPlayer();
        switch(spell.getMechanic().getShape()) {
            case PROJECTILE -> {
                if(spell.getMechanic() instanceof SpellProjectile spellProjectile) {
                    int i = spellProjectile.launchProjectile(mechanic.getMechanicSettings().getEntityType(), player, spell.getMechanic().getMechanicModifier());
                    if(i > 0) {
                        Ollivanders.getProjectileHandler().getProjectileMap().put(i, spell.getMechanic());
                    }
                }
            }
            case AREA -> {
                if(spell.getMechanic() instanceof SpellArea spellArea) {
                    boolean b = spellArea.castArea();
                    if(b) {
                        Bukkit.getPluginManager().callEvent(new AoeCastEvent(spell,context,wandData));
                    }
                }
            }
            case SELF -> {

            }
            case SIGHT -> {
                
            }
        }
    }
    public static void castEffect(SpellMechanic mechanic, Context context) {
        mechanic.setContext(context);
        switch (mechanic.getShape()) {
            case PROJECTILE -> {
                if(mechanic instanceof SpellProjectile spellProjectile) {
                    spellProjectile.effectOnHit();
                }
            }
        }
    }
    public static void persistentCast(Spell spell, Context context) {
        switch(spell.getMechanic().getShape()) {
            case AREA -> {
                if(spell.getMechanic() instanceof SpellArea spellArea) {
                    boolean b = spellArea.castArea();
                }
            }
        }
    }
}
