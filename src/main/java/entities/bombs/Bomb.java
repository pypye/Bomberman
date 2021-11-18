package entities.bombs;

import com.jme3.math.Vector3f;
import entities.Entity;
import entities.players.Player;
import particles.BombSparkParticle;

public class Bomb extends Entity {
    public static final double DURATION = 3000.0;
    public static final double COOL_DOWN = 4000.0;
    private final BombSparkParticle spark;
    private final double timeStarted;
    private final Player owner;

    public Bomb(Vector3f position, Player owner, double timeStarted) {
        super(position, "Models/Bomb/bomb.obj");
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
