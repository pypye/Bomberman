package entities.players;

import entities.players.enemies.Enemy;
import input.PlayerInput;
import ui.gui.AnnouncementGui;

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
                new AnnouncementGui(false, 1);
            }
            players.remove(player);

        } else {
            player.shieldBuffActivated = false;
            player.shieldBuffDuration = 0;
        }
    }

    public static void removeAll() {
        ArrayList<Player> removeList = new ArrayList<>(players);
        for (Player player : removeList) {
            PlayerList.remove(player);
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
        ArrayList<Player> removeList = new ArrayList<>();
        for (Player player : players) {
            player.onUpdate(tpf);
            if (player instanceof Enemy) {
                Enemy enemy = (Enemy) player;
                enemy.onMoving();
                boolean collision = enemy.isCollisionWithMainPlayer();
                if (collision && getMainPlayer() != null) {
                    removeList.add(getMainPlayer());
                }
            }
        }
        for (Player player : removeList) {
            PlayerList.remove(player);
        }
    }
}
