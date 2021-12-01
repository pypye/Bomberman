package ui.gui.menu;

import cores.Main;
import scenes.Game;
import scenes.SceneController;
import ui.gui.ButtonGui;
import ui.gui.ImageGui;
import ui.gui.LocationGui;

public class MenuGui {
    private static ImageGui background;
    private static ImageGui logo;
    private static ButtonGui btnPlay;
    private static ButtonGui btnAchievement;
    private static ButtonGui btnSettings;
    private static ButtonGui btnExit;

    public static void initialize() {
        background = new ImageGui(Main.WIDTH, Main.HEIGHT, "Textures/Menu/Lobby-Background.png");
        logo = new ImageGui(512, 206, "Textures/Menu/logo.png");
        LocationGui.centerXObject(logo, background, Main.HEIGHT - 400);
        btnPlay = new ButtonGui(-1, -1, "Play", 200, 50) {
            @Override
            public void onClick() {
                SceneController.setScene(new Game(1));
            }
        };
        btnAchievement = new ButtonGui(-1, -1, "Achievement", 200, 50) {
            @Override
            public void onClick() {}
        };
        btnSettings = new ButtonGui(-1, -1, "Settings", 200, 50) {
            @Override
            public void onClick() {}
        };
        btnExit = new ButtonGui(-1, -1, "Exit", 200, 50) {
            @Override
            public void onClick() {}
        };
        LocationGui.centerXObject(btnPlay, background, Main.HEIGHT - 425);
        LocationGui.centerXObject(btnAchievement, background, Main.HEIGHT - 485);
        LocationGui.centerXObject(btnSettings, background, Main.HEIGHT - 545);
        LocationGui.centerXObject(btnExit, background, Main.HEIGHT - 605);
        MenuGui.show();
    }

    public static void show() {
        background.show();
        logo.show();
        btnPlay.show();
        btnAchievement.show();
        btnSettings.show();
        btnExit.show();
    }

    public static void remove() {
        background.remove();
        logo.remove();
        btnPlay.remove();
        btnAchievement.remove();
        btnSettings.remove();
        btnExit.remove();
    }
}
