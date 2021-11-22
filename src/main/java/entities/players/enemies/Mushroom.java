package entities.players.enemies;

import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;

public class Mushroom extends Enemy {
    public Mushroom(Vector3f position) {
        super(position, "Models/Mushroom/mushroom.gltf");
        this.spatial.setLocalScale(0.05f, 0.05f, 0.05f);
    }

    @Override
    public int nextMove(Vector2f enemy, int[][] map) {
        Vector2f targetPoint = getRandomTargetPoint();
        if (enemy == null || targetPoint == null
            || (int) enemy.getX() == (int) targetPoint.getX()
            || (int) enemy.getY() == (int) targetPoint.getY()) {
            setRandomTargetPoint(enemy, map, 5);
        }
        targetPoint = getRandomTargetPoint();
        return nextMoveBase(enemy, targetPoint, map);
        //hint: bfs to a point
    }
}
