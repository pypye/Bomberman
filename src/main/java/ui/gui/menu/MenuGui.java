package ui.gui.menu;

import cores.Main;
import ui.gui.ButtonGui;
import ui.gui.ImageGui;
import ui.gui.ItemGui;
import ui.gui.LocationGui;


public class MenuGui {
    public MenuGui() {
        ItemGui background = new ImageGui(Main.WIDTH, Main.HEIGHT, "Textures/Menu/Lobby-Background.png");
        ItemGui logo = new ImageGui(512, 206, "Textures/Menu/logo.png");
        LocationGui.centerXObject(logo, background, Main.HEIGHT - 400);
        ButtonGui btnPlay = new ButtonGui(-1, -1, "Play", 200, 50);
        ButtonGui btnSettings = new ButtonGui(-1, -1, "Settings", 200, 50);
        ButtonGui btnExit = new ButtonGui(-1, -1, "Exit", 200, 50);
        LocationGui.centerXObject(btnPlay, background, Main.HEIGHT - 425);
        LocationGui.centerXObject(btnSettings, background, Main.HEIGHT - 485);
        LocationGui.centerXObject(btnExit, background, Main.HEIGHT - 545);
        background.show();
        logo.show();
        btnPlay.show();
        btnSettings.show();
        btnExit.show();
    }
}
