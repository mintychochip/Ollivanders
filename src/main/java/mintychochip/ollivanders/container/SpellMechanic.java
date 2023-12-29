package mintychochip.ollivanders.container;

import mintychochip.genesis.particle.GenesisShape;
import mintychochip.ollivanders.enums.Shape;
import mintychochip.ollivanders.spells.shape.SpellArea;
import mintychochip.ollivanders.spells.shape.SpellProjectile;
import mintychochip.ollivanders.spells.shape.SpellSelf;
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
    public static double effectiveFieldCalculation(double base, double modifier, double wandBoost) {
        if(base > modifier) {
            return base * wandBoost;
        }
        return modifier * wandBoost;
    }
    public double effectiveMagnitude() {
        return effectiveFieldCalculation(mechanicSettings.getMagnitude(),mechanicModifier.getMagnitude(),wandData.getWandBoost().getMagnitude());
    }
    public double effectiveRange() {
        return effectiveFieldCalculation(mechanicSettings.getRange(),-1,wandData.getWandBoost().getRange());
    }
    public double effectiveDuration() {
        return effectiveFieldCalculation(mechanicSettings.getDuration(),-1,wandData.getWandBoost().getDuration());
    }
    public WandData getWandData() {
        return wandData;
    }

    public Shape getShape() {
        return shape;
    }

    public MechanicSettings getMechanicSettings() {
        return mechanicSettings;
    }

    public Context getContext() {
        return context;
    }

    public MechanicModifier getMechanicModifier() {
        return mechanicModifier;
    }


    public Spell getTransition() {
        return transition;
    }


    public Location getCastLocation() {
        return context.getHitLocation() == null ? context.getPlayer().getLocation() : context.getHitLocation();
    }

    public List<Entity> getNearbyEntities() { //can probably make this better
        Location castLocation = getCastLocation();
        double effectiveRange = (mechanicSettings.getRange() * wandData.getWandBoost().getRange()) / 2; //diameter / 2
        if(castLocation != null && castLocation.getWorld() != null) {
            return new ArrayList<>(castLocation.getWorld().getNearbyEntities(castLocation, effectiveRange, effectiveRange, effectiveRange));
        }
        return null;
    }
    public List<LivingEntity> nearbyLivingEntities() {
        return getNearbyEntities().stream()
                .filter(entity -> (entity instanceof LivingEntity && entity != context.getPlayer()))
                .map(e -> (LivingEntity) e)
                .collect(Collectors.toList());
    }
    public SpellMechanic setWandData(WandData wandData) {
        this.wandData = wandData;
        return this;
    }

    public SpellMechanic setContext(Context context) {
        this.context = context;
        return this;
    }

    public SpellMechanic setMechanicSettings(MechanicSettings mechanicSettings) {
        this.mechanicSettings = mechanicSettings;
        return this;
    }

    public SpellMechanic setMechanicModifier(MechanicModifier mechanicModifier) {
        this.mechanicModifier = mechanicModifier;
        return this;
    }

    public SpellMechanic setTransition(Spell transition) {
        this.transition = transition;
        return this;
    }

    public SpellMechanic setShape(Shape shape) {
        this.shape = shape;
        return this;
    }
    public boolean isValidShape() {
        switch (shape) {
            case AREA: return this instanceof SpellArea;
            case PROJECTILE: return this instanceof SpellProjectile;
            case SELF: return this instanceof SpellSelf;
        }
        return false;
    }
    @Override
    public SpellMechanic clone() throws CloneNotSupportedException {
        return (SpellMechanic) super.clone();
    }
}
