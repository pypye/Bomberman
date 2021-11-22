package entities.players.enemies;

import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;

public class Oneal extends Enemy {
    private boolean isChasingPlayer = false;

    public Oneal(Vector3f position) {
        super(position, "Models/Oneal/oneal.gltf");
        this.spatial.setLocalScale(0.3f, 0.3f, 0.3f);
        this.offsetAngle = 0;
    }

    @Override
    public int nextMove(Vector2f enemy, int[][] map) {
        if (isChasingPlayer) {
            //TODO: a function that chase player
            //Like finding path
        } else {
            //TODO: make random move
        }
        return 0;
    }
}
