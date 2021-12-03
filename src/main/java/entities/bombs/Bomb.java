package entities.bombs;

import com.jme3.math.Vector3f;
import entities.Entity;
import entities.players.Player;
import particles.BombSparkParticle;

public class Bomb extends Entity {
    public static final double DURATION = 3000.0;
    public static final double COOL_DOWN = 4;
    public static final String BOMB_DEFAULT = "Models/Bomb/bomb.obj";
    public static final String BOMB_UPGRADE = "Models/BombUpgrade/bomb_upgrade.obj";
    private final BombSparkParticle spark;
    private final double timeStarted;
    private final Player owner;

    public Bomb(Vector3f position, Player owner, double timeStarted, boolean buff) {
        super(position, buff ? BOMB_UPGRADE : BOMB_DEFAULT);
        this.spark = new BombSparkParticle(spatial);
        this.timeStarted = timeStarted;
        this.blocked = false;
        this.owner = owner;
    }

    public BombSparkParticle getSpark() {
        return spark;
    }

    public double getTimeStarted() {
        return timeStarted;
    }

    public Player getOwner() {
        return owner;
    }
}
