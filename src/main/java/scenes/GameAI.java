package scenes;

import audio.AudioManager;
import cores.Debugger;
import cores.Environment;
import cores.Map;
import entities.bombs.BombList;
import entities.players.PlayerList;
import input.SystemInput;
import particles.BombExplodeParticleList;
import ui.gui.buffs.BuffListGui;
import ui.gui.game.InfoGuiList;

public class GameAI extends Scene{
    private int level;

    public GameAI(int level) {
        this.level = level;
    }


    @Override
    public void show() {
        Debugger.log(Debugger.GAME, "Init scene with level = " + level);
        setActive(true);
        SystemInput.setActive(true);
        Environment.initialize();
        Map.initialize(level, true);
        InfoGuiList.initialize();
        AudioManager.bgm.play();
    }

    @Override
    public void update(float tpf) {
        BombList.onUpdate(tpf);
        BombExplodeParticleList.onUpdate();
        PlayerList.onUpdate(tpf);
        InfoGuiList.onUpdate(tpf);
    }

    @Override
    public void remove() {
        setActive(false);
        InfoGuiList.remove();
        PlayerList.removeAll();
        BombList.removeAll();
        Map.remove();
        Environment.remove();
        AudioManager.bgm.stop();
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getLevel() {
        return level;
    }

    @Override
    public void restart(){
        InfoGuiList.remove();
        InfoGuiList.initialize();
        BuffListGui.reLocateBuffGUI();
    }
}
