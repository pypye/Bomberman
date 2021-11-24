package entities.players.enemies;

import algorithms.RandomizeMap;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import cores.Map;
import entities.Entity;
import entities.players.Player;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public abstract class Enemy extends Player {
    public static final int STAND = -1;
    public static final int LEFT = 0;
    public static final int RIGHT = 1;
    public static final int DOWN = 2;
    public static final int UP = 3;

    public static final int[] dx = {0, 0, -1, 1, -1, -1, 1, 1};
    public static final int[] dy = {-1, 1, 0, 0, -1, 1, -1, 1};

    private boolean moving = false;
    protected int nextMove = STAND;
    protected Vector2f targetPoint = null;
    private Vector2f prefMove = null;

    public Enemy(Vector3f position, String path) {
        super(position, path);
    }

    public void onMoving() {
        if (!this.isMoving()) {
            prefMove = this.getCord();
            System.out.println("[Debug/EnemyPosition] " + prefMove);
            this.setNextMove(prefMove);
            System.out.println("[Debug/EnemyNextMove] " + nextMove);
            this.setMoving(true);
        }
        if (this.isMoving()) {
            switch (this.getNextMove()) {
                case Enemy.LEFT:
                    this.moveLeft(0.01f);
                    fixPosition(0, -1);
                    break;
                case Enemy.RIGHT:
                    this.moveRight(0.01f);
                    fixPosition(0, 1);
                    break;
                case Enemy.UP:
                    this.moveForward(0.01f);
                    fixPosition(1, 0);
                    break;
                case Enemy.DOWN:
                    this.moveBackward(0.01f);
                    fixPosition(-1, 0);
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

    public Vector2f getTargetPoint() {
        return targetPoint;
    }

    public void setTargetPoint(Vector2f start, int dist) {
        int u = (int) start.x;
        int v = (int) start.y;

        boolean[][] visited = new boolean[Map.SIZE][Map.SIZE];
        int[][] step = new int[Map.SIZE][Map.SIZE];
        Queue<Vector2f> queue = new LinkedList<>();
        ArrayList<Vector2f> ans = new ArrayList<>();
        queue.add(new Vector2f(u, v));
        visited[u][v] = true;
        step[u][v] = 0;

        while (!queue.isEmpty()) {
            Vector2f cell = queue.peek();
            queue.remove();
            int x = (int) cell.x;
            int y = (int) cell.y;
            int st = step[x][y];
            if (st == dist) ans.add(new Vector2f(x, y));
            if (st > dist) continue;
            for (int i = 0; i < 4; i++) {
                int ax = x + dx[i];
                int ay = y + dy[i];
                if (Map.isInside(ax, ay) && !visited[ax][ay] && !Map.isBlocked(ax, ay)) {
                    queue.add(new Vector2f(ax, ay));
                    visited[ax][ay] = true;
                    step[ax][ay] = st + 1;
                }
            }
        }
        int randomInt = RandomizeMap.randomInt((int) Math.sqrt(ans.size()));
        targetPoint = ans.isEmpty() ? null : ans.get(randomInt);
    }

    public static int nextMoveBase(Vector2f start, Vector2f finish) {
        int u = (int) start.x;
        int v = (int) start.y;
        int ans = STAND;
        if (finish == null) return ans;

        boolean[][] visited = new boolean[Map.SIZE][Map.SIZE];
        Vector2f[][] parent = new Vector2f[Map.SIZE][Map.SIZE];
        int[][] direction = new int[Map.SIZE][Map.SIZE];

        Queue<Vector2f> queue = new LinkedList<>();
        queue.add(new Vector2f(u, v));
        parent[u][v] = new Vector2f(u, v);
        visited[u][v] = true;
        direction[u][v] = STAND;

        while (!queue.isEmpty()) {
            Vector2f cell = queue.peek();
            queue.remove();
            int x = (int) cell.x;
            int y = (int) cell.y;
            if (cell.equals(finish)) {
                Vector2f par = parent[x][y];
                while (x != (int) par.x || y != (int) par.y) {
                    if (direction[x][y] != STAND) ans = direction[x][y];
                    x = (int) par.x;
                    y = (int) par.y;
                    par = parent[x][y];
                }
                return ans;
            }
            for (int i = 0; i < 4; i++) {
                int ax = x + dx[i];
                int ay = y + dy[i];
                if (Map.isInside(ax, ay) && !visited[ax][ay] && !Map.isBlocked(ax, ay)) {
                    queue.add(new Vector2f(ax, ay));
                    visited[ax][ay] = true;
                    parent[ax][ay] = new Vector2f(x, y);
                    direction[ax][ay] = i;
                }
            }
        }
        return ans;
    }

    public boolean isMoving() {
        return moving;
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
    }


}
