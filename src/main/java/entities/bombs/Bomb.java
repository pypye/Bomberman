package entities.bombs;

import com.jme3.math.Vector3f;
import entities.Entity;
import entities.players.Player;
import particles.BombSpark;

public class Bomb extends Entity {
    public static final double DURATION = 3000.0;
    private final BombSpark spark;
    private final double timeStarted;
    private final Player owner;

    public Bomb(Vector3f position, Player owner, double timeStarted) {
        super(position, "Models/Bomb/bomb.obj");
        this.spark = new BombSpark(spatial);
        this.timeStarted = timeStarted;
        this.blocked = false;
        this.owner = owner;
    }

    public BombSpark getSpark() {
        return spark;
    }

    public double getTimeStarted() {
        return timeStarted;
    }

    public Player getOwner() {
        return owner;
    }
}
