package mintychochip.ollivanders.util;

import mintychochip.ollivanders.Ollivanders;
import mintychochip.ollivanders.api.AoeCastEvent;
import mintychochip.ollivanders.api.ProjectileCastEvent;
import mintychochip.ollivanders.api.SelfCastEvent;
import mintychochip.ollivanders.api.SpellCastEvent;
import mintychochip.ollivanders.container.Context;
import mintychochip.ollivanders.container.Spell;
import mintychochip.ollivanders.container.SpellMechanic;
import mintychochip.ollivanders.spells.shape.SpellArea;
import mintychochip.ollivanders.spells.shape.SpellProjectile;
import mintychochip.ollivanders.spells.shape.SpellSelf;
import mintychochip.ollivanders.wand.container.WandData;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;

public class SpellCaster {
    public static void cast(Spell spell, WandData wandData, Context context) {
        if(spell == null) {
            return;
        }
        SpellMechanic mechanic = spell.getMechanic();
        mechanic.setContext(context);
        mechanic.setWandData(wandData);
        Player player = mechanic.getContext().getPlayer();
        switch(mechanic.getShape()) {
            case PROJECTILE -> {
                if(mechanic instanceof SpellProjectile spellProjectile) {
                    Entity entity = spellProjectile.launchProjectile(mechanic.getMechanicSettings().getEntityType(), player, mechanic.getMechanicModifier());
                    if(entity != null) {
                        Ollivanders.getProjectileHandler().getProjectileMap().put(entity.getEntityId(), mechanic);
                        Bukkit.getPluginManager().callEvent(new ProjectileCastEvent(spell,context,wandData, (Projectile) entity));
                    }
                }
            }
            case AREA -> {
                if(mechanic instanceof SpellArea spellArea) {
                    boolean b = spellArea.castArea();
                    if(b) {
                        Bukkit.getPluginManager().callEvent(new AoeCastEvent(spell,context,wandData));
                    }
                }
            }
            case SELF -> {
                if(mechanic instanceof SpellSelf spellSelf) {
                    boolean b = spellSelf.castSelf();
                    if(b) {
                        Bukkit.getPluginManager().callEvent(new SelfCastEvent(spell,context,wandData));
                    }
                }
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
        SpellMechanic mechanic = spell.getMechanic();
        switch(mechanic.getShape()) {
            case AREA -> {
                if(mechanic instanceof SpellArea spellArea) {
                    boolean b = spellArea.castArea();
                }
            }
            case SELF -> {
                if(mechanic instanceof SpellSelf spellSelf) {
                    boolean b = spellSelf.castSelf();
                }
            }
        }
    }
}
