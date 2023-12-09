package mintychochip.ollivanders.util;

import mintychochip.ollivanders.container.MechanicModifier;
import mintychochip.ollivanders.container.Spell;
import mintychochip.ollivanders.enums.Shape;
import mintychochip.ollivanders.spells.shape.SpellArea;
import mintychochip.ollivanders.spells.shape.SpellProjectile;
import mintychochip.ollivanders.spells.shape.SpellSelf;
import mintychochip.ollivanders.spells.shape.SpellSight;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.io.IOException;

public class SpellCaster {
    public static void cast(Spell spell) {
        if(spell == null) {
            return;
        }
        Player player = spell.getMechanic().getContext().getPlayer();
        switch(spell.getMechanic().getShape()) {
            case PROJECTILE -> {
                if(spell.getMechanic() instanceof SpellProjectile spellProjectile) {
                    int i = spellProjectile.launchProjectile(EntityType.FIREBALL, player, spell.getMechanic().getMechanicModifier());
                    if(i > 0) {
                        //generate a random number here, can just use genesis rand
                    }
                }
            }
            case AREA -> {

            }
            case SELF -> {

            }
            case SIGHT -> {
                
            }
        }
    }
}
