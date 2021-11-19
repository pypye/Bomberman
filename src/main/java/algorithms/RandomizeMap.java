package algorithms;

public class RandomizeMap {

  public static final int N = 20; //size of Maze

  public static int randomInt(int limit) {
    double randomDouble = Math.random();
    randomDouble = randomDouble * limit * limit + 1;
    return (int) randomDouble;
  }

  public static int twoForOne(int u, int v) {
    return ((u - 1) * N) + v;
  }

  /**
   * @param nContainer is limit number of container
   * @param nRock      is limit number of rock
   * @return 2D maze
   */
  public static int[][] randomMaze(int nContainer, int nRock) {
    int[] maze1D = new int[N * N + 5];
    int[][] maze2D = new int[N + 1][N + 1];
    int components = 0; //
    int block = 0; //so luong khoi hien tai
    UnionFind unionFind;

    for (int i = 0; i <= N * N; i++) {
      maze1D[i] = 0;
    }

    int limit = nContainer + nRock;

    while (block < limit) {
      unionFind = new UnionFind(N * N + 1);
      block++;
      int randomInt = randomInt(N);
      if (maze1D[randomInt] == 1) {
        block--;
        continue;
      }
      int U = ((randomInt - 1) / N) + 1;
      int V = ((randomInt - 1) % N) + 1;
      maze1D[randomInt] = 1;
      maze2D[U][V] = 1;
      for (int i = 1; i <= N * N; i++) {
        if (maze1D[i] == 1) {
          continue;
        }
        int u = ((i - 1) / N) + 1;
        int v = ((i - 1) % N) + 1;
        if (u > 1) {
          int p = twoForOne(u - 1, v);
          if (!(maze1D[p] == 1 || unionFind.find(i) == unionFind.find(p))) {
            unionFind.union(i, p);
          }
        }
        if (u < N) {
          int p = twoForOne(u + 1, v);
          if (!(maze1D[p] == 1 || unionFind.find(i) == unionFind.find(p))) {
            unionFind.union(i, p);
          }
        }
        if (v > 1) {

          int p = twoForOne(u, v - 1);
          if (!(maze1D[p] == 1 || unionFind.find(i) == unionFind.find(p))) {
            unionFind.union(i, p);
          }
        }
        if (v < N) {
          int p = twoForOne(u, v + 1);
          if (!(maze1D[p] == 1 || unionFind.find(i) == unionFind.find(p))) {
            unionFind.union(i, p);
          }
        }
      }
      components = unionFind.count();
      if (components - block > 2) {
        maze1D[randomInt] = 0;
        maze2D[U][V] = 0;
        block--;
      }
    }
    block = 0;
    while (block < nRock) {
      int randomInt = randomInt(N);
      if (maze1D[randomInt] == 1) {
        block++;
        int U = ((randomInt - 1) / N) + 1;
        int V = ((randomInt - 1) % N) + 1;
        maze1D[randomInt] = 2;
        maze2D[U][V] = 2;
      }
    }
    return maze2D;
  }

  public static void show(int[][] arr) {
    for (int i = 1; i <= N; i++) {
      for (int j = 1; j <= N; j++) {
        System.out.print(arr[i][j] + " ");
      }
      System.out.println();
    }
  }

}
