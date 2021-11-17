package map;

public class RandomMap {

  public static final int N = 20; //size of Maze

  public static int radomInt(int limit) {
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
  public static int[][] radomMaze(int nContainer, int nRock) {
    int[] maze1D = new int[N * N + 5];
    int[][] maze2D = new int[N + 1][N + 1];
    int components = 0; //
    int block = 0; //so luong khoi hien tai
    UF uf;

    for (int i = 0; i <= N * N; i++) {
      maze1D[i] = 0;
    }

    int limit = nContainer + nRock;

    while (block < limit) {
      uf = new UF(N * N + 1);
      block++;
      int radomInt = radomInt(N);
      if (maze1D[radomInt] == 1) {
        block--;
        continue;
      }
      int U = ((radomInt - 1) / N) + 1;
      int V = ((radomInt - 1) % N) + 1;
      maze1D[radomInt] = 1;
      maze2D[U][V] = 1;
      for (int i = 1; i <= N * N; i++) {
        if (maze1D[i] == 1) {
          continue;
        }
        int u = ((i - 1) / N) + 1;
        int v = ((i - 1) % N) + 1;
        if (u > 1) {
          int p = twoForOne(u - 1, v);
          if (!(maze1D[p] == 1 || uf.find(i) == uf.find(p))) {
            uf.union(i, p);
          }
        }
        if (u < N) {
          int p = twoForOne(u + 1, v);
          if (!(maze1D[p] == 1 || uf.find(i) == uf.find(p))) {
            uf.union(i, p);
          }
        }
        if (v > 1) {

          int p = twoForOne(u, v - 1);
          if (!(maze1D[p] == 1 || uf.find(i) == uf.find(p))) {
            uf.union(i, p);
          }
        }
        if (v < N) {
          int p = twoForOne(u, v + 1);
          if (!(maze1D[p] == 1 || uf.find(i) == uf.find(p))) {
            uf.union(i, p);
          }
        }
      }
      components = uf.count();
      if (components - block > 2) {
        maze1D[radomInt] = 0;
        maze2D[U][V] = 0;
        block--;
      }
    }
    block = 0;
    while (block < nRock) {
      int radomInt = radomInt(N);
      if (maze1D[radomInt] == 1) {
        block++;
        int U = ((radomInt - 1) / N) + 1;
        int V = ((radomInt - 1) % N) + 1;
        maze1D[radomInt] = 2;
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
