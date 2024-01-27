package mintychochip.ollivanders.util;

import mintychochip.ollivanders.Ollivanders;
import mintychochip.ollivanders.api.SpellCastEvent;
import mintychochip.ollivanders.container.Context;
import mintychochip.ollivanders.container.Spell;
import mintychochip.ollivanders.container.SpellMechanic;
import mintychochip.ollivanders.items.container.WandData;
import mintychochip.ollivanders.spells.shape.SpellArea;
import mintychochip.ollivanders.spells.shape.SpellProjectile;
import mintychochip.ollivanders.spells.shape.SpellSelf;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;

public class SpellCaster { //only casts and calls event, need to check if effects hit, write the listeners for damageevents


    public static boolean cast(Spell spell, WandData wandData, Context context) {
        if (spell == null) {
            return false;
        }
        SpellMechanic mechanic = spell.getMechanic() //setup
                .setContext(context)
                .setWandData(wandData);
        Player player = context.getPlayer();
        if(!Permissions.playerHasSpellPermission(player,mechanic.getName().toLowerCase())) {
            return false;
        }
        if (!mechanic.isValidShape()) {
            return false;//check if the book shape is applicable to the spell
        }

        if (player.getTotalExperience() < mechanic.getMechanicSettings().getCost() && !Permissions.costBypass(player)) {
            player.sendMessage("out of xp");
            return false;
        }
        if(Ollivanders.getCooldownManager().hasCooldown(mechanic.getName() + "-" + mechanic.getShape(), mechanic.getMechanicSettings().getCooldown(), player.getUniqueId()) && !Permissions.cooldownBypass(player)) {
            player.sendMessage(mechanic.getName() + " " + Ollivanders.getCooldownManager().cooldownRemaining(mechanic.getName() + "-" + mechanic.getShape(),mechanic.getMechanicSettings().getCooldown(),player.getUniqueId()) + " seconds remaining");
            return false;
        }
        boolean b = genericCastMethod(mechanic); //was the mechanic able to be casted
        if (b) { //call initially, enter block if the cast was successful else return false
            Bukkit.getPluginManager().callEvent(new SpellCastEvent(spell, wandData, context)); //call event
            if (mechanic.getMechanicSettings().isPersistent()) {
                Ollivanders.getPersistentSpellManager().add(spell, context.getPlayer()); //if cast was persistent, then we can continue to cast
            }
        }
        return b;
    }

    public static boolean persistentCast(Spell spell) {
        return genericCastMethod(spell.getMechanic());
    }

    public static boolean genericCastMethod(SpellMechanic mechanic) {
        Player player = mechanic.getContext().getPlayer();
        boolean b = false;
        switch (mechanic.getShape()) {
            case PROJECTILE -> {
                if (mechanic instanceof SpellProjectile spellProjectile) {
                    Projectile entity = (Projectile) spellProjectile.launchProjectile(mechanic.getMechanicSettings().getEntityType(), player, mechanic.getMechanicModifier());
                    if (entity != null) {
                        Ollivanders.getProjectileHandler().getProjectileMap().put(entity.getEntityId(), mechanic);
                    }
                }
                b = true;
            }
            case AREA -> b = ((SpellArea) mechanic).castArea();
            case SELF -> b = ((SpellSelf) mechanic).castSelf();
        }
        return b;
    }
}
