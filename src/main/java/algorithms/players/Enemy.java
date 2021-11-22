package algorithms.players;

import java.util.ArrayList;

public class Enemy extends Pair {

  public static final int LEFT = 0;
  public static final int Right = 1;
  public static final int UP = 2;
  public static final int DOWN = 3;

  public static final int MUSHROOM = -2;
  public static final int ONEAL = -3;

  private int type;

  public Enemy() {
  }

  public Enemy(int x, int y, int type) {
    super(x, y, DOWN);
    this.type = type;
  }

  public Enemy(int x, int y, int direction, int type) {
    super(x, y, direction);
    this.type = type;
  }

  public static ArrayList<Enemy> enemyList = new ArrayList<>();

  public static boolean isOut(int u, int v) {
    return u >= 1 && v >= 1 && u <= N && v <= N;
  }

  public boolean compare(Enemy u) {
    return this.getX() == u.getX() && this.getY() == u.getY();
  }

  public static ArrayList<Enemy> nextMove(int[][] map, ArrayList<Pair> players) {
    ArrayList<Enemy> ans = new ArrayList<>();
    for (Enemy enemy : enemyList) {
      if (enemy.type == MUSHROOM) {
        Enemy next = Mushroom.nextM(enemy, map);
      } else if (enemy.type == ONEAL) {
        Enemy next = Oneal.nextM(enemy, map, players);
        ans.add(next);
      }
    }
    enemyList = ans;
    return enemyList;
  }
}
