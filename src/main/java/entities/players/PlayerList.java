package entities.players;

import entities.players.enemies.Enemy;
import input.PlayerInput;

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
                System.out.println("[Debug/Event] Player died, back to main menu adding soon!");
                System.exit(0);
            }
            players.remove(player);

        } else {
            player.shieldBuffActivated = false;
            player.shieldBuffDuration = 0;
        }
    }

    public static Player getMainPlayer() {
        for (Player player : players) {
            if (player instanceof MainPlayer) {
                return player;
            }
        }
        return null;
    }

    public static void onUpdate(float tpf) {
        for (Player player : players) {
            player.onUpdate(tpf);
            if (player instanceof Enemy) {
                Enemy enemy = (Enemy) player;
                enemy.onMoving();
                boolean collision = enemy.isCollisionWithMainPlayer();
                if (collision && getMainPlayer() != null) {
                    PlayerList.remove(getMainPlayer());
                }
            }
        }
    }
}
