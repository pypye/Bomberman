package entities.players;

import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import cores.Map;
import entities.Entity;
import entities.players.enemies.Enemy;
import events.PlayerInput;

import java.util.ArrayList;

public class PlayerList {
    public static final ArrayList<Player> players = new ArrayList<>();

    public static void add(Player player) {
        players.add(player);
    }

    public static void remove(Player player) {
        if (!player.shieldBuffActivated) {
            player.dead();
            if (player instanceof MainPlayer) {
                PlayerInput.destroyKeys();
                System.out.println("Player died, back to main menu adding soon!");
                System.exit(0);
            }
            players.remove(player);
        } else {
            player.shieldBuffActivated = false;
            player.shieldBuffDuration = 0;
        }
    }

    public static void onUpdate(float tpf) {
        for (Player player : players) {
            player.onUpdate(tpf);
            if (player instanceof Enemy) {
                Enemy enemy = (Enemy) player;
                Vector2f enemyCord = new Vector2f(enemy.getCord().x, enemy.getCord().y);
                Vector2f enemyPos = new Vector2f(enemy.getPosition().x, enemy.getPosition().z);
                //System.out.println(enemyPos);
                //System.out.println(enemyCord + " " + enemy.getRandomTargetPoint() + " " + enemy.getNextMove());
                Vector2f playerCord = new Vector2f(players.get(0).getCord().x, players.get(0).getCord().y);
                if (!enemy.isMoving()) {
                    int nextMove = enemy.nextMove(enemyCord, playerCord, Map.getMap());
                    enemy.setNextMove(nextMove);
                    enemy.setMoving(true);
                }
                if (enemy.isMoving()) {
                    switch (enemy.getNextMove()) {
                        case Enemy.LEFT:
                            enemy.moveLeft(tpf / 2);
                            break;
                        case Enemy.RIGHT:
                            enemy.moveRight(tpf / 2);
                            break;
                        case Enemy.UP:
                            enemy.moveForward(tpf / 2);
                            break;
                        case Enemy.DOWN:
                            enemy.moveBackward(tpf / 2);
                            break;
                    }
                    Vector2f enemyNewPos = new Vector2f(enemy.getPosition().x, enemy.getPosition().z);
                    Vector2f center = Entity.getCenterCord(enemy.getCord().x, enemy.getCord().y);
                    //System.out.println("Center " + center);
                    //System.out.println("New pos" + " " + enemyNewPos);
                    float eps = tpf / 2;
//                    if (Math.abs(enemyNewPos.x - enemyPos.x) <= eps && Math.abs(enemyNewPos.y - enemyPos.y) <= eps) {
//                        //enemy.setPosition(new Vector3f(center.x, 1, center.y));
//                        enemy.setMoving(false);
//                        continue;
//                    }

                    if ((center.x - eps < enemyNewPos.x && enemyNewPos.x < center.x + eps)
                            && center.y - eps < enemyNewPos.y && enemyNewPos.y < center.y + eps) {
                        enemy.setPosition(new Vector3f(center.x, 1, center.y));
                        enemy.setMoving(false);
                    }
                }
            }
        }
    }
}
