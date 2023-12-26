package mintychochip.ollivanders.listener;

import mintychochip.genesis.Genesis;
import mintychochip.genesis.particle.GenesisShape;
import mintychochip.genesis.particle.ParticleShape;
import mintychochip.ollivanders.api.AoeCastEvent;
import mintychochip.ollivanders.api.ProjectileCastEvent;
import mintychochip.ollivanders.api.SelfCastEvent;
import mintychochip.ollivanders.api.SpellCastEvent;
import mintychochip.ollivanders.config.Registry;
import mintychochip.ollivanders.container.MechanicSettings;
import mintychochip.ollivanders.spells.shape.SpellProjectile;
import org.bukkit.Bukkit;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class ParticleListener implements Listener {

    @EventHandler
    public void onProjectileCast(final ProjectileCastEvent event) {


    }
    @EventHandler
    public void onSelfCastEvent(final SelfCastEvent event) {
    }
}
