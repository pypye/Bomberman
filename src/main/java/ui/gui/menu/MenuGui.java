package ui.gui.menu;

import cores.Main;
import ui.gui.ImageGui;
import ui.gui.ItemGui;

public class MenuGui {
    public MenuGui() {
        ItemGui background = new ImageGui(Main.WIDTH, Main.HEIGHT, "Textures/Menu/Lobby-Background.png");
        background.show();
    }
}
