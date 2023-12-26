package mintychochip.ollivanders.api;

import mintychochip.ollivanders.container.Context;
import mintychochip.ollivanders.container.Spell;
import mintychochip.ollivanders.wand.container.WandData;
import org.bukkit.entity.Projectile;

public class ProjectileCastEvent extends SpellCastEvent {

    private final Projectile projectile;
    public ProjectileCastEvent(Spell spell, Context context, WandData wandData, Projectile projectile) {
        super(spell, context, wandData);
        this.projectile = projectile;
    }

    public Projectile getProjectile() {
        return projectile;
    }
}
