package entities.players;

import algorithms.FindPathAI;
import algorithms.FindPathMainPlayerAI;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import cores.Debugger;
import cores.Map;
import entities.Entity;
import entities.players.enemies.Enemy;
import scenes.SceneController;

public class MainPlayerAI extends MainPlayer {
    private boolean moving = false;
    protected int nextMove = Enemy.STAND;
    private Vector2f prefMove = null;

    public MainPlayerAI(Vector3f position) {
        super(position);
    }

    public void onMoving() {
        if (!this.isMoving()) {
            prefMove = this.getCord();
            this.setNextMove();
            Debugger.log(Debugger.ENTITY, "AI " + this + " position is " + prefMove);
            Debugger.log(Debugger.ENTITY, "AI " + this + " next move is " + nextMove);
            this.setMoving(true);
        }
        if (this.isMoving()) {
            switch (this.getNextMove()) {
                case Enemy.LEFT:
                    this.moveLeft(speed / 300f);
                    fixPosition(0, -1);
                    break;
                case Enemy.RIGHT:
                    this.moveRight(speed / 300f);
                    fixPosition(0, 1);
                    break;
                case Enemy.UP:
                    this.moveForward(speed / 300f);
                    fixPosition(1, 0);
                    break;
                case Enemy.DOWN:
                    this.moveBackward(speed / 300f);
                    fixPosition(-1, 0);
                    break;
                case Enemy.SET_BOMB:
                    this.setBomb();
                    this.setMoving(false);
                    break;
                case Enemy.STAND:
                    this.setMoving(false);
                    break;
            }
        }
    }

    private void fixPosition(int x, int y) {
        Vector2f enemyPos = new Vector2f(this.getPosition().x, this.getPosition().z);
        if (Map.isBlocked((int) prefMove.x + x, (int) prefMove.y + y)) {
            this.prefMove = new Vector2f(prefMove.x + x, prefMove.y + y);
            if (this.nextMove >= 2) this.nextMove = 5 - this.nextMove;
            else this.nextMove = 1 - this.nextMove;
        } else {
            Vector2f center = Entity.getCenterCord(prefMove.x + x, prefMove.y + y);
            if (x > 0) {
                if (enemyPos.x >= center.x) {
                    enemyPos.x = center.x;
                    this.setMoving(false);
                }
            } else if (x < 0) {
                if (enemyPos.x <= center.x) {
                    enemyPos.x = center.x;
                    this.setMoving(false);
                }
            }
            if (y > 0) {
                if (enemyPos.y >= center.y) {
                    enemyPos.y = center.y;
                    this.setMoving(false);
                }
            } else if (y < 0) {
                if (enemyPos.y <= center.y) {
                    enemyPos.y = center.y;
                    this.setMoving(false);
                }
            }
        }
        this.setPosition(new Vector3f(enemyPos.x, 1, enemyPos.y));
    }

    public int getNextMove() {
        return nextMove;
    }

    public void setNextMove() {
        FindPathMainPlayerAI a = new FindPathMainPlayerAI(
                SceneController.getCurrentScene().getLevel());
        Debugger.log(Debugger.EVENT,
                "AI move case is " + a.moveCase((int) this.getCord().x, (int) this.getCord().y));
        this.nextMove = a.nextMove((int) this.getCord().x, (int) this.getCord().y);

    }

    public boolean isMoving() {
        return moving;
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
    }
}
