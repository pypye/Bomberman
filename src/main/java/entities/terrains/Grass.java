package entities.terrains;

import com.jme3.math.Vector3f;
import entities.Entity;

public class Grass extends Entity {
    public Grass(Vector3f position) {
        super(position, "Models/Grass/grass.obj");
    }

}
