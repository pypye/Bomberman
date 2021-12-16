package algorithms;

import com.jme3.math.Vector2f;
import cores.Map;
import entities.bombs.Bomb;
import entities.bombs.BombList;

import entities.buffs.ShieldItem;
import entities.players.MainPlayerAI;
import entities.players.Player;
import entities.players.PlayerList;
import entities.players.enemies.Enemy;
import entities.players.enemies.Golem;
import entities.players.enemies.Spider;
import entities.players.enemies.Turtle;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

public class FindPathMainPlayerAI {

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

    public FindPathMainPlayerAI() {
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

        Player mainPlayerAI = PlayerList.getMainPlayerAI();
        int safeDistance = 2;
        if (!hasShield(mainPlayerAI)) {
            for (Bomb bomb : BombList.bombs) {
                if (mainPlayerAI.getBombBuffDuration() >= 0f) {
                    safeDistance = 4;
                }
                if (bomb.getCord().x == u) {
                    if (Math.abs(bomb.getCord().y - v) <= safeDistance
                            && bomb.getTimeElapsed() < Bomb.DURATION - 0.5f) {
                        return false;
                    }
                }
                if (bomb.getCord().y == v) {
                    if (Math.abs(bomb.getCord().x - u) <= safeDistance
                            && bomb.getTimeElapsed() < Bomb.DURATION - 0.5f) {
                        return false;
                    }
                }
            }
        }



        return true;
    }

    private boolean hasShield(Player player) {
        if (player instanceof MainPlayerAI) {
            return player.getShieldBuffDuration() >= 0.2f;
        }
        return false;
    }
    private boolean checkNearEnemy(Vector2f position) {
        int[] corX = {0, 0, 0, -1, 1, 0};
        int[] corY = {0, -1, 1, 0, 0, 0};

        for (Player player : PlayerList.players) {
            if (player instanceof MainPlayerAI) {
                continue;
            }
            int moveOfEnemy = ((Enemy) player).getNextMove();
            Vector2f playerCord = player.getCord();
            if (player instanceof Spider ) {
                if (Manhattan(player.getCord().x, player.getCord().y, (int) position.x, (int) position.y) <= 1.5 && player.isUltimateActivated()) {
                    return false;
                }
                if (
                        checkRange((int) playerCord.x + corX[moveOfEnemy + 1], (int) playerCord.y + corY[moveOfEnemy + 1])
                        && checkRange((int) position.x, (int) position.y)
                        && checkRange((int) playerCord.x , (int) playerCord.y)
                        && (Manhattan(playerCord.x + corX[moveOfEnemy + 1], playerCord.y + corY[moveOfEnemy + 1],
                                    (int) position.x, (int) position.y) < 1f
                        || Manhattan(playerCord.x, playerCord.y, (int) position.x, (int) position.y) < 1f)

                ) {
                    return false;
                }
            }
            if (player instanceof Turtle) {
                if (
                        checkRange((int) playerCord.x + corX[moveOfEnemy + 1], (int) playerCord.y + corY[moveOfEnemy + 1])
                                && checkRange((int) position.x, (int) position.y)
                                && checkRange((int) playerCord.x , (int) playerCord.y)
                                && (Manhattan(playerCord.x + corX[moveOfEnemy + 1], playerCord.y + corY[moveOfEnemy + 1],
                                (int) position.x, (int) position.y) < 1f
                                || Manhattan(playerCord.x, playerCord.y, (int) position.x, (int) position.y) < 1f)

                ) {
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


    private float Manhattan(float x, float y, int u, int v) {
        return Math.abs(x - u) + Math.abs(y - v);
    }

    public int moveCase(int x, int y) {
        bfs(x, y);
        boolean checkHasShield = false;
        for (Player player : PlayerList.players) {
            if (player instanceof MainPlayerAI) {
                checkHasShield = hasShield(player);
                continue;
            }
            Vector2f playerPos = player.getCord();
            enemyList.add(playerPos);
        }

        Player spider = PlayerList.getSpider();
        //Player turtle = PlayerList.getTurtle();

        if ((bombList.size() > 0 && !checkHasShield)
                || (spider != null && spider.isUltimateActivated()
                    && Manhattan(x, y, (int) spider.getCord().x, (int) spider.getCord().y) <= 2)
                || !checkNearEnemy(new Vector2f(x, y))
        ) {
            if (checkSafePosition(x, y) && checkNearEnemy(new Vector2f(x, y))) {
                boolean check = true;
                for (Bomb bomb : BombList.bombs) {
                    if (bomb.getTimeElapsed() >= Bomb.DURATION - 1.8 / Manhattan(bomb.getCord().x,
                            bomb.getCord().y, x, y)) {
                        check = false;
                        break;
                    }
                }
                if(check) {
                    return 3;
                }
                else {
                    return 0;
                }
            } else {
                return 1;
            }
        } else {
            if (itemList.size() > 0) {
                return 2;
            } else {
                if (enemyList.size() > 0) {
                    float nearestEnemy = MAX_PATH;
                    Vector2f posOfNearestEnemy = new Vector2f(0, 0);
                    for (Vector2f enemy : enemyList) {
                        if (Manhattan(x, y, (int) enemy.getX(), (int) enemy.getY())
                                < nearestEnemy) {
                            posOfNearestEnemy = enemy;
                            nearestEnemy = Manhattan(x, y, (int) enemy.getX(), (int) enemy.getY());
                        }
                    }
                    int u = (int) posOfNearestEnemy.getX();
                    int v = (int) posOfNearestEnemy.getY();
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

    private boolean checkDifferentPosition(Vector2f pos, int next) {
        int[] corX = {0, 0, 0, -1, 1, 0};
        int[] corY = {0, -1, 1, 0, 0, 0};
        pos.setX(pos.getX() + corX[next + 1]);
        pos.setY(pos.getY() + corY[next + 1]);
        if (!checkNearEnemy(pos)) {
            return false;
        }
        return true;
    }

    public int nextMove(int x, int y) {
        int option = moveCase(x, y);
        int result = -1;
        switch (option) {
            case 0:
                break;
            case 1:
                Vector2f tempPosition = new Vector2f(-1000000000, -1000000000);
                int tempPath = 6;
                for (int i = -5; i <= 5; i++) {
                    for (int j = -5; j <= 5; j++) {
                        if (checkSafePosition(x + i, y + j) && path[x + i][y + j] <= tempPath
                                && checkNearEnemy(new Vector2f(x + i, y + j))) {

                            int temp = getDirection(x, y, (int) x + i,
                                    (int) y + j);
                            if (checkDifferentPosition(new Vector2f(x, y), temp)) {
                                tempPath = path[x + i][y + j];
                                tempPosition = new Vector2f(x + i, y + j);
                                result = temp;
                            }

                        }
                    }
                }


                break;
            case 2:
                tempPath = MAX_PATH;
                tempPosition = new Vector2f(-1000000000, -1000000000);
                for (Vector2f item : itemList) {
                    if (path[(int) item.getX()][(int) item.getY()] <= tempPath && checkNearEnemy(
                            item) && checkSafePosition((int) item.getX(), (int) item.getY())) {
                        int temp = getDirection(x, y, (int) item.getX(), (int) item.getY());
                        if (checkDifferentPosition(new Vector2f(x, y), temp)) {
                            tempPath = path[(int) item.getX()][(int) item.getY()];
                            tempPosition = item;
                            result = temp;
                        }
                    }
                }

                break;
            case 3:
                tempPath = MAX_PATH;
                tempPosition = new Vector2f(-1000000000, -1000000000);
                for (Vector2f enemy : enemyList) {
                    if (path[(int) enemy.getX()][(int) enemy.getY()] <= tempPath) {
                        tempPath = path[(int) enemy.getX()][(int) enemy.getY()];
                        tempPosition = enemy;
                        if (tempPath <= 2) {
                            if (tempPath == 1) {
//                        if (checkSafePosition((int) tempPosition.getX(), (int) tempPosition.getY()) || !checkNearEnemy(tempPosition)){
//                            result = 4;
//                        }
//                        else {
//                            result = -1;
//                        }
                                result = 4;
                            }
                            else {
                                if (Manhattan(x, y, (int) tempPosition.getX(), (int) tempPosition.getY()) <= 2) {
                                    result = 4;
                                }
                                else {
                                    result = getDirection(x, y, (int) tempPosition.getX(),
                                            (int) tempPosition.getY());
                                }
                            }
                        } else {
                            int temp = getDirection(x, y, (int) tempPosition.getX(),
                                    (int) tempPosition.getY());
                            if (checkDifferentPosition(new Vector2f(x, y), temp)) {
                                result = temp;
                            }
                        }
                    }
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
                                    <= tempPath && checkNearEnemy(tempPositionOfContainer)) {
                                tempPath = path[(int) tempPositionOfContainer.getX()][(int) tempPositionOfContainer.getY()];
                                tempPosition = tempPositionOfContainer;
                                if (tempPath == 0) {
                                    result = 4;
                                } else {
                                    if(tempPath == MAX_PATH){
                                        continue;
                                    }
                                    int temp = getDirection(x, y, (int) tempPosition.getX(), (int) tempPosition.getY());
                                    if(checkDifferentPosition(new Vector2f(x, y), temp)){
                                        result = temp;
                                    }
                                }
                            }
                        }
                    }
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
                || trace[(int) result.getX()][(int) result.getY()].getY()
                != positionOfRoot.getY()) {
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
