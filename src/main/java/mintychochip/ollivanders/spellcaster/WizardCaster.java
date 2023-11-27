package mintychochip.ollivanders.spellcaster;

import mintychochip.ollivanders.Ollivanders;
import mintychochip.ollivanders.betterwand.WandBoost;
import mintychochip.ollivanders.container.Context;
import mintychochip.ollivanders.container.WizardSpell;
import mintychochip.ollivanders.events.AoeCastEvent;
import mintychochip.ollivanders.events.LaserCastEvent;
import mintychochip.ollivanders.events.SelfCastEvent;
import mintychochip.ollivanders.handler.ProjectileHandler;
import mintychochip.ollivanders.shape.*;
import mintychochip.ollivanders.shape.Shape;
import mintychochip.ollivanders.shape.implementation.AoeImplementation;
import mintychochip.ollivanders.shape.implementation.LaserImplementation;
import mintychochip.ollivanders.shape.implementation.ProjectileImplementation;
import mintychochip.ollivanders.shape.implementation.SelfImplementation;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;

import java.awt.*;

public class WizardCaster {

    private final WizardSpell spell;

    public WizardCaster(WizardSpell spell) {
        this.spell = spell;
    }

    public void cast(Context context, WandBoost wandBoost) {
        spell.getMechanic().setContext(context);
        spell.getMechanic().setWandBoost(wandBoost);
        Shape shape = spell.getMechanic().getShape();
        String name = spell.getMechanic().getName();
        context.getPlayer().spigot().sendMessage(ChatMessageType.ACTION_BAR,TextComponent.fromLegacyText(ChatColor.DARK_GRAY + "Casting: " + ChatColor.GOLD + name));
        new BukkitRunnable() {

            @Override
            public void run() {
                switch (shape) {
                    case PROJECTILE -> {
                        if (spell.getMechanic() instanceof WizardProjectile) {
                            int projectileImplementation = new ProjectileImplementation(spell.getMechanic(), wandBoost).castProjectile();
                            if (projectileImplementation > 0) {
                                ProjectileHandler.getInstance().getHitMap().put(projectileImplementation, spell);
                            }
                        }
                    }
                    case AOE -> {
                        if (spell.getMechanic() instanceof WizardAoe) {
                            boolean aoeImplementation = new AoeImplementation(spell.getMechanic(), wandBoost).castAoe();
                            if (aoeImplementation) {
                                Bukkit.getPluginManager().callEvent(new AoeCastEvent(shape, spell.getMechanic(),spell));
                            }
                        }
                    }
                    case SELF -> {
                        if (spell.getMechanic() instanceof WizardSelf self) {
                            boolean b = new SelfImplementation(spell.getMechanic(), wandBoost).castSelf();
                            if (b) {
                                Bukkit.getPluginManager().callEvent(new SelfCastEvent(shape, spell.getMechanic(),spell));
                                self.applyParticleSelf();
                            }
                        }
                    }
                    case LASER -> {
                        if (spell.getMechanic() instanceof WizardLaser laser) {
                            int i = new LaserImplementation(spell.getMechanic(), wandBoost).castLaser();
                            if(i > 0) {
                                ProjectileHandler.getInstance().getHitMap().put(i, spell);

                                Bukkit.getPluginManager().callEvent(new LaserCastEvent(shape,spell.getMechanic(),spell));
                            }

                    }
                    }
                    default -> {
                    }
                }
            }
        }.runTaskLater(Ollivanders.getInstance(), 0L);


    }

    public void persistentCast(Context context, WandBoost wandBoost) {
        spell.getMechanic().setContext(context);
        spell.getMechanic().setWandBoost(wandBoost);
        Shape shape = spell.getMechanic().getShape();
        new BukkitRunnable() {

            @Override
            public void run() {
                switch (shape) {
                    case AOE -> {
                        if (spell.getMechanic() instanceof WizardAoe) {
                            boolean aoeImplementation = new AoeImplementation(spell.getMechanic(), wandBoost).castAoe();
                        }
                    }
                    case SELF -> {
                        if (spell.getMechanic() instanceof WizardSelf self) {
                            boolean b = new SelfImplementation(spell.getMechanic(), wandBoost).castSelf();
                            if (b) {
                                self.applyParticleSelf();
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
