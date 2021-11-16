package Player;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import map.RandomMap;

public class Enemy extends Pair {

  public static boolean isOut(int u, int v) {
    return u >= 1 && v >= 1 && u <= N && v <= N;
  }

  /**
   * @return pair which is the next step of enemy
   */
  public static Pair isMove(Pair enemy, int[][] map) {
    int u = enemy.getX();
    int v = enemy.getY();
    boolean[][] visted = new boolean[N + 1][N + 1];
    Pair[][] parent = new Pair[N + 1][N + 1];
    Queue<Pair> queue = new LinkedList<>();
    ArrayList<Pair> ans = new ArrayList<>();

    queue.add(new Pair(u, v));
    visted[u][v] = true;
    parent[u][v] = new Pair(u, v);

    while (!queue.isEmpty()) {
      Pair cell = queue.peek();
      int x = cell.getX();
      int y = cell.getY();
      if (map[x][y] == 9) {
        while (!parent[x][y].compare(new Pair(x, y))) {
          ans.add(new Pair(x, y));
          x = parent[x][y].getX();
          y = parent[x][y].getY();
        }
        return ans.get(ans.size() - 1);
      }
      queue.remove();
      for (int i = 0; i < 4; i++) {
        int adjx = x + dx[i];
        int adjy = y + dy[i];
        if (isOut(adjx, adjy) && !visted[adjx][adjy] && (map[adjx][adjy] == 0 || map[adjx][adjy] == 9)) {
          queue.add(new Pair(adjx, adjy));
          visted[adjx][adjy] = true;
          parent[adjx][adjy] = new Pair(x, y);
        }
      }
    }
    return null;
  }

  /**
   * @param enemy enemy
   * @param players is a list of player
   * @return pair if cant move go random
   */
  public static Pair moveEnemy(Pair enemy, int[][] map, ArrayList<Pair> players) {
    for (int i = 1; i < players.size(); i++) {
      int U = players.get(i).getX();
      int V = players.get(i).getY();
      map[U][V] = 9;
    }
    Pair isMove = isMove(enemy,map);
    int u = enemy.getX();
    int v = enemy.getY();
    if(isMove == null) {
      while(true) {
        int randomInt = RandomMap.radomInt(2) - 1;
        int U = u + dx[randomInt];
        int V = v + dy[randomInt];
        if(map[U][V] == 0) return new Pair(U, V);
      }
    }
    return isMove;
  }
}
