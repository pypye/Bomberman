package scenes;

import audio.AudioManager;
import cores.Debugger;
import cores.Environment;
import cores.Map;
import entities.bombs.BombList;
import entities.players.PlayerList;
import input.PlayerInput;
import input.SystemInput;
import particles.BombExplodeParticleList;
import ui.gui.buffs.BuffListGui;
import ui.gui.game.AnnouncementGui;
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
        SystemInput.setActive(true);
        Environment.initialize();
        Map.initialize(level, false);
        PlayerInput.initialize();
        InfoGuiList.initialize();
        AudioManager.bgm.play();
    }

    @Override
    public void update(float tpf) {
        BombList.onUpdate(tpf);
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
        AudioManager.bgm.stop();
        AnnouncementGui.remove();
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getLevel() {
        return level;
    }

    @Override
    public void restart() {
        InfoGuiList.remove();
        InfoGuiList.initialize();
        BuffListGui.reLocateBuffGUI();
    }
}
