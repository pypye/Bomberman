package entities.terrains;

import com.jme3.math.Vector3f;
import entities.Entity;

public class Tree extends Entity {

    public Tree(Vector3f position) {
        super(position, "Models/Tree/tree.obj");
        this.blocked = true;
    }
}
