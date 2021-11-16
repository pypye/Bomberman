package entities.players;

import com.jme3.math.Vector3f;

public class Oneal extends Player {

    public Oneal(Vector3f position) {
        super(position, "Models/Oneal/oneal.gltf");
        this.spatial.setLocalScale(0.3f, 0.3f, 0.3f);
        this.offsetAngle = 0;
    }
}
