package algorithms.bot;


import cores.Map;
import entities.bombs.Bomb;
import entities.bombs.BombList;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;


public class GolemAI {

    private final int[][] visited;
    private final int length = 20;
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

    public GolemAI() {
        visited = new int[length][length];
        path = new int[length][length];
    }

    public boolean checkRange(int u, int v) {
        return u >= 0 && u < length && v >= 0 && v < length;
    }


    public boolean isVisited(int u, int v) {
        return visited[u][v] == 1;
    }

    public boolean checkSafePosition(int u, int v) {
        if (!checkRange(u, v)) {
            return false;
        }
        for (Bomb bomb : BombList.bombs) {
            if (bomb.getCord().x == u) {
                if (Math.abs(bomb.getCord().y - v) <= 2 && System.currentTimeMillis() - bomb.getTimeStarted() < Bomb.DURATION - 500) {
                    return false;
                }
            }
            if (bomb.getCord().y == v) {
                if (Math.abs(bomb.getCord().x - u) <= 2 && System.currentTimeMillis() - bomb.getTimeStarted() < Bomb.DURATION - 500) {
                    return false;
                }
            }

        }
        return true;
    }

    public void bfs(int x, int y) {
        int[][] map = Map.getMap();
        updateMap();
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
                if (checkRange(newX, newY)) {
                    addAttribute(newX, newY, map);
                }
                if (checkRange(newX, newY)) {
                    if (!isVisited(newX, newY) && !Map.isBlocked(newX, newY)) {
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

    private void updateMap() {
        bombList = new ArrayList<>();
        itemList = new ArrayList<>();
        enemyList = new ArrayList<>();
        exitList = new ArrayList<>();
        containerList = new ArrayList<>();
        trace = new Pair[length][length];

        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                visited[i][j] = 0;
                path[i][j] = 1000000000;
            }
        }

    }
    private int randomMissRate(int result, double missRate) {
        missRate *= 1000;
        Random r = new Random();
        int game = r.nextInt(100 * 1000);
        int resultAfterMiss;
        if (game <= missRate){
            resultAfterMiss = r.nextInt(result);
        }
        else {
            return result;
        }
        return resultAfterMiss;
    }
    private float mahattan(float x, float y, int u, int v) {
        return Math.abs(x - u) + Math.abs(y - v);
    }

    public int moveCase(int x, int y, int l, int r) {
        bfs(x, y);
        enemyList.add(new Pair(l, r));
        if (bombList.size() > 0) {
            if (checkSafePosition(x, y)) {
                for (Bomb bomb : BombList.bombs) {
                    if(System.currentTimeMillis() - bomb.getTimeStarted() < Bomb.DURATION - 2000 / mahattan(bomb.getCord().x, bomb.getCord().y, x, y)) {
                        return 3;
                    }
                }
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

    public int nextMove(int x, int y, int l, int r) {
        int option = moveCase(x, y, l, r);
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

                if (tempPosition.getX() == -1000000000 && tempPosition.getY() == -1000000000) {
                    result = -1;
                } else {
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
                if (tempPath <= 2) {
                    if (x == tempPosition.getX()) {
                        if (Math.abs(y - tempPosition.getY()) <= 2) {
                            result = 4;
                        } else {
                            result = getDirection(x, y, tempPosition.getX(), tempPosition.getY());
                        }
                    } else if (y == tempPosition.getY()) {
                        if (Math.abs(x - tempPosition.getX()) <= 2) {
                            result = 4;
                        } else {
                            result = getDirection(x, y, tempPosition.getX(), tempPosition.getY());
                        }
                    } else {
                        result = getDirection(x, y, tempPosition.getX(), tempPosition.getY());
                    }
                } else {
                    result = getDirection(x, y, tempPosition.getX(), tempPosition.getY());
                }
                break;
            case 4:
                tempPath = MAX_PATH;
                tempPosition = new Pair(-1000000000, -1000000000);
                for (Pair container : containerList) {
                    Pair tempPositionOfContainer;
                    for (int i = 0; i < 4; i++) {
                        tempPositionOfContainer = new Pair(container.getX() + dx[i],
                                container.getY() + dy[i]);
                        if (checkSafePosition(tempPositionOfContainer.getX(),
                                tempPositionOfContainer.getY())) {
                            if (path[tempPositionOfContainer.getX()][tempPositionOfContainer.getY()]
                                    < tempPath) {
                                tempPath = path[tempPositionOfContainer.getX()][tempPositionOfContainer.getY()];
                                tempPosition = tempPositionOfContainer;
                            }
                        }
                    }
                }
                if (tempPath == 0) {
                    result = 4;
                } else {
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


        return randomMissRate(result, 0);
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
                return 2;
            } else {
                return 3;
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

    public Pair getTrace(int u, int v) {
        return trace[u][v];
    }

}
