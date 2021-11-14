package entities;

import com.jme3.math.Vector3f;

public class Tree extends Entity{

    public Tree(Vector3f position) {
        super(position, "Models/Tree/tree.obj");
        this.blocked = true;
    }
}
