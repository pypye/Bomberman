package entities;

import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;
import cores.Main;

public abstract class Entity {
    protected Spatial spatial;
    protected int id;
    private static int idCount = 0;
    public static final float BLOCK_SIZE = 2f;

    public Entity(Vector3f position, String path) {
        Spatial spatial = Main.ASSET_MANAGER.loadModel(path);
        spatial.setLocalTranslation(position.x, position.y, position.z);
        Main.ROOT_NODE.attachChild(spatial);
        this.spatial = spatial;
        idCount += 1;
        id = idCount;
    }

    public void setPosition(Vector3f position) {
        spatial.setLocalTranslation(position.x, position.y, position.z);
    }

    public Vector3f getPosition() {
        return spatial.getLocalTranslation();
    }

    public Spatial getSpatial() {
        return spatial;
    }

    public void remove() {
        Main.ROOT_NODE.detachChild(spatial);
        spatial = null;
    }
}
