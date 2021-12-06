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
import org.lwjgl.openal.AL10;
import scenes.Menu;
import scenes.SceneController;

public class Main extends SimpleApplication {
    public static AssetManager ASSET_MANAGER;
    public static InputManager INPUT_MANAGER;
    public static Camera CAM;
    public static Node ROOT_NODE;
    public static Node GUI_NODE;
    public static int WIDTH = 1366;
    public static int HEIGHT = 768;

    public static Main APP;
    public static AppSettings APP_SETTINGS;

    public static void main(String[] args) {
        APP = new Main();
        APP_SETTINGS = new AppSettings(true);
        APP_SETTINGS.setResolution(WIDTH, HEIGHT);
        APP_SETTINGS.setVSync(true);
        APP_SETTINGS.setTitle("Bomberman");
        APP_SETTINGS.setFrameRate(60);

        APP.setSettings(APP_SETTINGS);
        APP.setShowSettings(false);
        APP.setDisplayFps(true);
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
    }

    @Override
    public void simpleUpdate(float tpf) {
        SceneController.update(tpf);
    }
}