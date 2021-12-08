package algorithms;


import com.jme3.math.Vector2f;
import cores.Map;
import entities.bombs.Bomb;
import entities.bombs.BombList;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;


public class FindPathAI {

    private final int[][] visited;
    private final int length = 20;
    private final int[] dx = {-1, 0, 1, 0};
    private final int[] dy = {0, 1, 0, -1};
    private final int[][] path;
    private List<Vector2f> bombList;
    private List<Vector2f> itemList;
    private List<Vector2f> enemyList;
    private List<Vector2f> exitList;
    private List<Vector2f> containerList;
    private Vector2f[][] trace;
    private static final int MAX_PATH = 1000000000;

    private final int level;

    public FindPathAI(int level) {
        visited = new int[length][length];
        path = new int[length][length];
        this.level = level;
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
                if (Math.abs(bomb.getCord().y - v) <= 2 && bomb.getTimeElapsed() < Bomb.DURATION - 0.5f) {
                    return false;
                }
            }
            if (bomb.getCord().y == v) {
                if (Math.abs(bomb.getCord().x - u) <= 2 && bomb.getTimeElapsed() < Bomb.DURATION - 0.5f) {
                    return false;
                }
            }

        }
        return true;
    }

    public void bfs(int x, int y) {
        int[][] map = Map.getMap();
        updateMap();
        Queue<Vector2f> queue = new LinkedList<>();

        visited[x][y] = 1;
        path[x][y] = 0;
        queue.add(new Vector2f(x, y));
        addAttribute(x, y, map);

        while (!queue.isEmpty()) {
            Vector2f pair = queue.poll();
            int u = (int) pair.getX();
            int v = (int) pair.getY();
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
                        queue.add(new Vector2f(newX, newY));
                        trace[newX][newY] = new Vector2f(u, v);
                        visited[newX][newY] = 1;
                    }
                }
            }
        }
    }

    private void addAttribute(int newX, int newY, int[][] map) {
        if (map[newX][newY] == 2) {
            containerList.add(new Vector2f(newX, newY));
        } else if (map[newX][newY] == 3) {
            bombList.add(new Vector2f(newX, newY));
        } else if (map[newX][newY] == 4) {
            exitList.add(new Vector2f(newX, newY));
        } else if (map[newX][newY] > 4 && map[newX][newY] < 9) {
            itemList.add(new Vector2f(newX, newY));
        } else if (map[newX][newY] == 9) {
            enemyList.add(new Vector2f(newX, newY));
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
        trace = new Vector2f[length][length];

        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                visited[i][j] = 0;
                path[i][j] = 1000000000;
            }
        }

    }

    private int randomMissRate(int result, double missRate) {
        missRate *= 1000;

        int range = 100001;
        int rand = (int) (Math.random() * range) + 1;
        int resultAfterMiss =  -1;
        if (rand <= missRate) {
            resultAfterMiss = (int) (Math.random() * 6) - 1;
        } else {
            return result;
        }
        return resultAfterMiss;
    }

    private float Manhattan(float x, float y, int u, int v) {
        return Math.abs(x - u) + Math.abs(y - v);
    }

    public int moveCase(int x, int y, int l, int r) {
        bfs(x, y);
        enemyList.add(new Vector2f(l, r));
        if (bombList.size() > 0) {
            if (checkSafePosition(x, y)) {
                for (Bomb bomb : BombList.bombs) {
                    if (bomb.getTimeElapsed() < Bomb.DURATION - 2 / Manhattan(bomb.getCord().x, bomb.getCord().y, x, y)) {
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
                    int u = (int) enemyList.get(0).getX();
                    int v = (int) enemyList.get(0).getY();
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
        option = randomMissRate(option, 100 - 15 * level);
        int result = -1;
        switch (option) {
            case 0:
                break;
            case 1:
                Vector2f tempPosition = new Vector2f(-1000000000, -1000000000);
                int tempPath = 6;
                for (int i = -5; i <= 5; i++) {
                    for (int j = -5; j <= 5; j++) {
                        if (checkSafePosition(x + i, y + j) && path[x + i][y + j] < tempPath) {
                            tempPath = path[x + i][y + j];
                            tempPosition = new Vector2f(x + i, y + j);
                        }
                    }
                }

                if ((int) tempPosition.getX() == -1000000000 && (int) tempPosition.getY() == -1000000000);
                else {
                    result = getDirection(x, y, (int) tempPosition.getX(), (int) tempPosition.getY());
                }
                break;
            case 2:
                tempPath = MAX_PATH;
                tempPosition = new Vector2f(-1000000000, -1000000000);
                for (Vector2f item : itemList) {
                    if (path[(int) item.getX()][(int) item.getY()] < tempPath) {
                        tempPath = path[(int) item.getX()][(int) item.getY()];
                        tempPosition = item;
                    }
                }
                result = getDirection(x, y, (int) tempPosition.getX(), (int) tempPosition.getY());
                break;
            case 3:
                tempPath = MAX_PATH;
                tempPosition = new Vector2f(-1000000000, -1000000000);
                for (Vector2f enemy : enemyList) {
                    if (path[(int) enemy.getX()][(int) enemy.getY()] < tempPath) {
                        tempPath = path[(int) enemy.getX()][(int) enemy.getY()];
                        tempPosition = enemy;
                    }
                }
                if (tempPath <= 2) {
                    if (x == (int) tempPosition.getX()) {
                        if (Math.abs(y - (int) tempPosition.getY()) <= 2) {
                            result = 4;
                        } else {
                            result = getDirection(x, y, (int) tempPosition.getX(), (int) tempPosition.getY());
                        }
                    } else if (y == (int) tempPosition.getY()) {
                        if (Math.abs(x - (int) tempPosition.getX()) <= 2) {
                            result = 4;
                        } else {
                            result = getDirection(x, y, (int) tempPosition.getX(), (int) tempPosition.getY());
                        }
                    } else {
                        result = getDirection(x, y, (int) tempPosition.getX(), (int) tempPosition.getY());
                    }
                } else {
                    result = getDirection(x, y, (int) tempPosition.getX(), (int) tempPosition.getY());
                }
                break;
            case 4:
                tempPath = MAX_PATH;
                tempPosition = new Vector2f(-1000000000, -1000000000);
                for (Vector2f container : containerList) {
                    Vector2f tempPositionOfContainer;
                    for (int i = 0; i < 4; i++) {
                        tempPositionOfContainer = new Vector2f(container.getX() + dx[i],
                                container.getY() + dy[i]);
                        if (checkSafePosition((int) tempPositionOfContainer.getX(),
                                (int) tempPositionOfContainer.getY())) {
                            if (path[(int) tempPositionOfContainer.getX()][(int) tempPositionOfContainer.getY()]
                                    < tempPath) {
                                tempPath = path[(int) tempPositionOfContainer.getX()][(int) tempPositionOfContainer.getY()];
                                tempPosition = tempPositionOfContainer;
                            }
                        }
                    }
                }
                if (tempPath == 0) {
                    result = 4;
                } else {
                    result = getDirection(x, y, (int) tempPosition.getX(), (int) tempPosition.getY());
                }
                break;
            case 5:
                tempPath = MAX_PATH;
                tempPosition = new Vector2f(-1000000000, -1000000000);
                for (Vector2f exit : exitList) {
                    if (path[(int) exit.getX()][(int) exit.getY()] < tempPath) {
                        tempPath = path[(int) exit.getX()][(int) exit.getY()];
                        tempPosition = exit;
                    }
                }
                result = getDirection(x, y, (int) tempPosition.getX(), (int) tempPosition.getY());
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
        Vector2f move = traceBack(new Vector2f(x, y), new Vector2f(u, v));
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

    private Vector2f traceBack(Vector2f positionOfRoot, Vector2f positionOfDestination) {
        Vector2f result = new Vector2f(positionOfDestination.getX(), positionOfDestination.getY());
        if (result.getX() == positionOfRoot.getX() && result.getY() == positionOfRoot.getY()) {
            return result;
        }
        while (trace[(int) result.getX()][(int) result.getY()].getX() != positionOfRoot.getX()
                || trace[(int) result.getX()][(int) result.getY()].getY() != positionOfRoot.getY()) {
            //System.out.println((int) result.getX() + " " + (int) result.getY() + " " + positionOfRoot.getX() + " " + positionOfRoot.getY());
            result = trace[(int) result.getX()][(int) result.getY()];
        }
        //System.out.println(trace[(int) result.getX()][(int) result.getY()].getX() + " " + trace[(int) result.getX()][(int) result.getY()].getY());
        return result;
    }

    public Vector2f getTrace(int u, int v) {
        return trace[u][v];
    }

}
