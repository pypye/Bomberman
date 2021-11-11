package cores;

import com.jme3.light.AmbientLight;
import com.jme3.texture.Texture;
import com.jme3.util.SkyFactory;

public class Ambient {
    public static void init() {
        Texture west    = Main.ASSET_MANAGER.loadTexture("Textures/Skybox/l.png");
        Texture east    = Main.ASSET_MANAGER.loadTexture("Textures/Skybox/r.png");
        Texture north   = Main.ASSET_MANAGER.loadTexture("Textures/Skybox/f.png");
        Texture south   = Main.ASSET_MANAGER.loadTexture("Textures/Skybox/b.png");
        Texture up      = Main.ASSET_MANAGER.loadTexture("Textures/Skybox/t.png");
        Texture down    = Main.ASSET_MANAGER.loadTexture("Textures/Skybox/d.png");
        Main.ROOT_NODE.attachChild(SkyFactory.createSky(Main.ASSET_MANAGER, west, east, north, south, up, down));
        Main.ROOT_NODE.addLight(new AmbientLight());
    }
}
