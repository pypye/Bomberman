package cores;

import audio.AudioManager;
import com.jme3.app.SimpleApplication;
import com.jme3.asset.AssetManager;
import com.jme3.asset.plugins.FileLocator;
import com.jme3.input.InputManager;
import com.jme3.renderer.Camera;
import com.jme3.scene.Node;
import com.jme3.system.AppSettings;
import input.SystemInput;
import io.socket.emitter.Emitter;
import socket.SocketIO;
import org.lwjgl.openal.AL10;
import org.lwjgl.opengl.Display;
import scenes.Menu;
import scenes.SceneController;

import java.awt.*;
import java.util.Arrays;

public class Main extends SimpleApplication {
    public static AssetManager ASSET_MANAGER;
    public static InputManager INPUT_MANAGER;
    public static Camera CAM;
    public static Node ROOT_NODE;
    public static Node GUI_NODE;
    public static Main APP;
    public static AppSettings APP_SETTINGS;

    public static void main(String[] args) {
        Config.importConfig();
        APP = new Main();
        APP_SETTINGS = new AppSettings(true);
        APP_SETTINGS.setResolution(Config.WIDTH, Config.HEIGHT);
        APP_SETTINGS.setVSync(true);
        APP_SETTINGS.setFullscreen(Config.FULLSCREEN != 0);
        APP_SETTINGS.setTitle("Bomberman");
        APP_SETTINGS.setFrameRate(60);
        APP.setSettings(APP_SETTINGS);
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        int width = gd.getDisplayMode().getWidth();
        int height = gd.getDisplayMode().getHeight();
        Display.setLocation((width - Config.WIDTH) / 2, (height - Config.HEIGHT) / 2 - 25);
        APP.setShowSettings(false);
        APP.setDisplayFps(false);
        APP.setDisplayStatView(false);
        APP.start();
    }

    @Override
    public void simpleInitApp() {
        AL10.alSourcef(1, AL10.AL_GAIN, 5f);
        ASSET_MANAGER = assetManager;
        INPUT_MANAGER = inputManager;
        ROOT_NODE = rootNode;
        GUI_NODE = guiNode;
        CAM = cam;
        flyCam.setEnabled(false);
        assetManager.registerLocator("assets", FileLocator.class);
        SystemInput.initialize();
        AudioManager.initialize();
        Debugger.initialize(true);
        SceneController.setScene(new Menu());
        SocketIO.initialize();
        SocketIO.getSocket().on("sendAllRoomExists", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                System.out.println(args[0]);
            }
        });
        SocketIO.getSocket().on("notification", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                System.out.println(args[0]);
            }
        });
    }

    @Override
    public void simpleUpdate(float tpf) {
        SceneController.update(tpf);
    }

    @Override
    public void destroy() {
        Debugger.log(Debugger.EVENT, "Application closed");
        Config.exportConfig();
        super.destroy();
    }
}