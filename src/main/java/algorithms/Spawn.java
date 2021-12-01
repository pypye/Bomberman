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
    private Vector2f mainPlayer;
    private ArrayList<Vector2f> enemy1 = new ArrayList<>();
    private ArrayList<Vector2f> enemy2 = new ArrayList<>();
    private ArrayList<Vector2f> enemy3 = new ArrayList<>();

    public Spawn(int level) {
        this.level = level;
        try {
            String url = "assets/Level/level";
            insertFromFile(url + level + ".txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void insertFromFile(String url) throws FileNotFoundException {
        mainPlayer = null;
        enemy1.clear();
        enemy2.clear();
        enemy3.clear();
//    if(level > 1000) {
//      RandomizeMap randomizeMap = new RandomizeMap(50, 100);
//      spawnMap =  randomizeMap.getRandomizeMap();
//      enemy1 = SpawnPlayer.spawn(spawnMap, 10, 3);
//      enemy2 = SpawnPlayer.spawn(spawnMap, 10, 3);
//      enemy3 = SpawnPlayer.spawn(spawnMap, 10, 3);
//    }

        FileInputStream fileInputStream = new FileInputStream(url);
        Scanner sc = new Scanner(fileInputStream);
        String Input = sc.nextLine();

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
                if (here == 'p') {
                    mainPlayer = new Vector2f(r, i);
                    ok = true;
                }
                if (here == '1') {
                    enemy1.add(new Vector2f(r, i));
                    ok = true;
                }
                if (here == '2') {
                    enemy2.add(new Vector2f(r, i));
                    ok = true;
                }
                if (here == '3') {
                    enemy3.add(new Vector2f(r, i));
                    ok = true;
                }
                if (!ok) spawnMap[r][i] = 0;
            }
            r++;
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

    public Vector2f getMainPlayer() {
        return mainPlayer;
    }

    public void setMainPlayer(Vector2f mainPlayer) {
        this.mainPlayer = mainPlayer;
    }

    public ArrayList<Vector2f> getEnemy1() {
        return enemy1;
    }

    public void setEnemy1(ArrayList<Vector2f> enemy1) {
        this.enemy1 = enemy1;
    }

    public ArrayList<Vector2f> getEnemy2() {
        return enemy2;
    }

    public void setEnemy2(ArrayList<Vector2f> enemy2) {
        this.enemy2 = enemy2;
    }

    public ArrayList<Vector2f> getEnemy3() {
        return enemy3;
    }

    public void setEnemy3(ArrayList<Vector2f> enemy3) {
        this.enemy3 = enemy3;
    }

    public static void main(String[] agrs) throws FileNotFoundException {
        Spawn demo = new Spawn(1);
        int a = 2;
    }
}
