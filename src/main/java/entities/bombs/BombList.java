package entities.bombs;

import cores.Map;
import entities.players.Player;
import entities.players.PlayerList;

import java.util.ArrayList;

public class BombList {
    public static ArrayList<Bomb> bombs = new ArrayList<>();

    public static void add(Bomb bomb) {
        bombs.add(bomb);
    }

    public static void remove(Bomb bomb) {
        int cordX = (int) bomb.getCord().x;
        int cordY = (int) bomb.getCord().y;
        for (int i = Math.max(0, cordX - 2); i <= Math.min(cordX + 2, 20); i++) {
            Map.setObject(i, cordY, Map.GRASS);
        }
        for (int i = Math.max(0, cordY - 2); i <= Math.min(cordY + 2, 20); i++) {
            Map.setObject(cordX, i, Map.GRASS);
        }
        bombs.remove(bomb);
    }

    public static void onUpdate() {
        ArrayList<Bomb> removeList = new ArrayList<>();
        for (Bomb bomb : bombs) {
            boolean check = true;
            for (Player player : PlayerList.players) {
                if (bomb.getCord().x == player.getCord().x && bomb.getCord().y == player.getCord().y) check = false;
            }
            if (check) {
                Map.setBlock((int) bomb.getCord().x, (int) bomb.getCord().y, Map.BLOCKED);
            }
            if (System.currentTimeMillis() - bomb.getTimeStarted() >= Bomb.DURATION) {
                removeList.add(bomb);
            }
        }
        for (Bomb bomb : removeList) {
            BombList.remove(bomb);
        }
    }
}
