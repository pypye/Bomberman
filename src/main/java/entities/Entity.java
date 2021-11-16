package entities;

import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;
import cores.Main;

public abstract class Entity {

    protected Spatial spatial;
    protected boolean blocked = false;
    protected int id;
    private static int ID_COUNT = 0;
    public static final float BLOCK_SIZE = 2f;

    public Entity(Vector3f position, String path) {
        spatial = Main.ASSET_MANAGER.loadModel(path);
        spatial.setLocalTranslation(position.x, position.y, position.z);
        Main.ROOT_NODE.attachChild(spatial);
        id = ++ID_COUNT;
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

    public Vector2f getCord() {
        int cordX = (int) ((getPosition().x + Entity.BLOCK_SIZE / 2) / Entity.BLOCK_SIZE);
        int cordZ = (int) ((getPosition().z + Entity.BLOCK_SIZE / 2) / Entity.BLOCK_SIZE);
        return new Vector2f(cordX, cordZ);
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public void remove() {
        Main.ROOT_NODE.detachChild(spatial);
        spatial = null;
    }
}
