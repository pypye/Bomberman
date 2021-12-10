package ui.gui.menu;

import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector2f;
import cores.Config;
import socket.SocketIO;
import ui.gui.*;

import java.util.ArrayList;

public class PlayCreateMultiGui {
    private static ImageGui filter, background;
    private static ImageButtonGui close;
    private static TextGui playText;
    private static ButtonGui returnBtn;
    private static final ArrayList<TextGui> playerTextList = new ArrayList<>();
    private static String id, roomId;

    public static void initialize(String id, String roomId) {
        PlayCreateMultiGui.id = id;
        PlayCreateMultiGui.roomId = roomId;
        filter = new ImageGui(new Vector2f(Config.WIDTH, Config.HEIGHT), "Textures/Menu/announcement_background.png");
        background = new ImageGui(new Vector2f(100, 100), new Vector2f((Config.WIDTH - 200), (Config.HEIGHT - 200)), "Textures/Settings/mainwindow.png");
        close = new ImageButtonGui(new Vector2f(), new Vector2f(32, 32), "Textures/Settings/X.png") {
            @Override
            public void onClick() {
                PlayCreateMultiGui.remove();
            }
        };
        playText = new TextGui(roomId, ColorRGBA.White, 32);
        LocationGui.anchorTopRightObject(close, background, 16, 16);
        LocationGui.anchorTopLeftObject(playText, background, 64, 0);

        returnBtn = new ButtonGui("Return to main menu", new Vector2f(), new Vector2f(200, 50)) {
            @Override
            public void onClick() {
                SocketIO.getSocket().emit("leave", id, roomId);
                PlayCreateMultiGui.remove();
            }
        };
        LocationGui.anchorBottomRightObject(returnBtn, background, 32, 16);
        show();
    }

    public static void onUpdate(ArrayList<String> players) {
        for (TextGui text : playerTextList) {
            text.remove();
        }
        playerTextList.clear();
        for (int i = 0; i < players.size(); i++) {
            if (players.get(i).equals(PlayCreateMultiGui.id)) {
                playerTextList.add(new TextGui(players.get(i) + " (You)", ColorRGBA.White));
            } else playerTextList.add(new TextGui(players.get(i), ColorRGBA.White));
            LocationGui.anchorTopLeftObject(playerTextList.get(i), background, 64, 80 + (i * 20));
        }
        for (TextGui text : playerTextList) {
            text.show();
        }
    }

    private static void show() {
        filter.show();
        background.show();
        close.show();
        playText.show();
        for (TextGui text : playerTextList) {
            text.show();
        }
        returnBtn.show();
    }

    public static void remove() {
        filter.remove();
        background.remove();
        close.remove();
        playText.remove();
        for (TextGui text : playerTextList) {
            text.remove();
        }
        playerTextList.clear();
        returnBtn.remove();
        PlayGui.initialize();
    }
}
