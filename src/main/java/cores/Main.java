package cores;

import com.jme3.app.SimpleApplication;
import com.jme3.asset.AssetManager;
import com.jme3.asset.plugins.FileLocator;
import com.jme3.input.InputManager;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.Node;
import com.jme3.system.AppSettings;
import entities.bombs.BombList;
import entities.players.MainPlayer;
import entities.players.enemies.Mushroom;
import entities.players.Player;
import entities.players.PlayerList;
import events.PlayerInput;
import particles.BombExplodeParticleList;

public class Main extends SimpleApplication {
    public static AssetManager ASSET_MANAGER;
    public static InputManager INPUT_MANAGER;
    public static Camera CAM;
    public static Node ROOT_NODE;
    public static Node GUI_NODE;
    public static float WIDTH;
    public static float HEIGHT;

    public static void main(String[] args) {
        Main app = new Main();
        //Logger.getLogger("com.jme3").setLevel(Level.OFF);
        AppSettings settings = new AppSettings(true);
        settings.setResolution(1280, 720);
        settings.setTitle("Bomberman");
        settings.setFrameRate(0);
        app.setSettings(settings);
        app.setShowSettings(false);
        app.setDisplayFps(false);
        app.setDisplayStatView(false);
        app.start();
    }
    //Player x;
    @Override
    public void simpleInitApp() {
        ASSET_MANAGER = assetManager;
        INPUT_MANAGER = inputManager;
        WIDTH = settings.getWidth();
        HEIGHT = settings.getHeight();
        ROOT_NODE = rootNode;
        GUI_NODE = guiNode;
        CAM = cam;
        cam.setPlaneState(0);
        flyCam.setEnabled(false);
        //flyCam.setMoveSpeed(2.5f);
        assetManager.registerLocator("assets", FileLocator.class);
        Environment.init();
        Map.init();

        //x = new Mushroom(new Vector3f(0, 1, 0));
        //PlayerList.add(x);
    }

    @Override
    public void simpleUpdate(float tpf) {
        BombList.onUpdate();
        BombExplodeParticleList.onUpdate();
        //x.moveRight(0.01f);
        PlayerInput.onUpdate();
        PlayerList.onUpdate(tpf);
    }

}