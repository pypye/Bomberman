package algorithms;

import com.jme3.math.Vector2f;
import java.util.ArrayList;

public class Spawn {

  private int level;
  private int[][] spawnMap;
  private ArrayList<Vector2f> spawnPlayer;
  private static String url = "assets/Level/level";

  public Spawn(int level) {
    this.level = level;
    url = url + level + ".txt";
  }

  private void insertFromFile() {
    if(level > 1000) {
      RandomizeMap randomizeMap = new RandomizeMap(50, 100);
      spawnMap =  randomizeMap.getRandomizeMap();
      spawnPlayer = SpawnPlayer.spawn(spawnMap, 30, 3);
    }
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
}
