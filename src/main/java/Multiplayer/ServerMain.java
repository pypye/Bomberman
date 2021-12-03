package Multiplayer;

import com.jme3.app.SimpleApplication;
import com.jme3.asset.AssetManager;
import com.jme3.input.InputManager;
import com.jme3.network.Message;
import com.jme3.network.Network;
import com.jme3.network.Server;
import com.jme3.network.service.serializer.ServerSerializerRegistrationsService;
import com.jme3.renderer.Camera;
import com.jme3.scene.Node;

import com.jme3.system.JmeContext;
import java.io.IOException;

public class ServerMain extends SimpleApplication {
    Server myServer = null;
    public static void main(String[] args) {
        ServerMain app = new ServerMain();
        //simpleInitApp();
        app.start(JmeContext.Type.Headless);
    }

    @Override
    public void simpleInitApp() {

        try {
            myServer = Network.createServer(6143);
        } catch (IOException e) {
            e.printStackTrace();
        }
        HelloMessage.initializeSerializables();
        myServer.start();

        ServerSerializerRegistrationsService ssr = myServer.getServices().getService( ServerSerializerRegistrationsService.class );
        myServer.getServices().removeService( ssr );
        Message message = new HelloMessage("Welcome!");
        myServer.broadcast(message);
    }

    @Override
    public void destroy() {
        myServer.close();
        super.destroy();
    }

}