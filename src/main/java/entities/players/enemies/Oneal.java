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
    public int nextMove(Vector2f enemy, Vector2f player, int[][] map) {
        if (isChasingPlayer) {
            int ans = nextMoveBase(enemy, player, map);
            if(ans == -1) isChasingPlayer = false;
            else return ans;
        } else {
            Vector2f targetPoint = getRandomTargetPoint();
            if (targetPoint == null
                || (int) enemy.getX() == (int) targetPoint.getX()
                || (int) enemy.getY() == (int) targetPoint.getY()) {
                setRandomTargetPoint(enemy, map, 5);
                if(getRandomTargetPoint() == null) setRandomTargetPoint(enemy, map, 1);
            }
            targetPoint = getRandomTargetPoint();
            return nextMoveBase(enemy, targetPoint, map);
        }
        return -1;
    }
}
