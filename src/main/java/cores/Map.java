package cores;

import com.jme3.math.Vector3f;
import entities.*;
import entities.bombs.Bomb;
import entities.bombs.BombList;

import java.util.Random;

public class Map {
    private static final int[][] object = new int[20][20]; // wall, bomb, everything ...
    private static final boolean[][] block = new boolean[20][20]; // player can move or not
    private static final Entity[][] entity = new Entity[20][20]; // entity on board

    public static final boolean BLOCKED = false;
    public static final boolean NOT_BLOCKED = true;
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
                setInitStateBlock(i, j, getObject(i, j));
            }
        }
        setObject(0, 0, GRASS);
        setObject(0, 1, GRASS);
        setObject(1, 0, GRASS);
        setObject(1, 1, GRASS);
        setBlock(0, 0, NOT_BLOCKED);
        setBlock(0, 1, NOT_BLOCKED);
        setBlock(1, 0, NOT_BLOCKED);
        setBlock(1, 1, NOT_BLOCKED);
    }

    public static void setObject(int x, int z, int value) {
        object[x][z] = value;
        if (entity[x][z] != null) {
            entity[x][z].remove();
            entity[x][z] = null;
            setBlock(x, z, NOT_BLOCKED);
        }
        switch (value) {
            case CONTAINER:
                entity[x][z] = new Container(new Vector3f(x * 2f, 1f, z * 2f));
                break;
            case ROCK:
                entity[x][z] = new Rock(new Vector3f(x * 2f, 2f, z * 2f));
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

    public static void setInitStateBlock(int x, int y, int value) {
        boolean state = NOT_BLOCKED;
        switch (value) {
            case GRASS:
            case BOMB:
                break;
            default:
                state = BLOCKED;
                break;
        }
        block[x][y] = state;
    }

    public static void setBlock(int x, int y, boolean value) {
        block[x][y] = value;
    }

    public static boolean isBlock(int x, int y) {
        return block[x][y] == BLOCKED;
    }
}
