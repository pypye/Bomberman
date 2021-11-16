package Player;

public class Pair {

  public static final int N = 20;
  public static final int[] dx = {0, 0, -1, 1};
  public static final int[] dy = {-1, 1, 0, 0};

  private int x;
  private int y;

  public Pair() {
  }

  public Pair(int x, int y) {
    this.x = x;
    this.y = y;
  }

  public int getX() {
    return x;
  }

  public void setX(int x) {
    this.x = x;
  }

  public int getY() {
    return y;
  }

  public void setY(int y) {
    this.y = y;
  }

  public boolean compare(Pair u) {
    return this.x == u.getX() && this.y == u.getY();
  }
}
