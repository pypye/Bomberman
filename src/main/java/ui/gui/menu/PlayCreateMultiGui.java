package ui.gui.menu;

import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector2f;
import cores.Config;
import ui.gui.*;

public class PlayCreateMultiGui {
    private static ImageGui filter, background;
    private static ImageButtonGui close;
    private static TextGui playText;
    private static ButtonGui returnBtn;
    public static void initialize(String id) {
        filter = new ImageGui(new Vector2f(Config.WIDTH, Config.HEIGHT), "Textures/Menu/announcement_background.png");
        background = new ImageGui(new Vector2f(100, 100), new Vector2f((Config.WIDTH - 200), (Config.HEIGHT - 200)), "Textures/Settings/mainwindow.png");
        close = new ImageButtonGui(new Vector2f(), new Vector2f(32, 32), "Textures/Settings/X.png") {
            @Override
            public void onClick() {
                PlayCreateMultiGui.remove();
            }
        };
        playText = new TextGui("room_" + id, ColorRGBA.White, 32);
        LocationGui.anchorTopRightObject(close, background, 16, 16);
        LocationGui.anchorTopLeftObject(playText, background, 64, 0);

        returnBtn = new ButtonGui("Return to main menu", new Vector2f(), new Vector2f(200, 50)) {
            @Override
            public void onClick() {
                PlayCreateMultiGui.remove();
            }
        };
        LocationGui.anchorBottomRightObject(returnBtn, background, 32, 16);
        show();
    }

    private static void show() {
        filter.show();
        background.show();
        close.show();
        playText.show();

        returnBtn.show();
    }

    public static void remove() {
        filter.remove();
        background.remove();
        close.remove();
        playText.remove();
        returnBtn.remove();
        PlayGui.initialize();
    }
}
