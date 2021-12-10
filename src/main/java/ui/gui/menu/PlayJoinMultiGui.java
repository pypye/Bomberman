package ui.gui.menu;

import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector2f;
import cores.Config;
import scenes.Game;
import scenes.SceneController;
import ui.gui.*;

import java.awt.*;
import java.util.ArrayList;

public class PlayJoinMultiGui {
    private static ImageGui filter, background;
    private static ImageButtonGui close;
    private static TextGui playText;
    private static ButtonGui returnBtn;
    private static final ArrayList<TextGui> roomTextList = new ArrayList<>();
    private static final ArrayList<ImageGui> bgList = new ArrayList<>();
    private static final ArrayList<ButtonGui> btnList = new ArrayList<>();
    private static String id;

    public static void initialize(String id) {
        PlayJoinMultiGui.id = id;
        filter = new ImageGui(new Vector2f(Config.WIDTH, Config.HEIGHT), "Textures/Menu/announcement_background.png");
        background = new ImageGui(new Vector2f(100, 100), new Vector2f((Config.WIDTH - 200), (Config.HEIGHT - 200)), "Textures/Settings/mainwindow.png");
        close = new ImageButtonGui(new Vector2f(), new Vector2f(32, 32), "Textures/Settings/X.png") {
            @Override
            public void onClick() {
                PlayJoinMultiGui.remove();
            }
        };
        playText = new TextGui("Find room", ColorRGBA.White, 32);
        LocationGui.anchorTopRightObject(close, background, 16, 16);
        LocationGui.anchorTopLeftObject(playText, background, 64, 0);

        returnBtn = new ButtonGui("Return to main menu", new Vector2f(), new Vector2f(200, 50)) {
            @Override
            public void onClick() {
                PlayJoinMultiGui.remove();
            }
        };
        LocationGui.anchorBottomRightObject(returnBtn, background, 32, 16);
        show();
    }

    public static void onUpdate(ArrayList<String> players) {
        for (ImageGui bg : bgList) bg.remove();
        for (TextGui text : roomTextList) text.remove();
        for (ButtonGui btn : btnList) btn.remove();
        bgList.clear();
        roomTextList.clear();
        btnList.clear();
        for (int i = 0; i < players.size(); i++) {
            create(80 + i * 100, players.get(i));
        }
        for (ImageGui bg : bgList) bg.show();
        for (TextGui text : roomTextList) text.show();
        for (ButtonGui btn : btnList) btn.show();
    }

    private static void create(int pos, String _text) {
        if (background == null)
            background = new ImageGui(new Vector2f(100, 100), new Vector2f((Config.WIDTH - 200), (Config.HEIGHT - 200)), "Textures/Settings/mainwindow.png");
        ImageGui bg = new ImageGui(new Vector2f(), new Vector2f(background.getSize().x - 128, 72), "Textures/Settings/part.png");
        LocationGui.anchorTopLeftObject(bg, background, 64, pos);
        TextGui text = new TextGui(_text, ColorRGBA.White);
        LocationGui.anchorTopLeftObject(text, bg, 16, 8);
        ButtonGui btn = new ButtonGui("Join", new Vector2f(), new Vector2f(100, 50)) {
            @Override
            public void onClick() {

            }
        };
        LocationGui.anchorTopRightObject(btn, bg, 32, 0);
        LocationGui.centerYObject(btn, bg, btn.getPosition().x);
        bgList.add(bg);
        roomTextList.add(text);
        btnList.add(btn);
    }

    private static void show() {
        filter.show();
        background.show();
        close.show();
        playText.show();
        for (ImageGui bg : bgList) bg.show();
        for (TextGui text : roomTextList) text.show();
        for (ButtonGui btn : btnList) btn.show();
        returnBtn.show();
    }

    public static void remove() {
        filter.remove();
        background.remove();
        close.remove();
        playText.remove();
        for (ImageGui bg : bgList) bg.remove();
        for (TextGui text : roomTextList) text.remove();
        for (ButtonGui btn : btnList) btn.remove();
        roomTextList.clear();
        returnBtn.remove();
        PlayGui.initialize();
    }
}
