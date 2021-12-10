package socket;

import cores.Debugger;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import org.json.JSONArray;
import org.json.JSONException;
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
