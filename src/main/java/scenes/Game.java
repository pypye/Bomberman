package scenes;

import com.jme3.math.Vector3f;
import cores.Debugger;
import cores.Environment;
import cores.Map;
import entities.bombs.BombList;
import entities.players.PlayerList;
import entities.players.enemies.Golem;
import input.PlayerInput;
import particles.BombExplodeParticleList;
import ui.gui.game.InfoGuiList;

public class Game extends Scene {
    private int level;

    public Game(int level) {
        this.level = level;
    }

    @Override
    public void setActive(boolean active) {
        super.setActive(active);
        PlayerInput.setPaused(!active);
    }

    @Override
    public void show() {
        Debugger.log(Debugger.GAME, "Init scene with level = " + level);
        setActive(true);
        Environment.initialize();
        Map.initialize(level);
        PlayerInput.initialize();
        InfoGuiList.initialize();
    }

    @Override
    public void update(float tpf) {
        BombList.onUpdate();
        BombExplodeParticleList.onUpdate();
        PlayerInput.onUpdate();
        PlayerList.onUpdate(tpf);
        InfoGuiList.onUpdate(tpf);
    }

    @Override
    public void remove() {
        setActive(false);
        InfoGuiList.remove();
        PlayerList.removeAll();
        BombList.removeAll();
        PlayerInput.remove();
        Map.remove();
        Environment.remove();
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getLevel() {
        return level;
    }
}
