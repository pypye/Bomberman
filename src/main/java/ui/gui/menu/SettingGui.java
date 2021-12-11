package ui.gui.menu;

import audio.AudioManager;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector2f;
import cores.Config;
import cores.Main;
import org.lwjgl.opengl.Display;
import scenes.Game;
import scenes.GameAI;
import scenes.Menu;
import scenes.SceneController;
import ui.gui.*;

import java.awt.*;
import java.util.Arrays;

public class SettingGui {
    private static ImageGui filter, background;
    private static ImageButtonGui close;
    private static TextGui settingText, resolutionText, fullscreenText, bgmVolumeText, effectVolumeText;
    private static ButtonGui applyBtn, returnBtn;
    private static SliderGui resolutionSlider, fullscreenSlider, bgmVolumeSlider, effectVolumeSlider;
    private static boolean enabled;

    public static void initialize() {
        filter = new ImageGui(new Vector2f(Config.WIDTH, Config.HEIGHT), "Textures/Menu/announcement_background.png");
        background = new ImageGui(new Vector2f(100, 100), new Vector2f((Config.WIDTH - 200), (Config.HEIGHT - 200)), "Textures/Settings/mainwindow.png");
        close = new ImageButtonGui(new Vector2f(), new Vector2f(32, 32), "Textures/Settings/X.png") {
            @Override
            public void onClick() {
                SettingGui.remove();
            }
        };
        settingText = new TextGui("Settings", ColorRGBA.White, 32);
        resolutionText = new TextGui("Resolution: ", ColorRGBA.White);
        fullscreenText = new TextGui("Fullscreen: ", ColorRGBA.White);
        bgmVolumeText = new TextGui("BGM Volume: ", ColorRGBA.White);
        effectVolumeText = new TextGui("Effect Volume: ", ColorRGBA.White);

        LocationGui.anchorTopRightObject(close, background, 16, 16);
        LocationGui.anchorTopLeftObject(settingText, background, 64, 0);
        LocationGui.anchorTopLeftObject(resolutionText, background, 64, 80);
        LocationGui.anchorTopLeftObject(fullscreenText, background, 64, 160);
        LocationGui.anchorTopLeftObject(bgmVolumeText, background, 64, 240);
        LocationGui.anchorTopLeftObject(effectVolumeText, background, 64, 320);

        resolutionSlider = new SliderGui(new Vector2f(), Arrays.asList("1024x768", "1280x720", "1366x768", "1600x900"), Main.APP_SETTINGS.getWidth() + "x" + Main.APP_SETTINGS.getHeight());
        fullscreenSlider = new SliderGui(new Vector2f(), Arrays.asList("Disabled", "Enabled"), Main.APP_SETTINGS.isFullscreen() ? "Enabled" : "Disabled");
        bgmVolumeSlider = new SliderGui(new Vector2f(), Arrays.asList("0.0", "0.1", "0.2", "0.3", "0.4", "0.5", "0.6", "0.7", "0.8", "0.9", "1.0"), String.format("%.1f", AudioManager.getBGMVolume()));
        effectVolumeSlider = new SliderGui(new Vector2f(), Arrays.asList("0.0", "0.1", "0.2", "0.3", "0.4", "0.5", "0.6", "0.7", "0.8", "0.9", "1.0"), String.format("%.1f", AudioManager.getSFXVolume()));
        LocationGui.anchorTopLeftObject(resolutionSlider, resolutionText, 150, 20);
        LocationGui.anchorTopLeftObject(fullscreenSlider, fullscreenText, 150, 20);
        LocationGui.anchorTopLeftObject(bgmVolumeSlider, bgmVolumeText, 150, 20);
        LocationGui.anchorTopLeftObject(effectVolumeSlider, effectVolumeText, 150, 20);

        returnBtn = new ButtonGui("Return to main menu", new Vector2f(), new Vector2f(200, 50)) {
            @Override
            public void onClick() {
                if (SceneController.getCurrentScene() instanceof Menu) {
                    SettingGui.remove();
                } else if (SceneController.getCurrentScene() instanceof Game) {
                    SettingGui.remove();
                    SceneController.setScene(new Menu());
                } else if (SceneController.getCurrentScene() instanceof GameAI) {
                    SettingGui.remove();
                    SceneController.setScene(new Menu());
                }
            }
        };
        applyBtn = new ButtonGui("Apply", new Vector2f(), new Vector2f(200, 50)) {
            @Override
            public void onClick() {
                String[] resolution = resolutionSlider.getCurrentOption().split("x");
                Config.WIDTH = Integer.parseInt(resolution[0]);
                Config.HEIGHT = Integer.parseInt(resolution[1]);
                Config.FULLSCREEN = fullscreenSlider.getCurrentOption().equals("Enabled") ? 1 : 0;
                Config.BGM_VOLUME = Float.parseFloat(bgmVolumeSlider.getCurrentOption());
                Config.SFX_VOLUME = Float.parseFloat(effectVolumeSlider.getCurrentOption());
                Main.APP_SETTINGS.setWidth(Config.WIDTH);
                Main.APP_SETTINGS.setHeight(Config.HEIGHT);
                Main.APP_SETTINGS.setFullscreen(Config.FULLSCREEN != 0);
                Main.APP.setSettings(Main.APP_SETTINGS);
                GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
                int width = gd.getDisplayMode().getWidth();
                int height = gd.getDisplayMode().getHeight();
                Display.setLocation((width - Config.WIDTH) / 2, (height - Config.HEIGHT) / 2 - 25);
                Main.APP.restart();
                SceneController.getCurrentScene().restart();
                AudioManager.setBGMVolume(Config.BGM_VOLUME);
                AudioManager.setSFXVolume(Config.SFX_VOLUME);
                SettingGui.remove();
            }
        };
        LocationGui.anchorBottomRightObject(returnBtn, background, 242, 32);
        LocationGui.anchorBottomRightObject(applyBtn, background, 32, 32);
        show();
        enabled = true;
        SceneController.getCurrentScene().setActive(false);
    }

    private static void show() {
        filter.show();
        background.show();
        close.show();
        settingText.show();
        resolutionText.show();
        fullscreenText.show();
        bgmVolumeText.show();
        effectVolumeText.show();

        resolutionSlider.show();
        fullscreenSlider.show();
        bgmVolumeSlider.show();
        effectVolumeSlider.show();

        returnBtn.show();
        applyBtn.show();
    }

    public static void remove() {
        filter.remove();
        background.remove();
        close.remove();
        settingText.remove();
        resolutionText.remove();
        fullscreenText.remove();
        bgmVolumeText.remove();
        effectVolumeText.remove();

        resolutionSlider.remove();
        fullscreenSlider.remove();
        bgmVolumeSlider.remove();
        effectVolumeSlider.remove();

        returnBtn.remove();
        applyBtn.remove();
        enabled = false;
        SceneController.getCurrentScene().setActive(true);
    }

    public static boolean isEnabled() {
        return enabled;
    }
}
