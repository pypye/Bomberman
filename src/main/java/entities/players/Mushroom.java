package entities.players;

import com.jme3.math.Vector3f;

public class Mushroom extends Player {
    public Mushroom(Vector3f position) {
        super(position, "Models/Mushroom/mushroom.gltf");
        this.spatial.setLocalScale(0.05f, 0.05f, 0.05f);
    }
}
