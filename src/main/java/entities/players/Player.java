package entities.players;

import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import entities.buffs.BuffItem;
import utils.LightUtils;
import cores.Map;
import entities.Entity;

public class Player extends Entity {
    public static final float OFFSET = 0.5f;
    protected float speed = 2f;
    protected int bombMax = 1;
    protected int bombLeft = 1;
    protected int bombExplodeLength = 1;

    protected float bombCoolDownTime = 5f;
    protected float speedBuffDuration = 0f;
    protected float bombBuffDuration = 0f;
    protected float shieldBuffDuration = 0f;
    protected float flameBuffDuration = 0f;

    protected boolean speedBuffActivated = false;
    protected boolean bombBuffActivated = false;
    protected boolean shieldBuffActivated = false;
    protected boolean flameBuffActivated = false;

    protected float offsetAngle = FastMath.HALF_PI;

    public Player(Vector3f position, String path) {
        super(position, path);
        LightUtils.setSpatialLight(spatial);
    }

    public void moveForward(float value) {
        rotate(0);
        Vector3f v = this.getPosition();
        if (v.x + value * speed + OFFSET > 39.0f) return;
        int cordX = (int) ((v.x + value * speed + OFFSET + Entity.BLOCK_SIZE / 2) / Entity.BLOCK_SIZE);
        int cordZ = (int) ((v.z + Entity.BLOCK_SIZE / 2) / Entity.BLOCK_SIZE);
        int cordZ1 = (int) ((v.z + OFFSET + Entity.BLOCK_SIZE / 2) / Entity.BLOCK_SIZE);
        int cordZ2 = (int) ((v.z - OFFSET + Entity.BLOCK_SIZE / 2) / Entity.BLOCK_SIZE);
        if (Map.isBlocked(cordX, cordZ1) || Map.isBlocked(cordX, cordZ2)) return;
        if (Map.getEntity(cordX, cordZ) instanceof BuffItem) ((BuffItem) Map.getEntity(cordX, cordZ)).buff(this);
        this.setPosition(new Vector3f(v.x + value * speed, v.y, v.z));
    }

    public void moveBackward(float value) {
        rotate(FastMath.PI);
        Vector3f v = this.getPosition();
        if (v.x - value * speed - OFFSET < -1.0f) return;
        int cordX = (int) ((v.x - value * speed - OFFSET + Entity.BLOCK_SIZE / 2) / Entity.BLOCK_SIZE);
        int cordZ = (int) ((v.z + Entity.BLOCK_SIZE / 2) / Entity.BLOCK_SIZE);
        int cordZ1 = (int) ((v.z - OFFSET + Entity.BLOCK_SIZE / 2) / Entity.BLOCK_SIZE);
        int cordZ2 = (int) ((v.z + OFFSET + Entity.BLOCK_SIZE / 2) / Entity.BLOCK_SIZE);
        if (Map.isBlocked(cordX, cordZ1) || Map.isBlocked(cordX, cordZ2)) return;
        if (Map.getEntity(cordX, cordZ) instanceof BuffItem) ((BuffItem) Map.getEntity(cordX, cordZ)).buff(this);
        this.setPosition(new Vector3f(v.x - value * speed, v.y, v.z));
    }

    public void moveLeft(float value) {
        rotate(FastMath.HALF_PI);
        Vector3f v = this.getPosition();
        if (v.z - value * speed - OFFSET < -1.0f) return;
        int cordX1 = (int) ((v.x - OFFSET + Entity.BLOCK_SIZE / 2) / Entity.BLOCK_SIZE);
        int cordX2 = (int) ((v.x + OFFSET + Entity.BLOCK_SIZE / 2) / Entity.BLOCK_SIZE);
        int cordX = (int) ((v.x + Entity.BLOCK_SIZE / 2) / Entity.BLOCK_SIZE);
        int cordZ = (int) ((v.z - value * speed - OFFSET + Entity.BLOCK_SIZE / 2) / Entity.BLOCK_SIZE);
        if (Map.isBlocked(cordX1, cordZ) || Map.isBlocked(cordX2, cordZ)) return;
        if (Map.getEntity(cordX, cordZ) instanceof BuffItem) ((BuffItem) Map.getEntity(cordX, cordZ)).buff(this);

        this.setPosition(new Vector3f(v.x, v.y, v.z - value * speed));
    }

    public void moveRight(float value) {
        rotate(-FastMath.HALF_PI);
        Vector3f v = this.getPosition();
        if (v.z + value * speed + OFFSET > 39.0f) return;
        int cordX1 = (int) ((v.x + OFFSET + Entity.BLOCK_SIZE / 2) / Entity.BLOCK_SIZE);
        int cordX2 = (int) ((v.x - OFFSET + Entity.BLOCK_SIZE / 2) / Entity.BLOCK_SIZE);
        int cordX = (int) ((v.x + Entity.BLOCK_SIZE / 2) / Entity.BLOCK_SIZE);
        int cordZ = (int) ((v.z + value * speed + OFFSET + Entity.BLOCK_SIZE / 2) / Entity.BLOCK_SIZE);
        if (Map.isBlocked(cordX1, cordZ) || Map.isBlocked(cordX2, cordZ)) return;
        if (Map.getEntity(cordX, cordZ) instanceof BuffItem) ((BuffItem) Map.getEntity(cordX, cordZ)).buff(this);
        this.setPosition(new Vector3f(v.x, v.y, v.z + value * speed));
    }

    public void setBomb() {
        int cordX = (int) this.getCord().x;
        int cordZ = (int) this.getCord().y;
        if (Map.getObject(cordX, cordZ) != Map.GRASS) return;
        Map.setObject(cordX, cordZ, Map.BOMB, this);
    }

    private void rotate(float angle) {
        Quaternion rot = spatial.getLocalRotation();
        rot.slerp(new Quaternion().fromAngleAxis(angle + offsetAngle, Vector3f.UNIT_Y), 0.25f);
        spatial.setLocalRotation(rot);
    }

    public void onUpdate(float tpf) {
        onSpeedBuffEffect(tpf);
        onBombBuffEffect(tpf);
        onShieldBuffEffect(tpf);
        onFlameBuffEffect(tpf);
    }

    protected void onSpeedBuffEffect(float tpf) {
        speedBuffDuration -= tpf;
        if (speedBuffDuration <= 0) {
            speedBuffActivated = false;
            speedBuffDuration = 0;
            speed = 2f;
            return;
        }
        if (!speedBuffActivated) {
            speedBuffActivated = true;
            speed *= 2;
        }
    }

    protected void onBombBuffEffect(float tpf) {
        bombBuffDuration -= tpf;
        if (bombBuffDuration <= 0) {
            bombBuffActivated = false;
            bombBuffDuration = 0;
            bombMax = 1;
            if (bombLeft > bombMax) bombLeft = bombMax;
            return;
        }
        if (!bombBuffActivated) {
            bombBuffActivated = true;
            bombLeft += 1;
            bombMax += 1;
        }
    }

    protected void onShieldBuffEffect(float tpf) {
        shieldBuffDuration -= tpf;
        if (shieldBuffDuration <= 0) {
            shieldBuffActivated = false;
            shieldBuffDuration = 0;
            return;
        }
        if (!shieldBuffActivated) {
            shieldBuffActivated = true;
        }
    }

    protected void onFlameBuffEffect(float tpf) {
        flameBuffDuration -= tpf;
        if (flameBuffDuration <= 0) {
            flameBuffActivated = false;
            flameBuffDuration = 0;
            bombExplodeLength = 1;
            return;
        }
        if (!flameBuffActivated) {
            flameBuffActivated = true;
            bombExplodeLength += 1;
        }
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public float getSpeed() {
        return speed;
    }

    public int getBombMax() {
        return bombMax;
    }

    public void setBombMax(int bombMax) {
        this.bombMax = bombMax;
    }

    public int getBombLeft() {
        return bombLeft;
    }

    public void setBombLeft(int bombLeft) {
        this.bombLeft = bombLeft;
    }

    public int getBombExplodeLength() {
        return bombExplodeLength;
    }

    public void setBombExplodeLength(int bombExplodeLength) {
        this.bombExplodeLength = bombExplodeLength;
    }

    public float getBombCoolDownTime() {
        return bombCoolDownTime;
    }

    public void setBombCoolDownTime(float bombCoolDownTime) {
        this.bombCoolDownTime = bombCoolDownTime;
    }

    public float getSpeedBuffDuration() {
        return speedBuffDuration;
    }

    public void setSpeedBuffDuration(float speedBuffDuration) {
        this.speedBuffDuration = speedBuffDuration;
    }

    public float getBombBuffDuration() {
        return bombBuffDuration;
    }

    public void setBombBuffDuration(float bombBuffDuration) {
        this.bombBuffDuration = bombBuffDuration;
    }

    public float getShieldBuffDuration() {
        return shieldBuffDuration;
    }

    public void setShieldBuffDuration(float shieldBuffDuration) {
        this.shieldBuffDuration = shieldBuffDuration;
    }

    public float getFlameBuffDuration() {
        return flameBuffDuration;
    }

    public void setFlameBuffDuration(float flameBuffDuration) {
        this.flameBuffDuration = flameBuffDuration;
    }
}
