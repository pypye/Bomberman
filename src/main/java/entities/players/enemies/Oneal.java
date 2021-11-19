package entities.players.enemies;

import com.jme3.math.Vector3f;
import entities.players.Player;

public class Oneal extends Player {

    public Oneal(Vector3f position) {
        super(position, "Models/Oneal/oneal.gltf");
        this.spatial.setLocalScale(0.3f, 0.3f, 0.3f);
        this.offsetAngle = 0;
    }
}
