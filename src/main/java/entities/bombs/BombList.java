package entities.bombs;

import cores.Map;
import entities.buffs.BuffItem;
import entities.players.Player;
import entities.players.PlayerList;
import entities.terrains.Container;
import entities.terrains.Portal;
import particles.BombExplodeParticle;
import particles.BombExplodeParticleList;
import java.util.ArrayList;

public class BombList {
    public static ArrayList<Bomb> bombs = new ArrayList<>();

    public static void add(Bomb bomb) {
        bombs.add(bomb);
    }

    public static void remove(Bomb bomb) {
        int cordX = (int) bomb.getCord().x;
        int cordY = (int) bomb.getCord().y;
        BombExplodeParticleList.add(new BombExplodeParticle(cordX, cordY));
        for (int i = Math.max(0, cordX - 1); i >= Math.max(0, cordX - bomb.getOwner().getBombExplodeLength()); --i) {
            checkKillPlayer(i, cordY);
            if (explosion(i, cordY)) break;
        }
        for (int i = Math.min(cordX + 1, Map.SIZE - 1); i <= Math.min(cordX + bomb.getOwner().getBombExplodeLength(), Map.SIZE - 1); ++i) {
            checkKillPlayer(i, cordY);
            if (explosion(i, cordY)) break;
        }
        for (int i = Math.max(0, cordY - 1); i >= Math.max(0, cordY - bomb.getOwner().getBombExplodeLength()); --i) {
            checkKillPlayer(cordX, i);
            if (explosion(cordX, i)) break;
        }
        for (int i = Math.min(cordY + 1, Map.SIZE - 1); i <= Math.min(cordY + bomb.getOwner().getBombExplodeLength(), Map.SIZE - 1); ++i) {
            checkKillPlayer(cordX, i);
            if (explosion(cordX, i)) break;
        }
        checkKillPlayer(cordX, cordY);
        Map.setObject(cordX, cordY, Map.GRASS, null);
        bomb.getSpark().remove();
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

    private static boolean explosion(int x, int y) {
        if (Map.getObject(x, y) != Map.GRASS) {
            if (Map.getObject(x, y) == Map.CONTAINER) {
                BombExplodeParticleList.add(new BombExplodeParticle(x, y));
                double r = Math.random();
                boolean generatedPortal = false;
                if (r >= 0.75 || Container.getCount() <= 1) {
                    if (Portal.hasRemain()) {
                        Map.setObject(x, y, Map.PORTAL, null);
                        generatedPortal = true;
                    }
                }
                if (!generatedPortal) {
                    BuffItem.generateBuffItem(x, y);
                }
            }
            return true;
        }
        BombExplodeParticleList.add(new BombExplodeParticle(x, y));
        return false;
    }

    private static void checkKillPlayer(int x, int y) {
        ArrayList<Player> removeList = new ArrayList<>();
        for (Player player : PlayerList.players) {
            if (player.getCord().x == x && player.getCord().y == y) {
                removeList.add(player);
            }
        }
        for (Player player : removeList) {
            PlayerList.remove(player);
        }
    }
}
