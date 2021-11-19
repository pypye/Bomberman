package algorithms.player;

import java.util.ArrayList;
import algorithms.RandomizeMap;

public class SpawnPlayer extends Pair{

  private static ArrayList<Pair> playerList = new ArrayList<>();

  public static int distance(int u, int v, int U, int V) {
    return (Math.abs(u - U) + Math.abs(v - V));
  }
  //change ArrayList <Pair> to ArrayList <Vector2f>
  public static ArrayList<Pair> spawn(int numPlayer, int[][] map, int distancePlayer) {
    int cntPlayer = 0;
    while (cntPlayer < numPlayer) {
      boolean isNear = false;
      int randomInt = RandomizeMap.randomInt(N);
      int U = ((randomInt - 1) / N) + 1;
      int V = ((randomInt - 1) % N) + 1;
      if (map[U][V] == 0) {
        for (Pair pair : playerList) {
          int u = pair.getX();
          int v = pair.getY();
          if (distance(u, v, U, V) < distancePlayer) {
            isNear = true;
          }
        }
        if(isNear) continue;
        cntPlayer++;
        playerList.add(new Pair(U,V));
      }
    }
    return playerList;
  }
}
