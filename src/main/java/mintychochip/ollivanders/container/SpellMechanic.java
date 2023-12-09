package mintychochip.ollivanders.container;

import mintychochip.ollivanders.enums.Shape;
import mintychochip.ollivanders.wand.container.WandBoost;
import mintychochip.ollivanders.wand.container.WandData;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public abstract class SpellMechanic implements Cloneable {

    protected WandData wandData;
    protected Context context;
    protected MechanicSettings mechanicSettings;
    protected MechanicModifier mechanicModifier;
    protected Spell transition;

    protected Shape shape;

    public double effectiveFieldCalculation(double base, double modifier, double wandBoost) {
        if(base > modifier) {
            return base * wandBoost;
        }
        return modifier * wandBoost;
    }
    public double getEffectiveMagnitude() {
        return effectiveFieldCalculation(mechanicSettings.getMagnitude(),mechanicModifier.getMagnitude(),wandData.getWandBoost().getMagnitude());
    }
    public double getEffectiveRange() {
        return effectiveFieldCalculation(mechanicSettings.getRange(),-1,wandData.getWandBoost().getRange());
    }
    public double getEffectiveDuration() {
        return effectiveFieldCalculation(mechanicSettings.getDuration(),-1,wandData.getWandBoost().getDuration());
    }
    public WandData getWandData() {
        return wandData;
    }

    public void setWandData(WandData wandData) {
        this.wandData = wandData;
    }

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

    public List<Entity> getNearbyEntities() {
        Location castLocation = getCastLocation();
        double effectiveRange = mechanicSettings.getRange() * wandData.getWandBoost().getRange() / 2.0; //diameter / 2
        if(castLocation != null && castLocation.getWorld() != null) {
            castLocation.getWorld().getNearbyEntities(castLocation, effectiveRange,effectiveRange,effectiveRange);
        }
        return null;
    }
    public List<LivingEntity> getNearbyLivingEntities() {
        List<LivingEntity> list = new ArrayList<>();
        for (Entity entity : getNearbyEntities()) {
            if (entity instanceof LivingEntity livingEntity) {
                list.add(livingEntity);
            }
        }
        return list;

    }
    @Override
    public SpellMechanic clone() throws CloneNotSupportedException {
        return (SpellMechanic) super.clone();
    }
}
