package particles;

import java.util.ArrayList;

public class BombExplodeParticleList {
    public static ArrayList<BombExplodeParticle> bombExplodeParticles = new ArrayList<>();

    public static void add(BombExplodeParticle bombExplodeParticle) {
        bombExplodeParticles.add(bombExplodeParticle);
    }

    public static void remove(BombExplodeParticle bombExplodeParticle) {
        bombExplodeParticle.remove();
        bombExplodeParticles.remove(bombExplodeParticle);
    }

    public static void onUpdate() {
        ArrayList<BombExplodeParticle> removeList = new ArrayList<>();
        for (BombExplodeParticle bombExplodeParticle : bombExplodeParticles) {
            if (System.currentTimeMillis() - bombExplodeParticle.getStartTime() > BombExplodeParticle.DURATION) {
                removeList.add(bombExplodeParticle);
            }
        }
        for (BombExplodeParticle bombExplodeParticle : removeList) {
            remove(bombExplodeParticle);
        }
    }
}
