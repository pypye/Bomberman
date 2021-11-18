package player;

import map.RandomMap;

public class Mushroom extends Enemy {

  public static Enemy nextM(Enemy enemy, int[][] map) {
    int u = enemy.getX();
    int v = enemy.getY();
    while (true) {
      int randomInt = RandomMap.radomInt(4) - 1;
      int U = u + dx[randomInt];
      int V = v + dy[randomInt];
      if (map[U][V] == 0) {
        return new Enemy(U, V, randomInt, MUSHROOM);
      }
    }
  }
}
