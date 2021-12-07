package entities.players.enemies;

import algorithms.RandomizeMap;
import com.jme3.bounding.BoundingBox;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import cores.Map;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class Turtle extends Enemy {
    private Vector2f targetPoint = null;

    public Turtle(Vector3f position) {
        super(position, "Models/Monster/turtle.obj");
        this.spatial.setModelBound(new BoundingBox());
        this.offsetAngle = 0;
    }

    @Override
    public void setNextMove(Vector2f enemy) {
        if (targetPoint == null || enemy.equals(targetPoint)) {
            targetPoint = setTargetPoint(enemy, 3);
        }
        this.nextMove = nextMoveBase(enemy, targetPoint);
        if (this.nextMove == -1) {
            targetPoint = setTargetPoint(enemy, 1);
            this.nextMove = nextMoveBase(enemy, targetPoint);
        }
    }

    public static Vector2f setTargetPoint(Vector2f start, int dist) {
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
        return ans.isEmpty() ? null : ans.get(randomInt);
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
}
