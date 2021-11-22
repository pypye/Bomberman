package entities.players.enemies;

import algorithms.RandomizeMap;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import entities.players.Player;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public abstract class Enemy extends Player {

  public static final int N = 20;
  public static final int LEFT = 0;
  public static final int Right = 1;
  public static final int UP = 2;
  public static final int DOWN = 3;
  public static final int[] dx = {0, 0, -1, 1, -1, -1, 1, 1};
  public static final int[] dy = {-1, 1, 0, 0, -1, 1, -1, 1};

  private Vector2f randomTargetPoint = null;

  public Enemy(Vector3f position, String path) {
    super(position, path);
  }

  public abstract int nextMove(Vector2f enemy, int[][] map);

  public Vector2f getRandomTargetPoint() {
    return randomTargetPoint;
  }

  public static boolean isIn(int u, int v) {
    return u >= 0 && v >= 0 && u < N && v < N;
  }

  public void setRandomTargetPoint(Vector2f enemyPoint, int[][] map, int distance) {
    int u = (int) enemyPoint.getX();
    int v = (int) enemyPoint.getY();

    boolean[][] visted = new boolean[N + 1][N + 1];
    int[][] step = new int[N + 1][N + 1];
    Queue<Vector2f> queue = new LinkedList<>();
    ArrayList<Vector2f> ans = new ArrayList<>();

    queue.add(new Vector2f(u, v));
    visted[u][v] = true;
    step[u][v] = 0;

    while (!queue.isEmpty()) {
      Vector2f cell = queue.peek();
      int x = (int) cell.getX();
      int y = (int) cell.getY();
      int st = step[x][y];
      if (st == distance) {
        ans.add(new Vector2f(x, y));
      }
      queue.remove();
      if (st > distance) {
        continue;
      }
      for (int i = 0; i < 4; i++) {
        int adjx = x + dx[i];
        int adjy = y + dy[i];
        if (isIn(adjx, adjy) && !visted[adjx][adjy] && (map[adjx][adjy] == 0
            || map[adjx][adjy] == -1)) {
          queue.add(new Vector2f(adjx, adjy));
          visted[adjx][adjy] = true;
          step[adjx][adjy] = st + 1;
        }
      }
    }
    int randomInt = algorithms.RandomizeMap.randomInt((int) Math.sqrt(ans.size()));
    this.randomTargetPoint = ans.get(randomInt);
  }

  public static int nextMoveBase(Vector2f start, Vector2f finish ,int[][] map) {
    int returnAns = -1;
    int u = (int) start.getX();
    int v = (int) start.getY();

    boolean[][] visted = new boolean[N + 1][N + 1];
    int[][] direction = new int[N + 1][N + 1];
    Vector2f[][] parent = new Vector2f[N + 1][N + 1];
    Queue<Vector2f> queue = new LinkedList<>();
    ArrayList<Integer> ans = new ArrayList<>();

    queue.add(new Vector2f(u, v));
    visted[u][v] = true;
    parent[u][v] = new Vector2f(u, v);
    direction[u][v] = -1;

    while (!queue.isEmpty()) {
      Vector2f cell = queue.peek();
      int x = (int) cell.getX();
      int y = (int) cell.getY();

      if(x == (int) finish.getX() && y == (int) finish.getY()) {
        Vector2f par = parent[x][y];
        while(x != (int) par.getX() || y != (int) par.getY()) {
          if(direction[x][y] != -1) returnAns = direction[x][y];
          x = (int) par.getX();
          y = (int) par.getY();
          par = parent[x][y];
        }
        return returnAns;
      }
      queue.remove();
      for (int i = 0; i < 4; i++) {
        int adjx = x + dx[i];
        int adjy = y + dy[i];
        if (isIn(adjx, adjy) && !visted[adjx][adjy]
            && (map[adjx][adjy] == 0 || map[adjx][adjy] == -1)) {
          queue.add(new Vector2f(adjx, adjy));
          visted[adjx][adjy] = true;
          parent[adjx][adjy] = new Vector2f(x, y);
          direction[adjx][adjy] = i;
        }
      }
    }
    return returnAns;
  }

  public static void main(String[] args) {
    RandomizeMap demo = new RandomizeMap(50, 50);
    int[][] dem = demo.getRandomizeMap();
    demo.showBoard();
    ArrayList<Vector2f> de = algorithms.player.SpawnPlayer.spawn(dem, 1, 0);
    int ans = nextMoveBase(de.get(0), new Vector2f(0, 0), dem);
    System.out.print(ans);
  }
}
