package entities.players;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import utils.LightUtils;
import cores.Map;
import entities.Entity;

public class Player extends Entity {
    public static final float SPEED = 2f;
    public static final float OFFSET = 0.5f;
    protected float offsetAngle = FastMath.HALF_PI;

    public Player(Vector3f position, String path) {
        super(position, path);
        LightUtils.setSpatialLight(spatial);
    }

    public void moveForward(float value) {
        rotate(0);
        Vector3f v = this.getPosition();
        if (v.x + value * SPEED + OFFSET > 39.0f) return;
        int cordX = (int) ((v.x + value * SPEED + OFFSET + Entity.BLOCK_SIZE / 2) / Entity.BLOCK_SIZE);
        int cordZ1 = (int) ((v.z + OFFSET + Entity.BLOCK_SIZE / 2) / Entity.BLOCK_SIZE);
        int cordZ2 = (int) ((v.z - OFFSET + Entity.BLOCK_SIZE / 2) / Entity.BLOCK_SIZE);
        if (Map.isBlocked(cordX, cordZ1) || Map.isBlocked(cordX, cordZ2)) return;
        this.setPosition(new Vector3f(v.x + value * SPEED, v.y, v.z));
    }

    public void moveBackward(float value) {
        rotate(FastMath.PI);
        Vector3f v = this.getPosition();
        if (v.x - value * SPEED - OFFSET < -1.0f) return;
        int cordX = (int) ((v.x - value * SPEED - OFFSET + Entity.BLOCK_SIZE / 2) / Entity.BLOCK_SIZE);
        int cordZ1 = (int) ((v.z - OFFSET + Entity.BLOCK_SIZE / 2) / Entity.BLOCK_SIZE);
        int cordZ2 = (int) ((v.z + OFFSET + Entity.BLOCK_SIZE / 2) / Entity.BLOCK_SIZE);
        if (Map.isBlocked(cordX, cordZ1) || Map.isBlocked(cordX, cordZ2)) return;
        this.setPosition(new Vector3f(v.x - value * SPEED, v.y, v.z));

    }

    public void moveLeft(float value) {
        rotate(FastMath.HALF_PI);
        Vector3f v = this.getPosition();
        if (v.z - value * SPEED - OFFSET < -1.0f) return;
        int cordX1 = (int) ((v.x - OFFSET + Entity.BLOCK_SIZE / 2) / Entity.BLOCK_SIZE);
        int cordX2 = (int) ((v.x + OFFSET + Entity.BLOCK_SIZE / 2) / Entity.BLOCK_SIZE);
        int cordZ = (int) ((v.z - value * SPEED - OFFSET + Entity.BLOCK_SIZE / 2) / Entity.BLOCK_SIZE);
        if (Map.isBlocked(cordX1, cordZ) || Map.isBlocked(cordX2, cordZ)) return;
        this.setPosition(new Vector3f(v.x, v.y, v.z - value * SPEED));
    }

    public void moveRight(float value) {
        rotate(-FastMath.HALF_PI);
        Vector3f v = this.getPosition();
        if (v.z + value * SPEED + OFFSET > 39.0f) return;
        int cordX1 = (int) ((v.x + OFFSET + Entity.BLOCK_SIZE / 2) / Entity.BLOCK_SIZE);
        int cordX2 = (int) ((v.x - OFFSET + Entity.BLOCK_SIZE / 2) / Entity.BLOCK_SIZE);
        int cordZ = (int) ((v.z + value * SPEED + OFFSET + Entity.BLOCK_SIZE / 2) / Entity.BLOCK_SIZE);
        if (Map.isBlocked(cordX1, cordZ) || Map.isBlocked(cordX2, cordZ)) return;
        this.setPosition(new Vector3f(v.x, v.y, v.z + value * SPEED));
    }

    public void setBomb() {
        int cordX = (int) this.getCord().x;
        int cordZ = (int) this.getCord().y;
        if (Map.getObject(cordX, cordZ) != Map.GRASS) return;
        Map.setObject(cordX, cordZ, Map.BOMB);
    }

    private void rotate(float angle) {
        Quaternion rot = spatial.getLocalRotation();
        rot.slerp(new Quaternion().fromAngleAxis(angle + offsetAngle, Vector3f.UNIT_Y), 0.25f);
        spatial.setLocalRotation(rot);
    }
}
