package cores;

import com.jme3.math.Vector3f;
import entities.*;
import entities.bombs.Bomb;
import entities.bombs.BombList;

import java.util.Random;

public class Map {
    private static final int[][] object = new int[20][20];
    private static final Entity[][] entity = new Entity[20][20];

    public static final int GRASS = 0;
    public static final int ROCK = 1;
    public static final int CONTAINER = 2;
    public static final int BOMB = 3;
    public static final int PORTAL = 4;

    public static void init() {
        Random r = new Random();
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 20; j++) {
                new Grass(new Vector3f(i * 2f, 0f, j * 2f));
                setObject(i, j, r.nextInt(3));
            }
        }
        setObject(0, 0, GRASS);
        setObject(0, 1, GRASS);
        setObject(1, 0, GRASS);
        setObject(1, 1, GRASS);
    }

    public static void setObject(int x, int z, int value) {
        object[x][z] = value;
        if (entity[x][z] != null) {
            entity[x][z].remove();
            entity[x][z] = null;
        }
        switch (value) {
            case CONTAINER:
                entity[x][z] = new Container(new Vector3f(x * 2f, 1f, z * 2f));
                break;
            case ROCK:
                Random r = new Random();
                if(r.nextBoolean()) entity[x][z] = new Rock(new Vector3f(x * 2f, 2f, z * 2f));
                else entity[x][z] = new Tree(new Vector3f(x * 2f, 1f, z * 2f));
                break;
            case BOMB:
                entity[x][z] = new Bomb(new Vector3f(x * 2f, 1f, z * 2f), System.currentTimeMillis());
                BombList.add((Bomb) entity[x][z]);
                break;
        }
    }

    public static int getObject(int x, int y) {
        return object[x][y];
    }

    public static void setBlocked(int x, int y, boolean value) {
        entity[x][y].setBlocked(value);
    }

    public static boolean isBlocked(int x, int y) {
        return entity[x][y] != null && entity[x][y].isBlocked();
    }
}
