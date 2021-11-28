package cores;

import algorithms.RandomizeMap;
import algorithms.SpawnPlayer;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import entities.*;
import entities.bombs.Bomb;
import entities.bombs.BombList;
import entities.players.MainPlayer;
import entities.players.Player;
import entities.players.enemies.Spider;
import entities.players.enemies.Turtle;
import entities.terrains.*;
import entities.buffs.BombExtendItem;
import entities.buffs.FlameItem;
import entities.buffs.ShieldItem;
import entities.buffs.SpeedItem;

import java.util.ArrayList;
import java.util.Random;

public class Map {
    public static final int SIZE = 20;
    private static final int[][] object = new int[SIZE][SIZE];
    private static final Entity[][] entity = new Entity[SIZE][SIZE];

    public static final int GRASS = 0;
    public static final int ROCK = 1;
    public static final int CONTAINER = 2;
    public static final int BOMB = 3;
    public static final int PORTAL = 4;
    public static final int SPEED_ITEM = 5;
    public static final int FLAME_ITEM = 6;
    public static final int BOMB_EX_ITEM = 7;
    public static final int SHIELD_ITEM = 8;

    public static void init() {
        int[][] map = new RandomizeMap(50, 100).getRandomizeMap();
        System.out.println("[Debug/Map] Init Terrain");
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 20; j++) {
                new Grass(new Vector3f(i * 2f, 0f, j * 2f));
                setObject(i, j, map[i][j], null);
            }
        }

        ArrayList<Vector2f> player = SpawnPlayer.spawn(map, 10, 3);
        System.out.println("[Debug/Map] Init player, with size = " + player.size());
        new MainPlayer(new Vector3f(player.get(0).x * 2f, 1, player.get(0).y * 2f));
        for (int i = 1; i < player.size(); ++i) {
            double r = Math.random();
            if (r >= 0.3) {
                new Turtle(new Vector3f(player.get(i).x * 2f, 1, player.get(i).y * 2f));
            } else {
                new Spider(new Vector3f(player.get(i).x * 2f, 1, player.get(i).y * 2f));
            }
        }
        System.out.println("[Debug/Map] Init map complete");
    }

    public static int[][] getMap() {
        return object;
    }

    public static void setObject(int x, int z, int value, Player owner) {
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
                if (r.nextBoolean()) entity[x][z] = new Rock(new Vector3f(x * 2f, 2f, z * 2f));
                else entity[x][z] = new Tree(new Vector3f(x * 2f, 1f, z * 2f));
                break;
            case BOMB:
                entity[x][z] = new Bomb(new Vector3f(x * 2f, 1f, z * 2f), owner, System.currentTimeMillis());
                BombList.add((Bomb) entity[x][z]);
                break;
            case PORTAL:
                entity[x][z] = new Portal(new Vector3f(x * 2f, 1f, z * 2f));
                break;
            case SPEED_ITEM:
                entity[x][z] = new SpeedItem(new Vector3f(x * 2f, 1.5f, z * 2f));
                break;
            case FLAME_ITEM:
                entity[x][z] = new FlameItem(new Vector3f(x * 2f, 1.5f, z * 2f));
                break;
            case BOMB_EX_ITEM:
                entity[x][z] = new BombExtendItem(new Vector3f(x * 2f, 1.5f, z * 2f));
                break;
            case SHIELD_ITEM:
                entity[x][z] = new ShieldItem(new Vector3f(x * 2f, 1.5f, z * 2f));
                break;
            default:
                object[x][z] = 0;
                break;
        }
    }

    public static int getObject(int x, int y) {
        return object[x][y];
    }

    public static void setBlocked(int x, int y, boolean value) {
        if (entity[x][y] != null) {
            entity[x][y].setBlocked(value);
        }
    }

    public static boolean isBlocked(int x, int y) {
        return entity[x][y] != null && entity[x][y].isBlocked();
    }

    public static boolean isInside(int x, int y) {
        return x >= 0 && x < SIZE && y >= 0 && y < SIZE;
    }

    public static Entity getEntity(int x, int y) {
        return entity[x][y];
    }

    public static void hide() {
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 20; j++) {
                if (entity[i][j] != null) {
                    entity[i][j].remove();
                    entity[i][j] = null;
                }
                object[i][j] = 0;
            }
        }
    }
}
