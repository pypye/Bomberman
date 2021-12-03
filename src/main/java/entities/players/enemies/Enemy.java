package entities.players.enemies;

import algorithms.spawn.RandomizeMap;
import com.jme3.collision.CollisionResults;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import cores.Debugger;
import cores.Map;
import entities.Entity;
import entities.players.Player;
import entities.players.PlayerList;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public abstract class Enemy extends Player {
    private static int count = 0;
    public static final int STAND = -1;
    public static final int LEFT = 0;
    public static final int RIGHT = 1;
    public static final int DOWN = 2;
    public static final int UP = 3;
    public static final int SET_BOMB = 4;

    public static final int[] dx = {0, 0, -1, 1, -1, -1, 1, 1};
    public static final int[] dy = {-1, 1, 0, 0, -1, 1, -1, 1};
    private boolean moving = false;
    protected int nextMove = STAND;

    private Vector2f prefMove = null;

    public Enemy(Vector3f position, String path) {
        super(position, path);
        count++;
    }

    public boolean isCollisionWithMainPlayer() {
        CollisionResults results = new CollisionResults();
        if (PlayerList.getMainPlayer() != null) {
            PlayerList.getMainPlayer().getSpatial().getWorldBound().collideWith(getSpatial().getWorldBound(), results);
            return results.size() > 0;
        }
        return false;
    }

    public void onMoving() {
        if (!this.isMoving()) {
            prefMove = this.getCord();
            this.setNextMove(prefMove);
            Debugger.log(Debugger.ENTITY, "Enemy " + this + " position is " + prefMove);
            Debugger.log(Debugger.ENTITY, "Enemy " + this + " next move is " + nextMove);
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

    public abstract void setNextMove(Vector2f enemy);

    public boolean isMoving() {
        return moving;
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
    }

    @Override
    public void remove() {
        super.remove();
        count--;
    }

    public static int getCount() {
        return count;
    }

    public static void setCount(int count) {
        Enemy.count = count;
    }
}
