package mintychochip.ollivanders.spellcaster;

import mintychochip.ollivanders.Ollivanders;
import mintychochip.ollivanders.container.Context;
import mintychochip.ollivanders.container.WizardSpell;
import mintychochip.ollivanders.events.AoeCastEvent;
import mintychochip.ollivanders.events.LaserCastEvent;
import mintychochip.ollivanders.events.SelfCastEvent;
import mintychochip.ollivanders.handler.ProjectileHandler;
import mintychochip.ollivanders.shape.*;
import mintychochip.ollivanders.shape.implementation.AoeImplementation;
import mintychochip.ollivanders.shape.implementation.LaserImplementation;
import mintychochip.ollivanders.shape.implementation.ProjectileImplementation;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

public class WizardCaster {

    private final WizardSpell spell;

    public WizardCaster(WizardSpell spell) {
        this.spell = spell;
    }

    public void cast(Context context) {
        spell.getMechanic().setContext(context);
        Shape shape = spell.getMechanic().getShape();
        new BukkitRunnable() {

            @Override
            public void run() {
                switch (shape) {
                    case PROJECTILE -> {
                        if (spell.getMechanic() instanceof WizardProjectile) {
                            int projectileImplementation = new ProjectileImplementation(spell.getMechanic()).castProjectile();
                            if (projectileImplementation > 0) {
                                ProjectileHandler.getInstance().getHitMap().put(projectileImplementation, spell);
                            }
                        }
                    }
                    case AOE -> {
                        if (spell.getMechanic() instanceof WizardAoe) {
                            boolean aoeImplementation = new AoeImplementation(spell.getMechanic()).castAoe();
                            if (aoeImplementation) {
                                Bukkit.getPluginManager().callEvent(new AoeCastEvent(shape, spell.getMechanic()));
                            }
                        }
                    }
                    case SELF -> {
                        if (spell.getMechanic() instanceof WizardSelf self) {
                            boolean b = self.castSelf();
                            if (b) {
                                Bukkit.getPluginManager().callEvent(new SelfCastEvent(shape, spell.getMechanic()));
                                self.applyParticleSelf();
                            }
                        }
                    }
                    case LASER -> {
                        if (spell.getMechanic() instanceof WizardLaser laser) {
                            int i = new LaserImplementation(spell.getMechanic()).castLaser();
                            if(i > 0) {
                                ProjectileHandler.getInstance().getHitMap().put(i, spell);

                                Bukkit.getPluginManager().callEvent(new LaserCastEvent(shape,spell.getMechanic()));
                            }

                    }
                    }
                    default -> {
                    }
                }
            }
        }.runTaskLater(Ollivanders.getInstance(), 0L);


    }

    public void callEvent() {

    }
}
