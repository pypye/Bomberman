package scenes;

import ui.gui.menu.MenuGui;

public class Menu extends Scene {
    @Override
    public void show() {
        MenuGui.init();
    }

    @Override
    public void update(float tpf) {

    }

    @Override
    public void hide() {
        MenuGui.hide();
    }
}
