package entities.bombs;

import com.jme3.math.Vector3f;
import entities.Entity;
import particles.BombSpark;

public class Bomb extends Entity {
    public static final double DURATION = 3000.0;
    private final BombSpark spark;
    private final double timeStarted;
    private int explosionLength;
    public Bomb(Vector3f position, double timeStarted) {
        super(position, "Models/Bomb/bomb.obj");
        this.spark = new BombSpark(spatial);
        this.timeStarted = timeStarted;
        this.blocked = false;
        this.explosionLength = 1;
    }

    public BombSpark getSpark() {
        return spark;
    }

    public double getTimeStarted() {
        return timeStarted;
    }

    public int getExplosionLength() {
        return explosionLength;
    }

    public void setExplosionLength(int explosionLength) {
        this.explosionLength = explosionLength;
    }
}
