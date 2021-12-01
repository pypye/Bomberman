package algorithms.bot;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;


public class BFS {
    private final int[][] grid;
    private final int[][] visited;
    private final int length;
    private final int[] dx = {-1, 0, 1, 0};
    private final int[] dy = {0, 1, 0, -1};
    private final int[][] path;
    private List<Pair> bombList;
    private List<Pair> itemList;
    private List<Pair> enemyList;
    private List<Pair> exitList;
    private List<Pair> containerList;
    private Pair[][] trace;
    private static final int MAX_PATH = 1000000000;

    public BFS(int[][] grid, int length) {
        this.length = length;
        this.grid = new int[length][length];
        visited = new int[length][length];
        path = new int[length][length];
        updateMap(grid);
    }

    /**
     * Check if the position is not out of range.
     *
     * @param u position of X.
     * @param v position of Y.
     * @return boolean.
     */
    public boolean checkRange(int u, int v) {
        return u >= 0 && u < length && v >= 0 && v < length;
    }


    public boolean isVisited(int u, int v) {
        return visited[u][v] == 1;
    }

    /**
     * Check if the position is safe. Not bomb surround, not out of range.
     *
     * @param u position of X.
     * @param v position of Y.
     * @return boolean.
     */
    public boolean checkSafePosition(int u, int v) {
        if (!checkRange(u, v)) {
            return false;
        }
        for (Pair bomb : bombList) {
            if (bomb.getX() == u) {
                if (Math.abs(bomb.getY() - v) <= 2) {
                    return false;
                }
            }
            if (bomb.getY() == v) {
                if (Math.abs(bomb.getX() - u) <= 2) {
                    return false;
                }
            }
        }
        return true;
    }

    public void bfs(int x, int y) {
        int[][] map = grid;
        updateMap(map);
        Queue<Pair> queue = new LinkedList<>();

        visited[x][y] = 1;
        path[x][y] = 0;
        queue.add(new Pair(x, y));
        addAttribute(x, y, map);

        while (!queue.isEmpty()) {
            Pair pair = queue.poll();
            int u = pair.getX();
            int v = pair.getY();
            visited[u][v] = 1;

            for (int i = 0; i < 4; i++) {
                int newX = u + dx[i];
                int newY = v + dy[i];
                if(checkRange(newX, newY)) {
                    addAttribute(newX, newY, map);
                }
                if (checkSafePosition(newX, newY)) {
                    if (!isVisited(newX, newY) && map[newX][newY] != 3 && map[newX][newY] != 1 && map[newX][newY] != 2) {
                        path[newX][newY] = path[u][v] + 1;
                        queue.add(new Pair(newX, newY));
                        trace[newX][newY] = new Pair(u, v);
                        visited[newX][newY] = 1;
                    }
                }
            }
        }
    }

    private void addAttribute(int newX, int newY, int[][] map) {
        if (map[newX][newY] == 2) {
            containerList.add(new Pair(newX, newY));
        } else if (map[newX][newY] == 3) {
            bombList.add(new Pair(newX, newY));
        } else if (map[newX][newY] == 4) {
            exitList.add(new Pair(newX, newY));
        } else if (map[newX][newY] > 4 && map[newX][newY] < 9) {
            itemList.add(new Pair(newX, newY));
        } else if (map[newX][newY] == 9) {
            enemyList.add(new Pair(newX, newY));
        }
    }
    public void showPath() {
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 20; j++) {
                int log = 1;
                if (path[i][j] != 0) {
                    log = (int) Math.log10(path[i][j]) + 1;
                }
                for (int k = 0; k < 10 - log; k++) {
                    System.out.print(" ");
                }
                System.out.print(path[i][j] + " ");
            }
            System.out.println();
        }
    }


    private void updateMap(int[][] grid) {
        bombList = new ArrayList<>();
        itemList = new ArrayList<>();
        enemyList = new ArrayList<>();
        exitList = new ArrayList<>();
        containerList = new ArrayList<>();
        trace = new Pair[length][length];

        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                this.grid[i][j] = grid[i][j];
                visited[i][j] = 0;
                path[i][j] = 1000000000;
            }
        }

    }

    private int moveCase(int x, int y, List<Pair> enemies) {

        bfs(x, y);
        enemyList = enemies;
        if (bombList.size() > 0) {
            if (checkSafePosition(x, y)) {
                return 0;
            } else {
                return 1;
            }
        } else {
            if (itemList.size() > 0) {
                return 2;
            } else {
                if (enemyList.size() > 0) {
                    int u = enemyList.get(0).getX();
                    int v = enemyList.get(0).getY();
                    if (path[u][v] >= 0 && path[u][v] < MAX_PATH) {
                        return 3;
                    } else {
                        return 4;
                    }
                } else {
                    if (exitList.size() > 0) {
                        return 5;
                    } else {
                        return 4;
                    }
                }
            }
        }
    }

    private void setEnemyList(List<Pair> enemyList) {
        this.enemyList = enemyList;
    }

    public int nextMove(int x, int y, List<Pair> enemies) {
        int option = moveCase(x, y, enemies);
        int result = -1;
        switch (option) {
            case 0:
                result = -1;
                break;
            case 1:
                Pair tempPosition = new Pair(-1000000000, -1000000000);
                int tempPath = 6;
                for (int i = -5; i <= 5; i++) {
                    for (int j = -5; j <= 5; j++) {
                        if (checkSafePosition(x + i, y + j) && path[x + i][y + j] < tempPath) {
                            tempPath = path[x + i][y + j];
                            tempPosition = new Pair(x + i, y + j);
                        }
                    }
                }

                if(tempPosition.getX() == -1000000000 && tempPosition.getY() == -1000000000){
                    result = -1;
                }
                else {
                    result = getDirection(x, y, tempPosition.getX(), tempPosition.getY());
                }
                break;
            case 2:
                tempPath = MAX_PATH;
                tempPosition = new Pair(-1000000000, -1000000000);
                for (Pair item : itemList) {
                    if (path[item.getX()][item.getY()] < tempPath) {
                        tempPath = path[item.getX()][item.getY()];
                        tempPosition = item;
                    }
                }
                result = getDirection(x, y, tempPosition.getX(), tempPosition.getY());
                break;
            case 3:
                tempPath = MAX_PATH;
                tempPosition = new Pair(-1000000000, -1000000000);
                for (Pair enemy : enemyList) {
                    if (path[enemy.getX()][enemy.getY()] < tempPath) {
                        tempPath = path[enemy.getX()][enemy.getY()];
                        tempPosition = enemy;
                    }
                }
                if (tempPath <= 2){
                    if(x == tempPosition.getX()) {
                        if(Math.abs(y - tempPosition.getY()) <= 2) {
                            result = 4;
                        }
                        else {
                            result = getDirection(x, y, tempPosition.getX(), tempPosition.getY());
                        }
                    }

                    else if(y == tempPosition.getY()) {
                        if(Math.abs(x - tempPosition.getX()) <= 2) {
                            result = 4;
                        }
                        else {
                            result = getDirection(x, y, tempPosition.getX(), tempPosition.getY());
                        }
                    }

                    else {
                        result = getDirection(x, y, tempPosition.getX(), tempPosition.getY());
                    }
                }
                else {
                    result = getDirection(x, y, tempPosition.getX(), tempPosition.getY());
                }
                break;
            case 4:
                tempPath = MAX_PATH;
                tempPosition = new Pair(-1000000000, -1000000000);
                for (Pair container : containerList) {
                    Pair tempPositionOfContainer;
                    for (int i = 0; i < 4; i++) {
                        tempPositionOfContainer = new Pair(container.getX() + dx[i], container.getY() + dy[i]);
                        if(checkSafePosition(tempPositionOfContainer.getX(), tempPositionOfContainer.getY())) {
                            if (path[tempPositionOfContainer.getX()][tempPositionOfContainer.getY()] < tempPath) {
                                tempPath = path[tempPositionOfContainer.getX()][tempPositionOfContainer.getY()];
                                tempPosition = tempPositionOfContainer;
                            }
                        }
                    }
                }
                if(tempPath == 0) {
                    result = 4;
                }
                else {
                    result = getDirection(x, y, tempPosition.getX(), tempPosition.getY());
                }
                break;
            case 5:
                tempPath = MAX_PATH;
                tempPosition = new Pair(-1000000000, -1000000000);
                for (Pair exit : exitList) {
                    if (path[exit.getX()][exit.getY()] < tempPath) {
                        tempPath = path[exit.getX()][exit.getY()];
                        tempPosition = exit;
                    }
                }
                result = getDirection(x, y, tempPosition.getX(), tempPosition.getY());
                break;
            default:
                break;
        }
        return result;
    }

    private int getDirection(int x, int y, int u, int v) {
        if (u == v && v == -1000000000) {
            return -1;
        }
        Pair move = traceBack(new Pair(x, y), new Pair(u, v));
        if (x == move.getX()) {
            if (y > move.getY()) {
                return 0;
            } else {
                return 1;
            }
        } else if (y == move.getY()) {
            if (x > move.getX()) {
                return 3;
            } else {
                return 2;
            }
        }

        return -1;
    }

    private Pair traceBack(Pair positionOfRoot, Pair positionOfDestination) {
        Pair result = new Pair(positionOfDestination.getX(), positionOfDestination.getY());
        if (result.getX() == positionOfRoot.getX() && result.getY() == positionOfRoot.getY()) {
            return result;
        }
        while (trace[result.getX()][result.getY()].getX() != positionOfRoot.getX()
                || trace[result.getX()][result.getY()].getY() != positionOfRoot.getY()) {
            //System.out.println(result.getX() + " " + result.getY() + " " + positionOfRoot.getX() + " " + positionOfRoot.getY());
            result = trace[result.getX()][result.getY()];
        }
        //System.out.println(trace[result.getX()][result.getY()].getX() + " " + trace[result.getX()][result.getY()].getY());
        return result;
    }

    public int[][] getGrid() {
        return grid;
    }

    public Pair getTrace(int u, int v) {
        return trace[u][v];
    }

    public static void main(String[] args) throws IOException {
        //RandomizeMap randomizeMap = new RandomizeMap(150, 50);
        File file = new File(
                "E:\\Code\\code_git\\BTL_OOP_2\\Bomberman\\src\\main\\java\\algorithms\\Bot\\testGrid.txt");
        BufferedReader br
                = new BufferedReader(new FileReader(file));

        int[][] grid = new int[20][20];

        for (int i = 0; i < 20; i++) {
            String[] st = br.readLine().split(" ");
            for (int j = 0; j < 20; j++) {
                grid[i][j] = Integer.parseInt(st[j]);
            }
        }
        BFS bfs = new BFS(grid, 20);
        int x = 1;
        int y = 1;
        bfs.bfs(x, y);

        int[][] map = bfs.getGrid();
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map.length; j++) {
                System.out.print(map[i][j] + " ");
            }
            System.out.println();
        }

        System.out.println("-----------------------------------------------------");
        //bfs.showPath();
        List<Pair> enemyList = new ArrayList<>(List.of(new Pair(1, 1)));
        int move = bfs.nextMove(x, y, enemyList);
        System.out.println(move);
        System.out.println(bfs.moveCase(x, y, enemyList));
        System.out.println("-----------------------------------------------------");
        //System.out.println(bfs.getDirection(0,1,1, 4));
        //System.out.println(bfs.getTrace(1, 1).getX() + " " + bfs.getTrace(1, 1).getY());
        //System.out.println(bfs.traceBack(new Pair(0, 1), new Pair(1, 4)).getX() + " " + bfs.traceBack(new Pair(0, 1), new Pair(1, 4)).getY());
    }

}
