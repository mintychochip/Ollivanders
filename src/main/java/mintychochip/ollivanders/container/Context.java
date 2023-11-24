package mintychochip.ollivanders.container;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class Context {
    private Player player;
    private Block hitBlock;
    private Entity hitEntity;
    private Location hitLocation;

    public Context(Player player, Location hitLocation, Block hitBlock, Entity hitEntity) {
        this.player = player;
        this.hitLocation = hitLocation;
        this.hitBlock = hitBlock;
        this.hitEntity = hitEntity;
        if (hitBlock != null || hitEntity != null) {
            if (hitBlock != null) {
                this.hitLocation = hitBlock.getLocation();
            } else {
                this.hitLocation = hitEntity.getLocation();
            }
        }
    }

    public Context(Player player, Location hitLocation) {
        this(player, hitLocation, null, null);
    }

    public Context(Player player) {
        this(player, null);
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Block getHitBlock() {
        return hitBlock;
    }

    public void setHitBlock(Block hitBlock) {
        this.hitBlock = hitBlock;
    }

    public Entity getHitEntity() {
        return hitEntity;
    }

    public void setHitEntity(Entity hitEntity) {
        this.hitEntity = hitEntity;
    }

    public Location getHitLocation() {
        return hitLocation;
    }

    public void setHitLocation(Location hitLocation) {
        this.hitLocation = hitLocation;
    }
}
