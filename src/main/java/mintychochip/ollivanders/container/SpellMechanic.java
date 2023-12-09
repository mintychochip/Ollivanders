package mintychochip.ollivanders.container;

import mintychochip.ollivanders.enums.Shape;
import mintychochip.ollivanders.wand.container.WandBoost;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public abstract class SpellMechanic implements Cloneable {

    protected Context context;
    protected MechanicSettings mechanicSettings;
    protected MechanicModifier mechanicModifier;
    protected Spell transition;

    protected Shape shape;
    public void setShape(Shape shape) {
        this.shape = shape;
    }

    public Shape getShape() {
        return shape;
    }

    public void setMechanicSettings(MechanicSettings mechanicSettings) {
        this.mechanicSettings = mechanicSettings;
    }

    public MechanicSettings getMechanicSettings() {
        return mechanicSettings;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public MechanicModifier getMechanicModifier() {
        return mechanicModifier;
    }

    public void setMechanicModifier(MechanicModifier mechanicModifier) {
        this.mechanicModifier = mechanicModifier;
    }

    public Spell getTransition() {
        return transition;
    }

    public void setTransition(Spell transition) {
        this.transition = transition;
    }


    public Location getCastLocation() {
        return context.getHitLocation() == null ? context.getPlayer().getLocation() : context.getHitLocation();
    }

    public List<Entity> getNearbyEntities(double v) {
        Location castLocation = getCastLocation();
        double effectiveRange = mechanicSettings.getRange() * v / 2.0; //diameter / 2
        if(castLocation != null) {
            castLocation.getWorld().getNearbyEntities(castLocation, effectiveRange,effectiveRange,effectiveRange);
        }
        return null;
    }
    public List<Entity> getNearbyEntities(WandBoost wandBoost) {
        return getNearbyEntities(wandBoost.getRange());
    }
    public List<LivingEntity> getNearbyLivingEntities(double v) {
        List<LivingEntity> list = new ArrayList<>();
        for (Entity entity : getNearbyEntities(v)) {
            if (entity instanceof LivingEntity livingEntity) {
                list.add(livingEntity);
            }
        }
        return list;

    }
    public List<LivingEntity> getNearbyLivingEntities(WandBoost wandBoost) {
        return getNearbyLivingEntities(wandBoost.getRange());
    }
    @Override
    public SpellMechanic clone() throws CloneNotSupportedException {
        return (SpellMechanic) super.clone();
    }
}
