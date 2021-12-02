package cores;

import audio.AudioManager;
import com.jme3.app.SimpleApplication;
import com.jme3.asset.AssetManager;
import com.jme3.asset.plugins.FileLocator;
import com.jme3.input.InputManager;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.Node;
import com.jme3.system.AppSettings;
import entities.players.Player;
import entities.players.PlayerList;
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
        SceneController.setScene(new Menu());
        //AudioManager.explosion.getAudio().setLocalTranslation(0, 1, 0);

        //AudioManager.explosion.getAudio().play();
        //Audio a = new LobbyAudio();
        //a.getAudio().play();
    }

    @Override
    public void simpleUpdate(float tpf) {
        SceneController.update(tpf);
    }
}