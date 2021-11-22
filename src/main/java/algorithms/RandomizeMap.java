package algorithms;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class RandomizeMap {
  public static final int BOMBER = -1;
  public static final int MUSHROOM = -2;
  public static final int ONEAL = -3;
  public static final int N = 20; //size of Maze

  private static int level;

  public static int randomInt(int limit) {
    double randomDouble = Math.random();
    randomDouble = randomDouble * limit * limit;
    return (int) randomDouble;
  }

  public static int twoForOne(int u, int v) {
    return (u * N) + v + 1;
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

  public static int toInt(String input) {
    int output = 0;
    for(int i = 0; i < input.length(); i++) {
      output = output * 10 + (int) (input.charAt(i) - '0');
    }
    return output;
  }

  public static int[][] insertFromFile() throws FileNotFoundException {
    char[] Tiles = new char[] {'#', '*', '~', 'x'};
    char[] Character = new char[] {'2', '1', 'p'};
    char[] Items = new char[] {'s', 'f', 'b'};
    String url = "src\\main\\java\\cores\\input.txt";
    FileInputStream fileInputStream = new FileInputStream(url);
    Scanner sc = new Scanner(fileInputStream);
    String Input = sc.nextLine();

    level = toInt(Input.split(" ")[0]) ;
    int R = toInt(Input.split(" ")[1]) ;
    int C = toInt(Input.split(" ")[2]) ;

    int[][] map = new int[R][C];
    int r = 0;
    while (sc.hasNextLine()) {
      Input = sc.nextLine();
      for(int i = 0; i < Input.length(); i++) {
        boolean ok = false;
        char here = Input.charAt(i);
        for(int Til = 0; Til < 4; Til++) {
          if(here == Tiles[Til]) {
            map[r][i] = Til + 1;
            ok = true;
          }
        }
        for(int Char = 0; Char < 3; Char++) {
          if(here == Character[Char]) {
            map[r][i] = Char - 3;
            ok = true;
          }
        }
        for(int Ite = 0; Ite < 3; Ite++) {
          if(here == Items[Ite]) {
            map[r][i] = Ite + 5;
            ok = true;
          }
        }
        if(!ok) map[r][i] = 0;
      }
      r++;
    }
    return map;
  }
  public static void main(String[] args) throws FileNotFoundException {
//    int[][] demo = randomMaze(0, 10);
//    show(demo);
    int[][] demo = insertFromFile();
    int t = 3;
  }
}
