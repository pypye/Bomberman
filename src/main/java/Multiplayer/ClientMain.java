package multiplayer;

import com.jme3.app.SimpleApplication;
import com.jme3.network.Client;
import com.jme3.network.Network;
import com.jme3.system.JmeContext;
import java.io.IOException;

public class ClientMain extends SimpleApplication {
    private Client myClient = null;

    public static void main(String[] args) {
        ClientMain app = new ClientMain();
        app.start(JmeContext.Type.Display); // standard display type

    }

    public void simpleInitApp() {

        try {
            myClient = Network.connectToServer("localhost", 6143);
        } catch (IOException e) {
            e.printStackTrace();
        }
        myClient.start();
    }

    @Override
    public void destroy() {
        myClient.close();
        super.destroy();
    }
}
