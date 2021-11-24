package entities.players.enemies;

import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;

public class Mushroom extends Enemy {
    public Mushroom(Vector3f position) {
        super(position, "Models/Mushroom/mushroom.gltf");
        this.spatial.setLocalScale(0.05f, 0.05f, 0.05f);
    }

    @Override
    public void setNextMove(Vector2f enemy) {
        if (targetPoint == null || enemy.equals(targetPoint)) setTargetPoint(enemy, 3);
        this.nextMove = nextMoveBase(enemy, targetPoint);
        if (this.nextMove == -1) {
            setTargetPoint(enemy, 1);
            this.nextMove = nextMoveBase(enemy, targetPoint);
        }
    }
}
