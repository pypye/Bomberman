package algorithms;

import com.jme3.math.Vector2f;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Spawn {

  public static final char[] Tiles = new char[]{'#', '*', '~', 'x'};
  public static final char[] Character = new char[]{'3', '2', '1', 'p'};
  public static final char[] Items = new char[]{'s', 'f', 'b'};

  private int level;
  private int[][] spawnMap;
  private ArrayList<Vector2f> spawnPlayer = new ArrayList<>();
  private static String url = "assets/Level/level";

  public Spawn(int level) {
    this.level = level;
    url = url + level + ".txt";
  }

  private void insertFromFile() throws FileNotFoundException {
    if(level > 1000) {
      RandomizeMap randomizeMap = new RandomizeMap(50, 100);
      spawnMap =  randomizeMap.getRandomizeMap();
      spawnPlayer = SpawnPlayer.spawn(spawnMap, 30, 3);
    }

    FileInputStream fileInputStream = new FileInputStream(url);
    Scanner sc = new Scanner(fileInputStream);
    String Input = sc.nextLine();

    ArrayList<Vector2f> enemy1 = new ArrayList<>();
    ArrayList<Vector2f> enemy2 = new ArrayList<>();
    ArrayList<Vector2f> enemy3 = new ArrayList<>();

    int level = Integer.parseInt(Input.split(" ")[0]);
    int R = Integer.parseInt(Input.split(" ")[1]);
    int C = Integer.parseInt(Input.split(" ")[2]);

    spawnMap = new int[R][C];
    int r = 0;
    while (sc.hasNextLine()) {
      Input = sc.nextLine();
      for (int i = 0; i < Input.length(); i++) {
        boolean ok = false;
        char here = Input.charAt(i);
        for (int Til = 0; Til < 4; Til++) {
          if (here == Tiles[Til]) {
            spawnMap[r][i] = Til + 1;
            ok = true;
          }
        }
        if(here == 'p') {
          spawnPlayer.add(new Vector2f(r, i));
          ok = true;
        }
        if(here == '1') {
          enemy1.add(new Vector2f(r, i));
          ok = true;
        }
        if(here == '2') {
          enemy2.add(new Vector2f(r, i));
          ok = true;
        }
        if(here == '3') {
          enemy3.add(new Vector2f(r, i));
          ok = true;
        }
        if (!ok) spawnMap[r][i] = 0;
      }
      r++;
    }
    spawnPlayer.addAll(enemy1);
    spawnPlayer.addAll(enemy2);
    spawnPlayer.addAll(enemy3);
  }

  public int getLevel() {
    return level;
  }

  public void setLevel(int level) {
    this.level = level;
  }

  public int[][] getSpawnMap() {
    return spawnMap;
  }

  public void setSpawnMap(int[][] spawnMap) {
    this.spawnMap = spawnMap;
  }

  public ArrayList<Vector2f> getSpawnPlayer() {
    return spawnPlayer;
  }

  public void setSpawnPlayer(ArrayList<Vector2f> spawnPlayer) {
    this.spawnPlayer = spawnPlayer;
  }

  public static void main(String[] agrs) throws FileNotFoundException {
    Spawn demo = new Spawn(1);
    demo.insertFromFile();
    int a = 2;
  }
}
