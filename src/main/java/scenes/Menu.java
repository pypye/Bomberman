package scenes;

import ui.gui.menu.MenuGui;

public class Menu extends Scene {
    @Override
    public void show() {
        MenuGui.initialize();
    }

    @Override
    public void update(float tpf) {
    }

    @Override
    public void remove() {
        MenuGui.remove();
    }
}
