package entities.bombs;

import com.jme3.math.Vector3f;
import entities.Entity;
import entities.players.Player;
import particles.BombSparkParticle;

public class Bomb extends Entity {
    public static final double DURATION = 3;
    public static final double COOL_DOWN = 4;
    public static final String BOMB_DEFAULT = "Models/Bomb/bomb.obj";
    public static final String BOMB_UPGRADE = "Models/BombUpgrade/bomb_upgrade.obj";
    private final BombSparkParticle spark;
    private final int bombExplodeLength;
    private final Player owner;
    private double timeElapsed = 0.0f;

    public Bomb(Vector3f position, Player owner, boolean buff) {
        super(position, buff ? BOMB_UPGRADE : BOMB_DEFAULT);
        this.bombExplodeLength = buff ? 3 : 2;
        this.spark = new BombSparkParticle(spatial);
        this.blocked = false;
        this.owner = owner;
    }

    public BombSparkParticle getSpark() {
        return spark;
    }

    public Player getOwner() {
        return owner;
    }

    public double getTimeElapsed() {
        return timeElapsed;
    }

    public void setTimeElapsed(double timeElapsed) {
        this.timeElapsed = timeElapsed;
    }

    public int getBombExplodeLength() {
        return bombExplodeLength;
    }
}
