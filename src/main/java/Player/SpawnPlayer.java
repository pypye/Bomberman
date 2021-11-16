package Player;

import java.util.ArrayList;
import map.RandomMap;

public class SpawnPlayer extends Pair{

  private static ArrayList<Pair> playerList = new ArrayList<>();

  public static int distance(int u, int v, int U, int V) {
    return (Math.abs(u - U) + Math.abs(v - V));
  }

  public static ArrayList<Pair> spawn(int numPlayer, int[][] map, int distancePlayer) {
    int cntPlayer = 0;
    while (cntPlayer < numPlayer) {
      boolean isNear = false;
      int radomInt = RandomMap.radomInt(N);
      int U = ((radomInt - 1) / N) + 1;
      int V = ((radomInt - 1) % N) + 1;
      if (map[U][V] == 0) {
        for (int i = 0; i < playerList.size(); i++) {
          int u = playerList.get(i).getX();
          int v = playerList.get(i).getY();
          if(distance(u,v,U,V) < distancePlayer) {
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
