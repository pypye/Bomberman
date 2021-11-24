package algorithms.players;

import com.jme3.math.Vector2f;
import java.util.ArrayList;
import algorithms.RandomizeMap;

public class SpawnPlayer extends Pair {

  private static ArrayList<Vector2f> playerList = new ArrayList<>();

  public static int distance(int u, int v, int U, int V) {
    return (Math.abs(u - U) + Math.abs(v - V));
  }

  public static boolean cntEmpty(int u, int v, int[][] map, int limit) {
    int cnt = 0;
    for (int i = 0; i < 4; i++) {
      int U = u + Pair.dx[i];
      int V = v + Pair.dy[i];
      if (U < 0 || V < 0 || U >= N || V >= N) {
        continue;
      }
      if (map[U][V] == 0) {
        cnt++;
      }
    }
    return cnt >= limit;
  }

  public static ArrayList<Vector2f> spawn(int[][] map, int numPlayer, int distancePlayer) {
    int cntPlayer = 0;
    while (cntPlayer < numPlayer) {
      boolean isNear = false;
      int randomInt = RandomizeMap.randomInt(N) + 1;
      int U = ((randomInt - 1) / N);
      int V = ((randomInt - 1) % N);
      if (map[U][V] == 0 && cntEmpty(U, V, map, 3)) {
        for (Vector2f vector2f : playerList) {
          int u = (int) vector2f.getX();
          int v = (int) vector2f.getY();
          if (distance(u, v, U, V) < distancePlayer) {
            isNear = true;
          }
        }
        if (isNear) {
          continue;
        }
        cntPlayer++;
        playerList.add(new Vector2f(U, V));
      }
    }
    return playerList;
  }

  public static void main(String[] args) {
    RandomizeMap randomizeMap = new RandomizeMap(5, 10);
    int[][] demo = randomizeMap.getRandomizeMap();
    //RandomizeMap.show(demo);
    ArrayList<Vector2f> he = spawn(demo, 4, 2);
    int a = 1;
  }
}
