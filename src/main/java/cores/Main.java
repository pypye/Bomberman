package cores;

import com.jme3.app.SimpleApplication;
import com.jme3.asset.AssetManager;
import com.jme3.asset.plugins.FileLocator;
import com.jme3.input.InputManager;
import com.jme3.math.ColorRGBA;
import com.jme3.renderer.Camera;
import com.jme3.scene.Node;
import com.jme3.system.AppSettings;
import entities.bombs.BombList;
import entities.players.PlayerList;

public class Main extends SimpleApplication {
    public static AssetManager ASSET_MANAGER;
    public static InputManager INPUT_MANAGER;
    public static Camera CAM;
    public static Node ROOT_NODE;

    public static void main(String[] args) {
        Main app = new Main();
        AppSettings settings = new AppSettings(true);
        settings.setResolution(1280, 720);
        settings.setTitle("Bomberman");
        app.setSettings(settings);
        app.setShowSettings(false);
        app.setDisplayStatView(false);
        app.start();
    }

    @Override
    public void simpleInitApp() {
        ASSET_MANAGER = assetManager;
        INPUT_MANAGER = inputManager;
        ROOT_NODE = rootNode;
        CAM = cam;
        flyCam.setEnabled(false);
        assetManager.registerLocator("assets", FileLocator.class);
        Ambient.init();
        Map.init();
        PlayerList.init();
    }

    @Override
    public void simpleUpdate(float tpf) {
        BombList.onUpdate();
    }

}