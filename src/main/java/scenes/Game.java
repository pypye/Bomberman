package scenes;

import cores.Environment;
import cores.Map;
import entities.bombs.BombList;
import entities.players.PlayerList;
import input.PlayerInput;
import particles.BombExplodeParticleList;
import ui.gui.InfoGuiList;

public class Game extends Scene {
    private int level;

    public Game(int level) {
        this.level = level;
    }

    @Override
    public void show() {
        Environment.init();
        Map.init();
        InfoGuiList.init();
        isActive = true;
    }

    @Override
    public void update(float tpf) {
        if (!isActive) return;
        BombList.onUpdate();
        BombExplodeParticleList.onUpdate();
        PlayerInput.onUpdate();
        PlayerList.onUpdate(tpf);
        InfoGuiList.onUpdate(tpf);
    }

    @Override
    public void hide() {
        isActive = false;
        PlayerInput.destroyKeys();
        PlayerList.removeAll();
        Environment.hide();
        Map.hide();
        InfoGuiList.hide();
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getLevel() {
        return level;
    }
}
