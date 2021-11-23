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
                Vector2f enemyPos = new Vector2f(enemy.getCord().x, enemy.getCord().y);
                Vector2f playerPos = new Vector2f(players.get(0).getCord().x, players.get(0).getCord().y);
                if (!enemy.isMoving()) {
                    System.out.println(enemyPos + " " + playerPos);
                    int nextMove = enemy.nextMove(enemyPos, playerPos, Map.getMap());
                    enemy.setNextMove(nextMove);
                    enemy.setMoving(true);
                }
                if(enemy.isMoving()) {
                    switch (enemy.getNextMove()) {
                        case Enemy.LEFT:
                            enemy.moveLeft(0.01f);
                            break;
                        case Enemy.RIGHT:
                            enemy.moveRight(0.01f);
                            break;
                        case Enemy.UP:
                            enemy.moveForward(0.01f);
                            break;
                        case Enemy.DOWN:
                            enemy.moveBackward(0.01f);
                            break;
                    }
                    enemyPos = new Vector2f(enemy.getPosition().x, enemy.getPosition().z);
                    Vector2f center = Entity.getCenterCord(enemy.getCord().x, enemy.getCord().y);
                    float eps = 0.005f;
                    if ((center.x - eps <= enemyPos.x && enemyPos.x <= center.x + eps)
                            && center.y - eps <= enemyPos.y && enemyPos.y <= center.y + eps) {
                        enemy.setPosition(new Vector3f(center.x, 1, center.y));
                        enemy.setMoving(false);
                    }
                }
            }
        }
    }
}
