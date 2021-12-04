package ui.gui.settings;

import com.jme3.math.ColorRGBA;
import scenes.SceneController;
import ui.gui.ImageButtonGui;
import ui.gui.ImageGui;
import ui.gui.LocationGui;
import ui.gui.TextGui;
import ui.gui.menu.MenuGui;

public class SettingGui {
    private static ImageGui background;
    private static ImageButtonGui close;
    private static TextGui settingText;
    private static TextGui resolutionText;
    private static TextGui vSyncText;
    private static TextGui fullscreenText;
    private static TextGui bgmVolumeText;
    private static TextGui effectVolumeText;
    private static boolean enabled;

    public static void initialize() {
        background = new ImageGui(1180, 620, (1280 - 1180) / 2f, (720 - 620) / 2f, "Textures/Setting/mainwindow.png");
        close = new ImageButtonGui(32, 32, -1, -1, "Textures/Setting/X.png") {
            @Override
            public void onClick() {
                SettingGui.remove();
            }
        };
        settingText = new TextGui("Settings", ColorRGBA.White, -1, -1, 32);
        resolutionText = new TextGui("Resolution: ", ColorRGBA.White, -1, -1);
        vSyncText = new TextGui("Vsync: ", ColorRGBA.White, -1, -1);
        fullscreenText = new TextGui("Fullscreen: ", ColorRGBA.White, -1, -1);
        bgmVolumeText = new TextGui("BGM Volume: ", ColorRGBA.White, -1, -1);
        effectVolumeText = new TextGui("Effect Volume: ", ColorRGBA.White, -1, -1);
        LocationGui.anchorTopRightObject(close, background, 16, 16);
        LocationGui.anchorTopLeftObject(settingText, background, 32, 0);
        LocationGui.anchorTopLeftObject(resolutionText, background, 32, 80);
        LocationGui.anchorTopLeftObject(vSyncText, background, 32, 120);
        LocationGui.anchorTopLeftObject(fullscreenText, background, 32, 160);
        LocationGui.anchorTopLeftObject(bgmVolumeText, background, 32, 200);
        LocationGui.anchorTopLeftObject(effectVolumeText, background, 32, 240);
        show();
        enabled = true;
        SceneController.getCurrentScene().setActive(false);
    }
    private static void show(){
        background.show();
        close.show();
        settingText.show();
        resolutionText.show();
        vSyncText.show();
        fullscreenText.show();
        bgmVolumeText.show();
        effectVolumeText.show();
    }

    public static void remove() {
        background.remove();
        close.remove();
        settingText.remove();
        resolutionText.remove();
        vSyncText.remove();
        fullscreenText.remove();
        bgmVolumeText.remove();
        effectVolumeText.remove();
        enabled = false;
        SceneController.getCurrentScene().setActive(true);
    }

    public static boolean isEnabled() {
        return enabled;
    }
}
