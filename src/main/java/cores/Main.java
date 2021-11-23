package cores;

import com.jme3.app.SimpleApplication;
import com.jme3.asset.AssetManager;
import com.jme3.asset.plugins.FileLocator;
import com.jme3.input.InputManager;
import com.jme3.renderer.Camera;
import com.jme3.scene.Node;
import com.jme3.system.AppSettings;
import entities.bombs.BombList;
import entities.players.PlayerList;
import events.Input;
import events.PlayerInput;
import particles.BombExplodeParticleList;
import scenes.Game;
import scenes.Menu;
import scenes.SceneController;
import ui.gui.ButtonGui;
import ui.gui.menu.MenuGui;


public class Main extends SimpleApplication {
    public static AssetManager ASSET_MANAGER;
    public static InputManager INPUT_MANAGER;
    public static Camera CAM;
    public static Node ROOT_NODE;
    public static Node GUI_NODE;
    public static int WIDTH = 1280;
    public static int HEIGHT = 720;

    public static void main(String[] args) {
        Main app = new Main();
        AppSettings settings = new AppSettings(true);
        settings.setResolution(WIDTH, HEIGHT);
        settings.setTitle("Bomberman");
        settings.setFrameRate(0);
        app.setSettings(settings);
        app.setShowSettings(false);
        app.setDisplayFps(false);
        app.setDisplayStatView(false);
        app.start();
    }

    @Override
    public void simpleInitApp() {
        ASSET_MANAGER = assetManager;
        INPUT_MANAGER = inputManager;
        ROOT_NODE = rootNode;
        GUI_NODE = guiNode;
        CAM = cam;
        cam.setPlaneState(0);
        flyCam.setEnabled(false);
        assetManager.registerLocator("assets", FileLocator.class);
        Input.init();
        SceneController.setScene(new Game());
    }

    @Override
    public void simpleUpdate(float tpf) {
        SceneController.update(tpf);
    }
}