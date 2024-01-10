package mintychochip.ollivanders.listener;

import mintychochip.genesis.Genesis;
import mintychochip.ollivanders.api.SpellDamageEvent;
import mintychochip.ollivanders.enums.DamageType;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Particle;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

public class SpellDamageListener implements Listener {
    @EventHandler
    public void onEntityFireDeath(final EntityDeathEvent event) {
        NamespacedKey fire = Genesis.getKeys().getMap().get("fire");
        String[] meats = {"PORKCHOP","BEEF","CHICKEN","COD","MUTTON"};
        Long l = event.getEntity().getPersistentDataContainer().get(fire, PersistentDataType.LONG);
        if(l != null) {
            for (ItemStack drop : event.getDrops()) {
                String string = drop.getType().toString();
                for (String meat : meats) {
                    if(string.equals(meat)) {
                        string = "COOKED_" + string;
                        drop.setType(Material.valueOf(string));
                    }
                }
            }

        }
    }
    @EventHandler
    public void onSpellDamageEvent(final SpellDamageEvent event) {
        if(event.getDamageType() == DamageType.FIRE) {
            Location location = event.getInflicted().getLocation();
            location.getWorld().spawnParticle(Particle.SMOKE_LARGE,location,2,0,0,0,0,null,true);
        }
    }
}
