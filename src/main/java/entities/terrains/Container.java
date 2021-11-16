package entities.terrains;

import com.jme3.math.Vector3f;
import entities.Entity;

public class Container extends Entity {
    public Container(Vector3f position) {
        super(position, "Models/Container/container.obj");
        this.blocked = true;
    }
}
