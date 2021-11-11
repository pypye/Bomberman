package entities.bombs;

import com.jme3.math.Vector3f;
import entities.Entity;

public class Bomb extends Entity {
    public static final double DURATION = 3000.0;
    private final double timeStarted;

    public Bomb(Vector3f position, double timeStarted) {
        super(position, "Models/Bomb/bomb.obj");
        this.timeStarted = timeStarted;
    }

    public double getTimeStarted() {
        return timeStarted;
    }
}
