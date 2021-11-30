package algorithms;

import cores.Map;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;

public class RandomizeMap {
    private final int numContainer;
    private final int numRock;
    private int[][] randomizeMap;

    public RandomizeMap(int numContainer, int numRock) {
        this.numContainer = numContainer;
        this.numRock = numRock;
        randomizeMap = new int[Map.SIZE][Map.SIZE];
        randomizeMap = randomMaze();
    }

    public static int randomInt(int limit) {
        double randomDouble = Math.random();
        randomDouble = randomDouble * limit * limit;
        return (int) randomDouble;
    }

    private static int twoForOne(int u, int v) {
        return (u * Map.SIZE) + v + 1;
    }

    private int[][] randomMaze() {
        int[] maze1D = new int[Map.SIZE * Map.SIZE + 1];
        int[][] maze2D = new int[Map.SIZE][Map.SIZE];
        int components;
        int block = 0;
        for (int i = 0; i < Map.SIZE * Map.SIZE; i++) maze1D[i] = 0;
        while (block < numRock) {
            UnionFind unionFind = new UnionFind(Map.SIZE * Map.SIZE + 1);
            block++;
            int randomInt = randomInt(Map.SIZE) + 1;
            if (maze1D[randomInt] == 2) {
                block--;
                continue;
            }
            int x = (randomInt - 1) / Map.SIZE;
            int y = (randomInt - 1) % Map.SIZE;
            maze1D[randomInt] = 2;
            maze2D[x][y] = 2;
            for (int i = 1; i <= Map.SIZE * Map.SIZE; i++) {
                if (maze1D[i] == 2) continue;
                int u = (i - 1) / Map.SIZE;
                int v = (i - 1) % Map.SIZE;
                if (u > 0) {
                    int p = twoForOne(u - 1, v);
                    if (!(maze1D[p] == 2 || unionFind.find(i) == unionFind.find(p))) unionFind.union(i, p);
                }
                if (u < Map.SIZE - 1) {
                    int p = twoForOne(u + 1, v);
                    if (!(maze1D[p] == 2 || unionFind.find(i) == unionFind.find(p))) unionFind.union(i, p);
                }
                if (v > 0) {
                    int p = twoForOne(u, v - 1);
                    if (!(maze1D[p] == 2 || unionFind.find(i) == unionFind.find(p))) unionFind.union(i, p);
                }
                if (v < Map.SIZE - 1) {
                    int p = twoForOne(u, v + 1);
                    if (!(maze1D[p] == 2 || unionFind.find(i) == unionFind.find(p))) unionFind.union(i, p);
                }
            }
            components = unionFind.count();
            if (components - block > 2) {
                maze1D[randomInt] = 0;
                maze2D[x][y] = 0;
                block--;
            }
        }
        block = 0;
        while (block < numContainer) {
            int randomInt = randomInt(Map.SIZE) + 1;
            int x = (randomInt - 1) / Map.SIZE;
            int y = (randomInt - 1) % Map.SIZE;
            if (maze1D[randomInt] == 0) {
                block++;
                maze1D[randomInt] = 1;
                maze2D[x][y] = 1;
            }
        }

        for (int i = 0; i < Map.SIZE; i++) {
            for (int j = 0; j < Map.SIZE; j++) {
                getRandomizeMap()[i][j] = maze2D[i][j];
            }
        }
        return maze2D;
    }

    private void showBoard() {
        for (int i = 0; i < Map.SIZE; i++) {
            for (int j = 0; j < Map.SIZE; j++) {
                System.out.print(getRandomizeMap()[i][j] + " ");
            }
            System.out.println();
        }
    }

    public int getNumContainer() {
        return numContainer;
    }

    public int getNumRock() {
        return numRock;
    }

    public int[][] getRandomizeMap() {
        return randomizeMap;
    }

    public void setRandomizeMap(int[][] randomizeMap) {
        this.randomizeMap = randomizeMap;
    }

}
