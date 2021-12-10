package scenes;

import audio.AudioManager;
import cores.Environment;
import cores.Map;
import entities.bombs.BombList;
import entities.players.PlayerList;
import input.PlayerInput;
import input.SystemInput;
import org.json.JSONArray;
import particles.BombExplodeParticleList;
import ui.gui.buffs.BuffListGui;
import ui.gui.game.InfoGuiList;

public class GameMulti extends Scene{
    private final JSONArray map;
    private final JSONArray players;
    public GameMulti(JSONArray map, JSONArray players) {
        this.map = map;
        this.players = players;
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
    public void show() {
        setActive(true);
        SystemInput.setActive(true);
        Environment.initialize();
        Map.initializeMulti(map, players);
        PlayerInput.initialize();
        InfoGuiList.initialize();
        AudioManager.bgm.play();
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
    }

    @Override
    public void restart() {
        InfoGuiList.remove();
        InfoGuiList.initialize();
        BuffListGui.reLocateBuffGUI();
    }
}
