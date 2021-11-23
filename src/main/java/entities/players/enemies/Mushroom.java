package entities.players.enemies;

import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;

public class Mushroom extends Enemy {
    public Mushroom(Vector3f position) {
        super(position, "Models/Mushroom/mushroom.gltf");
        this.spatial.setLocalScale(0.05f, 0.05f, 0.05f);
    }

    @Override
    public int nextMove(Vector2f enemy, Vector2f player, int[][] map) {
        Vector2f targetPoint = getRandomTargetPoint();
        if (targetPoint == null
                || (enemy.getX() == targetPoint.getX()
                && enemy.getY() ==  targetPoint.getY())) {
            setRandomTargetPoint(enemy, map, 3);
            if(getRandomTargetPoint() == null) setRandomTargetPoint(enemy, map, 1);
        }
        targetPoint = getRandomTargetPoint();
        return nextMoveBase(enemy, targetPoint, map);
    }
}
