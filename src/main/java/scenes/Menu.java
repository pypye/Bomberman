package scenes;

import audio.AudioManager;
import ui.gui.game.InfoGuiList;
import ui.gui.menu.MenuGui;

public class Menu extends Scene {
    @Override
    public void setActive(boolean active) {
        super.setActive(active);
        MenuGui.setActive(active);
    }

    @Override
    public void show() {
        MenuGui.initialize();
        InfoGuiList.setTimeCount(0);
        AudioManager.lobby.play();
    }

    @Override
    public void update(float tpf) {
    }

    @Override
    public void remove() {
        MenuGui.remove();
        AudioManager.lobby.stop();
    }
    @Override
    public void restart(){
        MenuGui.remove();
        MenuGui.initialize();
    }
}
