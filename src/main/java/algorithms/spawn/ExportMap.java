package algorithms.spawn;

import com.jme3.math.Vector2f;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class ExportMap {
    private final int level;
    private final int row;
    private final int col;

    private final int[] enemyTotal = new int[]{4, 6, 7, 8, 9, 11, 21};
    private final int[] enemy1 = new int[]{3, 3, 3, 3, 2, 3, 5};
    private final int[] enemy2 = new int[]{0, 1, 2, 3, 4, 5, 5};
    private final int[] enemy3 = new int[]{0, 1, 1, 1, 2, 2, 10};

    public ExportMap(int level, int row, int col) {
        this.level = level;
        this.row = row;
        this.col = col;
    }

    private int numOfEnemy(int level) {
        if (level < 5) return level - 1;
        if (level < 10) return 4;
        if (level < 100) return 5;
        if (level <= 1000) return 6;
        return -1;
    }

    private void mapExportToFile(int level, char[][] input) throws IOException {
        String url = "assets/Level/level" + level + ".txt";
        File file = new File(url);
        FileOutputStream outputStream = new FileOutputStream(file);
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
        StringBuilder here = new StringBuilder(level + " " + this.row + " " + this.col + "\n");
        outputStreamWriter.write(here.toString());
        for (int i = 0; i < this.row; i++) {
            if (i != 0) outputStreamWriter.write("\n");
            here = new StringBuilder();
            for (int j = 0; j < this.col; j++) {
                here.append(input[i][j]);
            }
            outputStreamWriter.write(here.toString());
        }
        outputStreamWriter.flush();
    }

    private char[][] arrayIntToChar(int level, int[][] map, ArrayList<Vector2f> player) {
        int number = 1;
        char[][] ans = new char[row][col];
        for (int i = 0; i < row; i++)
            for (int j = 0; j < col; j++) ans[i][j] = ' ';
        ans[(int) player.get(0).getX()][(int) player.get(0).getY()] = 'p';
        for (int i = number; i < number + enemy1[level]; i++) {
            int x = (int) player.get(i).getX();
            int y = (int) player.get(i).getY();
            ans[x][y] = '1';
        }

        number += enemy1[level];
        for (int i = number; i < number + enemy2[level]; i++) {
            int x = (int) player.get(i).getX();
            int y = (int) player.get(i).getY();
            ans[x][y] = '2';
        }

        number += enemy2[level];
        for (int i = number; i < number + enemy3[level]; i++) {
            int x = (int) player.get(i).getX();
            int y = (int) player.get(i).getY();
            ans[x][y] = '3';
        }

        for (int i = 0; i < row; i++)
            for (int j = 0; j < col; j++) {
                if (map[i][j] == 1) ans[i][j] = '#';
                if (map[i][j] == 2) ans[i][j] = '*';
            }
        return ans;
    }

    public void exportAllLevel() throws IOException {
        int hereLevel = 0;
        while (hereLevel < level) {
            hereLevel++;
            int cntEnemy = numOfEnemy(hereLevel);

            int[][] spawnMap = new RandomizeMap(50, 100).getRandomizeMap();
            ArrayList<Vector2f> spawnPlayer = SpawnPlayer.spawn(spawnMap, enemyTotal[cntEnemy], 3);

            mapExportToFile(hereLevel, arrayIntToChar(cntEnemy, spawnMap, spawnPlayer));
        }
    }

    public static void main(String[] args) throws IOException {
        ExportMap clone = new ExportMap(1000, 20, 20);
        clone.exportAllLevel();
    }
}
