package ui.gui.menu;

import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector2f;
import cores.Config;
import scenes.SceneController;
import ui.gui.*;

public class AchievementGui {
    private static ImageGui filter, background;
    private static ImageButtonGui close;
    private static TextGui achievementText, highestLevelText, timePlayedText;
    private static ButtonGui returnBtn;
    private static boolean enabled;

    public static void initialize() {
        filter = new ImageGui(new Vector2f(Config.WIDTH, Config.HEIGHT), "Textures/Menu/announcement_background.png");
        background = new ImageGui(new Vector2f(100, 100), new Vector2f((Config.WIDTH - 200), (Config.HEIGHT - 200)), "Textures/Settings/mainwindow.png");
        close = new ImageButtonGui(new Vector2f(), new Vector2f(32, 32), "Textures/Settings/X.png") {
            @Override
            public void onClick() {
                AchievementGui.remove();
            }
        };
        achievementText = new TextGui("Achievement", ColorRGBA.White, 32);
        highestLevelText = new TextGui("Highest Level Played: " + Config.LEVEL_PLAYED, ColorRGBA.White);
        timePlayedText = new TextGui("With Time elapsed: " + Config.TIME_PLAYED + "s", ColorRGBA.White);

        LocationGui.anchorTopRightObject(close, background, 16, 16);
        LocationGui.anchorTopLeftObject(achievementText, background, 64, 0);
        LocationGui.anchorTopLeftObject(highestLevelText, background, 64, 80);
        LocationGui.anchorTopLeftObject(timePlayedText, background, 64, 120);

        returnBtn = new ButtonGui("Return to main menu", new Vector2f(), new Vector2f(200, 50)) {
            @Override
            public void onClick() {
                AchievementGui.remove();
            }
        };
        LocationGui.anchorBottomRightObject(returnBtn, background, 32, 32);
        show();
        enabled = true;
        SceneController.getCurrentScene().setActive(false);
    }

    private static void show() {
        filter.show();
        background.show();
        close.show();
        achievementText.show();
        highestLevelText.show();
        timePlayedText.show();
        returnBtn.show();
    }

    public static void remove() {
        filter.remove();
        background.remove();
        close.remove();
        achievementText.remove();
        highestLevelText.remove();
        timePlayedText.remove();
        returnBtn.remove();
        enabled = false;
        SceneController.getCurrentScene().setActive(true);
    }

    public static boolean isEnabled() {
        return enabled;
    }
}
