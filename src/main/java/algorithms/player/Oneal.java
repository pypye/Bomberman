package algorithms.player;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import algorithms.RandomizeMap;
import com.jme3.math.Vector2f;

public class Oneal extends Enemy {

  public Oneal() {
  }

  /**
   * @return Enemy which is the next step of enemy
   */
//  public static Enemy isMove(Enemy enemy, int[][] map) {
//    int u = enemy.getX();
//    int v = enemy.getY();
//    boolean[][] visted = new boolean[N + 1][N + 1];
//    Enemy[][] parent = new Enemy[N + 1][N + 1];
//    Queue<Enemy> queue = new LinkedList<>();
//    ArrayList<Enemy> ans = new ArrayList<>();
//
//    queue.add(new Enemy(u, v, DOWN, ONEAL));
//    visted[u][v] = true;
//    parent[u][v] = new Enemy(u, v, DOWN, ONEAL);
//
//    while (!queue.isEmpty()) {
//      Enemy cell = queue.peek();
//      int x = cell.getX();
//      int y = cell.getY();
//      int d = cell.getDirection();
//      if (map[x][y] == 9) {
//        while (!parent[x][y].compare(new Enemy(x, y, d, ONEAL))) {
//          ans.add(new Enemy(x, y, d, ONEAL));
//          x = parent[x][y].getX();
//          y = parent[x][y].getY();
//        }
//        return ans.get(ans.size() - 1);
//      }
//      queue.remove();
//      for (int i = 0; i < 4; i++) {
//        int adjx = x + dx[i];
//        int adjy = y + dy[i];
//        if (isOut(adjx, adjy) && !visted[adjx][adjy] && (map[adjx][adjy] == 0
//            || map[adjx][adjy] == -1)) {
//          queue.add(new Enemy(adjx, adjy, i, ONEAL));
//          visted[adjx][adjy] = true;
//          parent[adjx][adjy] = new Enemy(x, y, i, ONEAL);
//        }
//      }
//    }
//    return null;
//  }

  /**
   * @param enemy   enemy
   * @param players is a list of algorithms.player
   * @return Enemy if cant move go random
   */
  public static Enemy nextM(Enemy enemy, int[][] map, ArrayList<Pair> players) {
    for (int i = 1; i < players.size(); i++) {
      int U = players.get(i).getX();
      int V = players.get(i).getY();
      map[U][V] = -1;
    }
    Enemy isMove = null;
        //isMove(enemy, map);
    int u = enemy.getX();
    int v = enemy.getY();
    if (isMove == null) {
      while (true) {
        int randomInt = RandomizeMap.randomInt(2) - 1;
        int U = u + dx[randomInt];
        int V = v + dy[randomInt];
        if (map[U][V] == 0) {
          return new Enemy(U, V, randomInt, ONEAL);
        }
      }
    }
    return isMove;
  }
}
