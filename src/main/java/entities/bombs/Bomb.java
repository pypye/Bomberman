package entities.bombs;

import com.jme3.math.Vector3f;
import entities.Entity;

public class Bomb extends Entity {
    public static final float DURATION = 3.0f;
    private float timeStarted;
    public Bomb(Vector3f position, float timeStarted) {
        super(position, "Models/Bomb/bomb.obj");
        this.timeStarted = timeStarted;
    }
}
