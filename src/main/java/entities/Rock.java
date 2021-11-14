package entities;

import com.jme3.math.Vector3f;

public class Rock extends Entity {
    public Rock(Vector3f position) {
        super(position, "Models/Rock/rock.obj");
        this.blocked = true;
    }
}
