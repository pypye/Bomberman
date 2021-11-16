package entities.bombs;

import cores.Map;
import entities.players.Player;
import entities.players.PlayerList;
import particles.BombExplode;
import particles.BombExplodeList;

import java.util.ArrayList;
import java.util.Random;

public class BombList {
    public static ArrayList<Bomb> bombs = new ArrayList<>();

    public static void add(Bomb bomb) {
        bombs.add(bomb);
    }

    public static void remove(Bomb bomb) {
        int cordX = (int) bomb.getCord().x;
        int cordY = (int) bomb.getCord().y;
        BombExplodeList.add(new BombExplode(cordX, cordY));
        for (int i = Math.max(0, cordX - 1); i >= Math.max(0, cordX - bomb.getExplosionLength()); --i) {
            if (explosion(i, cordY)) break;
        }
        for (int i = Math.min(cordX + 1, 20); i <= Math.min(cordX + bomb.getExplosionLength(), 20); ++i) {
            if (explosion(i, cordY)) break;
        }
        for (int i = Math.max(0, cordY - 1); i >= Math.max(0, cordY - bomb.getExplosionLength()); --i) {
            if (explosion(cordX, i)) break;
        }
        for (int i = Math.min(cordY + 1, 20); i <= Math.min(cordY + bomb.getExplosionLength(), 20); ++i) {
            if (explosion(cordX, i)) break;
        }
        Map.setObject(cordX, cordY, Map.GRASS);
        bomb.getSpark().remove();
        bombs.remove(bomb);
    }

    private static boolean explosion(int x, int y) {
        if (Map.getObject(x, y) != Map.GRASS) {
            if (Map.getObject(x, y) == Map.CONTAINER) {
                BombExplodeList.add(new BombExplode(x, y));
                generateBuffItem(x, y);
            }
            return true;
        }
        BombExplodeList.add(new BombExplode(x, y));
        return false;
    }

    public static void onUpdate() {
        ArrayList<Bomb> removeList = new ArrayList<>();
        for (Bomb bomb : bombs) {
            boolean check = true;
            for (Player player : PlayerList.players) {
                if (bomb.getCord().x == player.getCord().x && bomb.getCord().y == player.getCord().y) check = false;
            }
            if (check) {
                Map.setBlocked((int) bomb.getCord().x, (int) bomb.getCord().y, true);
            }
            if (System.currentTimeMillis() - bomb.getTimeStarted() >= Bomb.DURATION) {
                removeList.add(bomb);
            }
        }
        for (Bomb bomb : removeList) {
            BombList.remove(bomb);
        }
    }

    private static void generateBuffItem(int cordX, int cordY){
        Random random = new Random();
        int lower = 5, upper = 15;
        Map.setObject(cordX, cordY, random.nextInt(upper - lower) + lower);
    }
}
