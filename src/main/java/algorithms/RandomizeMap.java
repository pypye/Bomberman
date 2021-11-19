package algorithms;

import algorithms.player.Pair;

public class RandomizeMap {

  public static final int N = 20; //size of Maze

  public static int randomInt(int limit) {
    double randomDouble = Math.random();
    randomDouble = randomDouble * limit * limit;
    return (int) randomDouble;
  }

  public static int twoForOne(int u, int v) {
    return (u * N) + v + 1;
  }

  public static boolean cntEmpty(int u, int v, int[][] map, int limit) {
    int cnt = 0;
    for (int i = 0; i < 8; i++) {
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

  public static int[][] randomMaze(int nContainer, int nRock) {
    int[] maze1D = new int[N * N + 1];
    int[][] maze2D = new int[N][N];
    int components = 0;
    int block = 0;
    UnionFind unionFind;

    for (int i = 0; i < N * N; i++) {
      maze1D[i] = 0;
    }

    while (block < nRock) {
      unionFind = new UnionFind(N * N + 1);
      block++;
      int randomInt = randomInt(N) + 1;
      if (maze1D[randomInt] == 2) {
        block--;
        continue;
      }
      int U = ((randomInt - 1) / N);
      int V = ((randomInt - 1) % N);
      maze1D[randomInt] = 2;
      maze2D[U][V] = 2;
      for (int i = 1; i <= N * N; i++) {
        if (maze1D[i] == 2) {
          continue;
        }
        int u = ((i - 1) / N);
        int v = ((i - 1) % N);
        if (u > 0) {
          int p = twoForOne(u - 1, v);
          if (!(maze1D[p] == 2 || unionFind.find(i) == unionFind.find(p))) {
            unionFind.union(i, p);
          }
        }
        if (u < N - 1) {
          int p = twoForOne(u + 1, v);
          if (!(maze1D[p] == 2 || unionFind.find(i) == unionFind.find(p))) {
            unionFind.union(i, p);
          }
        }
        if (v > 0) {
          int p = twoForOne(u, v - 1);
          if (!(maze1D[p] == 2 || unionFind.find(i) == unionFind.find(p))) {
            unionFind.union(i, p);
          }
        }
        if (v < N - 1) {
          int p = twoForOne(u, v + 1);
          if (!(maze1D[p] == 2 || unionFind.find(i) == unionFind.find(p))) {
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
    while (block < nContainer) {
      int randomInt = randomInt(N) + 1;
      int U = ((randomInt - 1) / N);
      int V = ((randomInt - 1) % N);
      if (maze1D[randomInt] == 0) {
        block++;
        maze1D[randomInt] = 1;
        maze2D[U][V] = 1;
      }
    }
    return maze2D;
  }

  public static void show(int[][] arr) {
    for (int i = 0; i < N; i++) {
      for (int j = 0; j < N; j++) {
        System.out.print(arr[i][j] + " ");
      }
      System.out.println();
    }
  }

  public static void main(String[] args) {
    int[][] demo = randomMaze(0, 10);
    show(demo);
  }

}
