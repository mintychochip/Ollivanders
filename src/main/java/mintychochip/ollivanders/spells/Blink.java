package mintychochip.ollivanders.spells;

import mintychochip.ollivanders.container.WizardMechanic;
import mintychochip.ollivanders.shape.WizardSelf;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class Blink extends WizardMechanic implements WizardSelf {

    int range = 64;

    @Override
    public boolean castSelf() {
        Player player = getContext().getPlayer();
        Block targetBlockExact = player.getTargetBlockExact(range);
        if (targetBlockExact == null) {
            return false;
        }
        Location location = targetBlockExact.getLocation();
        location.setYaw(player.getLocation().getYaw());
        location.setPitch(player.getLocation().getPitch());
        player.teleport(location);
        player.playSound(player, Sound.ENTITY_FOX_TELEPORT, 3.0f, 3.0f);
        return true;
    }

    @Override
    public void applyParticleSelf() {
        Player player = getContext().getPlayer();
        Location location = player.getLocation();
        double y = location.getY();
        player.playEffect(player.getLocation(), Effect.ENDER_SIGNAL, 10);
    }
}
