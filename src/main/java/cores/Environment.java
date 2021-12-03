package cores;

import com.jme3.light.AmbientLight;
import com.jme3.scene.Spatial;
import com.jme3.texture.Texture;
import com.jme3.util.SkyFactory;

public class Environment {
    private static Spatial sky;
    private static AmbientLight light;

    public static void initialize() {
        Texture west = Main.ASSET_MANAGER.loadTexture("Textures/Skybox/l.png");
        Texture east = Main.ASSET_MANAGER.loadTexture("Textures/Skybox/r.png");
        Texture north = Main.ASSET_MANAGER.loadTexture("Textures/Skybox/f.png");
        Texture south = Main.ASSET_MANAGER.loadTexture("Textures/Skybox/b.png");
        Texture up = Main.ASSET_MANAGER.loadTexture("Textures/Skybox/t.png");
        Texture down = Main.ASSET_MANAGER.loadTexture("Textures/Skybox/d.png");
        light = new AmbientLight();
        sky = SkyFactory.createSky(Main.ASSET_MANAGER, west, east, north, south, up, down);
        Main.ROOT_NODE.attachChild(sky);
        Main.ROOT_NODE.addLight(light);
        Debugger.log(Debugger.ENVIRONMENT, "Environment initialized");
    }

    public static void remove() {
        Main.ROOT_NODE.detachChild(sky);
        Main.ROOT_NODE.removeLight(light);
    }
}
