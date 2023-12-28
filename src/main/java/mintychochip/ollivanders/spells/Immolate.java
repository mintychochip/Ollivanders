package mintychochip.ollivanders.spells;

import mintychochip.genesis.Genesis;
import mintychochip.ollivanders.container.SpellMechanic;
import mintychochip.ollivanders.spells.shape.SpellArea;
import mintychochip.ollivanders.spells.shape.SpellSelf;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.List;

public class Immolate extends SpellMechanic implements SpellArea, SpellSelf {
    @Override
    public boolean castArea() {
        return genericCastMethod();
    }

    @Override
    public boolean castSelf() {
        return genericCastMethod();
    }

    public boolean genericCastMethod() {
        Location castLocation = getCastLocation();
        double range = getEffectiveRange() / 2;
        double volume = Math.pow(range, 3) * ((double) 4 / 3) * Math.PI;
        castLocation.getWorld().spawnParticle(Particle.FLAME, castLocation, (int) (volume * .25), range, range, range, 0, null, true);


        List<LivingEntity> nearbyLivingEntities = getNearbyLivingEntities();
        if (nearbyLivingEntities == null) {
            return false;
        }
        for (LivingEntity nearbyLivingEntity : nearbyLivingEntities) { //should just remove the player originally anyway
            int ceil = (int) Math.ceil(getEffectiveDuration());
            if (ceil < 20) {
                ceil = 20;
            } //custom firetick method
        }
        return true;
    }
}
