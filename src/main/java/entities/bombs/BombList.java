package entities.bombs;

import audio.AudioManager;
import cores.Map;
import entities.Entity;
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
        for (int i = Math.max(0, cordX - 1); i >= Math.max(0, cordX - bomb.getBombExplodeLength()); --i) {
            checkKillPlayer(i, cordY);
            if (explosion(i, cordY)) break;
        }
        for (int i = Math.min(cordX + 1, Map.SIZE - 1); i <= Math.min(cordX + bomb.getBombExplodeLength(), Map.SIZE - 1); ++i) {
            checkKillPlayer(i, cordY);
            if (explosion(i, cordY)) break;
        }
        for (int i = Math.max(0, cordY - 1); i >= Math.max(0, cordY - bomb.getBombExplodeLength()); --i) {
            checkKillPlayer(cordX, i);
            if (explosion(cordX, i)) break;
        }
        for (int i = Math.min(cordY + 1, Map.SIZE - 1); i <= Math.min(cordY + bomb.getBombExplodeLength(), Map.SIZE - 1); ++i) {
            checkKillPlayer(cordX, i);
            if (explosion(cordX, i)) break;
        }
        checkKillPlayer(cordX, cordY);
        Map.setObject(cordX, cordY, Map.GRASS, null);
        AudioManager.explosion.setLocalTranslation(cordX * 2f, 1, cordY * 2f);
        AudioManager.explosion.play();
        bomb.getSpark().remove();
        bombs.remove(bomb);
    }

    public static void removeAll() {
        bombs.clear();
    }

    public static void onUpdate(float tpf) {
        ArrayList<Bomb> removeList = new ArrayList<>();
        for (Bomb bomb : bombs) {
            boolean check = true;
            for (Player player : PlayerList.players) {
                if (bomb.getCord().x == player.getCord().x && bomb.getCord().y == player.getCord().y) check = false;
            }
            if (check) Map.setBlocked((int) bomb.getCord().x, (int) bomb.getCord().y, true);
            bomb.setTimeElapsed(bomb.getTimeElapsed() + tpf);
            if (bomb.getTimeElapsed() >= Bomb.DURATION) removeList.add(bomb);
        }
        for (Bomb bomb : removeList) {
            BombList.remove(bomb);
        }
    }

    private static boolean explosion(int x, int y) {
        if (Map.getObject(x, y) != Map.GRASS && Map.getObject(x, y) != Map.PORTAL && Map.getObject(x, y) != Map.SPEED_ITEM && Map.getObject(x, y) != Map.BOMB_EX_ITEM && Map.getObject(x, y) != Map.FLAME_ITEM && Map.getObject(x, y) != Map.SHIELD_ITEM) {
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
