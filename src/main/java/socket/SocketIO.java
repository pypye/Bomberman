package socket;

import cores.Debugger;
import cores.Main;
import entities.players.PlayerList;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.lwjgl.Sys;
import scenes.GameMulti;
import scenes.SceneController;
import ui.gui.menu.PlayCreateMultiGui;
import ui.gui.menu.PlayJoinMultiGui;

import java.net.URISyntaxException;
import java.util.ArrayList;

public class SocketIO {
    private static Socket socket;
    private static ArrayList<String> roomList, playerList;

    public static void initialize() {
        try {
            roomList = new ArrayList<>();
            playerList = new ArrayList<>();
            IO.Options options = IO.Options.builder().build();
            socket = IO.socket("ws://bomberman-server.herokuapp.com/", options);
            //socket = IO.socket("ws://localhost:3000", options);
            socket.connect();
            socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    Debugger.log(Debugger.SOCKET, "Connected to server with id=" + socket.id());
                }
            });
            socket.on("sendAllRoomExists", new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    try {
                        JSONArray jsonArray = (JSONArray) args[0];
                        roomList.clear();
                        for (int i = 0; i < jsonArray.length(); i++) roomList.add(jsonArray.getString(i));
                        PlayJoinMultiGui.onUpdate(roomList);
                        System.out.println(roomList);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            });
            socket.on("notification", new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    System.out.println(args[0]);
                }
            });
            socket.on("sendRoomList", new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    try {
                        JSONArray jsonArray = (JSONArray) args[0];
                        playerList.clear();
                        for (int i = 0; i < jsonArray.length(); i++) playerList.add(jsonArray.getString(i));
                        System.out.println(playerList);
                        PlayCreateMultiGui.onUpdate(playerList);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            });
            socket.on("sendStartGame", new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    System.out.println("Start game");
                    JSONArray map = (JSONArray) args[0];
                    JSONArray player = (JSONArray) args[1];
                    SceneController.trigger = true;
                    SceneController.map = map;
                    SceneController.player = player;
                }
            });
            socket.on("sendPlayerMove", new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    String id = (String) args[0];
                    if(id.equals(SocketIO.getSocket().id())) return;
                    String direction = (String) args[1];
                    float value = Float.parseFloat(String.valueOf(args[2]));
                    switch (direction) {
                        case "forward":
                            PlayerList.getPlayer(id).moveForward(value);
                        case "backward":
                            PlayerList.getPlayer(id).moveBackward(value);
                        case "left":
                            PlayerList.getPlayer(id).moveLeft(value);
                        case "right":
                            PlayerList.getPlayer(id).moveRight(value);
                        default:
                            break;
                    }

                }
            });
            long timeStart = System.currentTimeMillis();
            while (!socket.connected()) {
                Thread.sleep(50);
                if (System.currentTimeMillis() - timeStart > 5000) {
                    Debugger.log(Debugger.SOCKET, "Can't connect to server");
                    break;
                }
            }
        } catch (URISyntaxException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static Socket getSocket() {
        return socket;
    }
}
