package entities.players.enemies;

import com.jme3.math.FastMath;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import entities.players.Player;
import entities.players.PlayerList;

public class Spider extends Enemy {
    private static int count = 0;
    private static final float CHASING_DURATION = 5f;
    private static final float CHASING_COOL_DOWN_DURATION = 10f;
    private float chasingTime = 0.0f;
    private float chasingCoolDownTime = (float) (Math.random() * 10f);

    public Spider(Vector3f position) {
        super(position, "Models/Monster/spider.obj");
        this.offsetAngle = FastMath.PI;
        count++;
    }

    @Override
    public void setNextMove(Vector2f enemy) {
        Player player = PlayerList.getMainPlayer();
        //if ultimate is activated, chase the player
        if (player != null && ultimateActivated) {
            this.nextMove = Turtle.nextMoveBase(enemy, player.getCord());
            if (this.nextMove == -1) ultimateActivated = false;
        }
        if (!ultimateActivated) {
            if (targetPoint == null || enemy.equals(targetPoint)) setTargetPoint(enemy, 3);
            this.nextMove = Turtle.nextMoveBase(enemy, targetPoint);
            if (this.nextMove == -1) {
                setTargetPoint(enemy, 1);
                this.nextMove = Turtle.nextMoveBase(enemy, targetPoint);
            }
        }
    }

    @Override
    public void onUpdate(float tpf) {
        super.onUpdate(tpf);
        if (!ultimateActivated) {
            chasingCoolDownTime -= tpf;
            if (chasingCoolDownTime <= 0) {
                ultimateActivated = true;
                chasingTime = CHASING_DURATION;
                System.out.println("[Debug/Spider] " + this + " started chasing player");
            }
        } else {
            chasingTime -= tpf;
            if (chasingTime <= 0) {
                ultimateActivated = false;
                chasingCoolDownTime = CHASING_COOL_DOWN_DURATION;
                System.out.println("[Debug/Spider] " + this + " stopped chasing player");
            }
        }
    }

    @Override
    public void remove() {
        super.remove();
        count--;
    }

    public static int getCount() {
        return count;
    }
}
