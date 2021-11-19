package entities.players.enemies;

import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;

public class Mushroom extends Enemy {
    private Vector2f randomTargetPoint;
    public Mushroom(Vector3f position) {
        super(position, "Models/Mushroom/mushroom.gltf");
        this.spatial.setLocalScale(0.05f, 0.05f, 0.05f);
    }

    @Override
    public int nextMove() {
        //TODO: random move
        //hint: bfs to a point
        return 0;
    }
}
