package ui.gui.menu;

import com.jme3.math.Vector2f;
import cores.Config;
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
        background = new ImageGui(new Vector2f(Config.WIDTH, Config.HEIGHT), "Textures/Menu/Lobby-Background.png");
        logo = new ImageGui(new Vector2f(512, 206), "Textures/Menu/logo.png");
        LocationGui.centerXObject(logo, background, Config.HEIGHT - 400);
        btnPlay = new ButtonGui("Play", new Vector2f(), new Vector2f(200, 50)) {
            @Override
            public void onClick() {
                PlayGui.initialize();
                MenuGui.setActive(false);
                //SceneController.setScene(new Game(1));
            }
        };
        btnAchievement = new ButtonGui("Achievement", new Vector2f(), new Vector2f(200, 50)) {
            @Override
            public void onClick() {
                AchievementGui.initialize();
                MenuGui.setActive(false);
            }
        };
        btnSettings = new ButtonGui("Settings", new Vector2f(), new Vector2f(200, 50)) {
            @Override
            public void onClick() {
                SettingGui.initialize();
                MenuGui.setActive(false);
            }
        };
        btnExit = new ButtonGui("Exit", new Vector2f(), new Vector2f(200, 50)) {
            @Override
            public void onClick() {
                Main.APP.stop();
            }
        };
        LocationGui.centerXObject(btnPlay, background, Config.HEIGHT - 425);
        LocationGui.centerXObject(btnAchievement, background, Config.HEIGHT - 485);
        LocationGui.centerXObject(btnSettings, background, Config.HEIGHT - 545);
        LocationGui.centerXObject(btnExit, background, Config.HEIGHT - 605);
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

    public static void setActive(boolean active) {
        btnPlay.setActive(active);
        btnAchievement.setActive(active);
        btnSettings.setActive(active);
        btnExit.setActive(active);
    }
}
