package multiplayer;

import cores.Debugger;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import java.net.URISyntaxException;

public class SocketIO {
    private static Socket socket;

    public static void initialize() {
        try {
            IO.Options options = IO.Options.builder().build();
            socket = IO.socket("ws://localhost:3000", options);
            socket.connect();
            socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    Debugger.log(Debugger.SOCKET, "Connected to server with id=" + socket.id());
                }
            });
            while (!socket.connected()) Thread.sleep(50);
        } catch (URISyntaxException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static Socket getSocket() {
        return socket;
    }
}
