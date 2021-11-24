package entities.players.enemies;

import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import entities.players.Player;
import entities.players.PlayerList;

public class Oneal extends Enemy {
    private boolean isChasingPlayer = false;

    public Oneal(Vector3f position) {
        super(position, "Models/Oneal/oneal.gltf");
        this.spatial.setLocalScale(0.3f, 0.3f, 0.3f);
        this.offsetAngle = 0;
    }

    @Override
    public void setNextMove(Vector2f enemy) {
        Player player = PlayerList.getMainPlayer();
        if (player != null && isChasingPlayer) {
            this.nextMove = nextMoveBase(enemy, player.getCord());
            if (this.nextMove == -1) isChasingPlayer = false;
        }
        if (!isChasingPlayer) {
            if (targetPoint == null || enemy.equals(targetPoint)) setTargetPoint(enemy, 3);
            this.nextMove = nextMoveBase(enemy, targetPoint);
            if (this.nextMove == -1) {
                setTargetPoint(enemy, 1);
                this.nextMove = nextMoveBase(enemy, targetPoint);
            }
        }
    }
}
