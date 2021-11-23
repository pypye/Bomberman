package scenes;

import cores.Environment;
import cores.Map;
import entities.bombs.BombList;
import entities.players.PlayerList;
import events.PlayerInput;
import particles.BombExplodeParticleList;

public class Game extends Scene {
    @Override
    public void show() {
        Environment.init();
        Map.init();
        isActive = true;
    }

    @Override
    public void update(float tpf) {
        if (!isActive) return;
        BombList.onUpdate();
        BombExplodeParticleList.onUpdate();
        PlayerInput.onUpdate();
        PlayerList.onUpdate(tpf);
    }

    @Override
    public void hide() {
        isActive = false;
        Environment.hide();
        Map.hide();
    }
}
