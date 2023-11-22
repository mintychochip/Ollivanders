package mintychochip.orchid.spells;

import mintychochip.orchid.container.OrchidMechanic;
import mintychochip.orchid.shape.OrchidSelf;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Horse;
import org.bukkit.inventory.ItemStack;

public class SummonSteed extends OrchidMechanic implements OrchidSelf {
    @Override
    public boolean castSelf() { //apply a key to the horse
        Location castLocation = context.getPlayer().getLocation();
        if(castLocation.getWorld() == null) {
            return false;
        }
        Horse horse = (Horse) castLocation.getWorld().spawnEntity(castLocation, EntityType.HORSE);
        horse.getInventory().setSaddle(new ItemStack(Material.SADDLE));
        horse.setOwner(context.getPlayer());
        horse.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(2f);
        return true;
    }

    @Override
    public void applyParticleSelf() {

    }
}
