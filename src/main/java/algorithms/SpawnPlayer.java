package algorithms;

import com.jme3.math.Vector2f;

import java.util.ArrayList;

import cores.Map;

public class SpawnPlayer {
    private static final int[] dx = {0, 0, -1, 1, -1, -1, 1, 1};
    private static final int[] dy = {-1, 1, 0, 0, -1, 1, -1, 1};
    private static final ArrayList<Vector2f> playerList = new ArrayList<>();

    private static int distance(int u, int v, int U, int V) {
        return (Math.abs(u - U) + Math.abs(v - V));
    }

    private static boolean isEmpty(int u, int v, int[][] map) {
        int cnt = 0;
        for (int i = 0; i < 4; i++) {
            int U = u + dx[i];
            int V = v + dy[i];
            if (U < 0 || V < 0 || U >= Map.SIZE || V >= Map.SIZE) continue;
            if (map[U][V] == 0) cnt++;
        }
        return cnt >= 3;
    }

    public static ArrayList<Vector2f> spawn(int[][] map, int num, int dist) {
        while (true) {
            ArrayList<Vector2f> result = preSpawn(map, num, dist);
            if (result != null) return result;
        }
    }

    public static ArrayList<Vector2f> preSpawn(int[][] map, int num, int dist) {
        playerList.clear();
        int cnt = 0;
        int nCnt = 0;
        while (cnt < num) {
            boolean isNear = false;
            int randomInt = RandomizeMap.randomInt(Map.SIZE) + 1;
            int x = ((randomInt - 1) / Map.SIZE);
            int y = ((randomInt - 1) % Map.SIZE);
            nCnt++;
            if (nCnt == 1000000) return null;
            if (map[x][y] == 0 && isEmpty(x, y, map)) {
                for (Vector2f player : playerList) {
                    int u = (int) player.x;
                    int v = (int) player.y;
                    if (distance(u, v, x, y) < dist) isNear = true;
                }
                if (isNear) continue;
                cnt++;
                playerList.add(new Vector2f(x, y));
            }
        }
        return playerList;
    }

    public static void main(String[] args) {
        RandomizeMap randomizeMap = new RandomizeMap(5, 10);
        int[][] demo = randomizeMap.getRandomizeMap();
        ArrayList<Vector2f> he = spawn(demo, 50, 2);
        int a = 1;
    }
}
