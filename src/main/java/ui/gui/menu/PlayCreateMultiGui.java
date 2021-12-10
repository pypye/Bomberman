package ui.gui.menu;

import algorithms.RandomizeMap;
import algorithms.Spawn;
import algorithms.SpawnPlayer;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector2f;
import cores.Config;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import socket.SocketIO;
import ui.gui.*;

import java.util.ArrayList;

public class PlayCreateMultiGui {
    private static ImageGui filter, background;
    private static ImageButtonGui close;
    private static TextGui playText;
    private static ButtonGui returnBtn, startGameBtn;
    private static final ArrayList<TextGui> playerTextList = new ArrayList<>();
    public static String id, roomId;

    public static void initialize(String id, String roomId) {
        PlayCreateMultiGui.id = id;
        PlayCreateMultiGui.roomId = roomId;
        filter = new ImageGui(new Vector2f(Config.WIDTH, Config.HEIGHT), "Textures/Menu/announcement_background.png");
        background = new ImageGui(new Vector2f(100, 100), new Vector2f((Config.WIDTH - 200), (Config.HEIGHT - 200)), "Textures/Settings/mainwindow.png");
        close = new ImageButtonGui(new Vector2f(), new Vector2f(32, 32), "Textures/Settings/X.png") {
            @Override
            public void onClick() {
                SocketIO.getSocket().emit("leave", id, roomId);
                PlayCreateMultiGui.remove();
                PlayGui.initialize();
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
                PlayGui.initialize();
            }
        };
        LocationGui.anchorBottomRightObject(returnBtn, background, 32, 32);
        if (("room_" + id).equals(roomId)) {
            startGameBtn = new ButtonGui("Start game", new Vector2f(), new Vector2f(200, 50)) {
                @Override
                public void onClick() {
                    //if (playerTextList.size() >= 2) {
                    Spawn spawn = new Spawn(1);
                    int[][] pre_map = spawn.getSpawnMap();
                    ArrayList<Vector2f> pre_player = SpawnPlayer.spawn(pre_map, playerTextList.size(), 2);
                    JSONArray map = new JSONArray();
                    for (int[] ints : pre_map) {
                        JSONArray row = new JSONArray();
                        for (int anInt : ints) {
                            row.put(anInt);
                        }
                        map.put(row);
                    }
                    JSONArray player = new JSONArray();
                    for (int i = 0; i < playerTextList.size(); i++) {
                        JSONObject playerObject = new JSONObject();
                        try {
                            if (playerTextList.get(i).getText().contains("(You)"))
                                playerObject.put("id", playerTextList.get(i).getText().substring(0, playerTextList.get(i).getText().indexOf("(You)")).trim());
                            else playerObject.put("id", playerTextList.get(i).getText());
                            playerObject.put("x", pre_player.get(i).x);
                            playerObject.put("y", pre_player.get(i).y);
                            player.put(playerObject);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    SocketIO.getSocket().emit("getStartGame", roomId, map, player);
                    //}
                }
            };
            LocationGui.anchorBottomRightObject(startGameBtn, background, 242, 32);
        }

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
        if (("room_" + id).equals(roomId)) {
            startGameBtn.show();
        }
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
        if (("room_" + id).equals(roomId)) {
            startGameBtn.remove();
        }

    }
}
