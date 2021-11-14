package particles;

import cores.Main;

import java.util.ArrayList;

public class BombExplodeList {
    public static ArrayList<BombExplode> bombExplodes = new ArrayList<>();

    public static void add(BombExplode bombExplode) {
        bombExplodes.add(bombExplode);
    }

    public static void remove(BombExplode bombExplode) {
        bombExplode.remove();
        bombExplodes.remove(bombExplode);
    }

    public static void onUpdate() {
        ArrayList<BombExplode> removeList = new ArrayList<>();
        for (BombExplode bombExplode : bombExplodes) {
            if (System.currentTimeMillis() - bombExplode.getStartTime() > BombExplode.DURATION) {
                removeList.add(bombExplode);
            }
        }
        for (BombExplode bombExplode : removeList) {
            remove(bombExplode);
        }
    }
}
