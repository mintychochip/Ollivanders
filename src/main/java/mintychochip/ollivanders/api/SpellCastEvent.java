package mintychochip.ollivanders.api;

import mintychochip.ollivanders.container.Context;
import mintychochip.ollivanders.container.Spell;
import mintychochip.ollivanders.wand.container.WandData;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class SpellCastEvent extends Event {
    private static final HandlerList handlers = new HandlerList();

    private Spell spell;
    private Context context;
    private WandData wandData;
    public SpellCastEvent(Spell spell, WandData wandData, Context context) {
        this.spell = spell;
        this.context = context;
        this.wandData = wandData;

    }
    public Spell getSpell() {
        return spell;
    }

    public void setSpell(Spell spell) {
        this.spell = spell;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public WandData getWandData() {
        return wandData;
    }

    public void setWandData(WandData wandData) {
        this.wandData = wandData;
    }

    public Player getPlayer() {
        return context.getPlayer();
    }
    public static HandlerList getHandlerList() {
        return handlers;
    }

    @NotNull
    @Override
    public HandlerList getHandlers() {
        return handlers;
    }
}
