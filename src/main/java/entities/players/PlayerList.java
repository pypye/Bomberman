package entities.players;

import entities.players.enemies.Enemy;
import ui.gui.game.AnnouncementGui;
import ui.gui.buffs.BuffListGui;

import java.util.ArrayList;

public class PlayerList {
    public static final ArrayList<Player> players = new ArrayList<>();

    public static void add(Player player) {
        players.add(player);
    }

    public static void remove(Player player) {
        if (!player.shieldBuffActivated) {
            player.remove();
            if (player instanceof MainPlayer) {
                new AnnouncementGui(false);
            }
            players.remove(player);

        } else {
            player.shieldBuffActivated = false;
            player.shieldBuffDuration = 0;
            if(player instanceof MainPlayer) {
                if (BuffListGui.getBuffList().contains(((MainPlayer) player).shieldBuffGUI))
                    BuffListGui.removeBuff(((MainPlayer) player).shieldBuffGUI);
            }
        }
    }

    public static void removeAll() {
        ArrayList<Player> removeList = new ArrayList<>(players);
        for (Player player : removeList) {
            remove(player);
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
