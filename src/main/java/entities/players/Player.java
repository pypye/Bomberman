package entities.players;

import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;
import entities.buffs.BuffItem;
import ui.gui.LocationGui;
import ui.gui.buffs.*;
import ui.gui3d.StatusBarGui3d;
import utils.LightUtils;
import cores.Map;
import entities.Entity;

public class Player extends Entity {
    public static final float OFFSET = 0.5f;
    public static final float DEFAULT_SPEED = 3f;
    public static final int DEFAULT_BOMB_LENGTH = 1;
    public static final int DEFAULT_BOMB_MAX = 3;

    protected float speed = DEFAULT_SPEED;
    protected int bombMax = DEFAULT_BOMB_MAX;
    protected int bombLeft = DEFAULT_BOMB_MAX;
    protected int bombExplodeLength = DEFAULT_BOMB_LENGTH;

    protected float bombCoolDownCurrent = 0f;
    protected float speedBuffDuration = 0f;
    protected float bombBuffDuration = 0f;
    protected float shieldBuffDuration = 0f;
    protected float flameBuffDuration = 0f;

    protected boolean speedBuffActivated = false;
    protected boolean bombBuffActivated = false;
    protected boolean shieldBuffActivated = false;
    protected boolean flameBuffActivated = false;

    protected float offsetAngle = FastMath.HALF_PI;

    protected final BuffGui speedBuffGUI = new SpeedBuffGui(-1, 90);
    protected final BuffGui bombExtendBuffGui = new BombExtendBuffGui(-1, 90);
    protected final BuffGui shieldBuffGUI = new ShieldBuffGui(-1, 90);
    protected final BuffGui flameBuffGui = new FlameBuffGui(-1, 90);
    protected StatusBarGui3d gui3d;

    public Player(Vector3f position, String path) {
        super(position, path);
        PlayerList.add(this);
        LightUtils.setSpatialLight(spatial);
        gui3d = new StatusBarGui3d(spatial, bombMax, bombLeft);
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
        if (bombLeft > 0) {
            Map.setObject(cordX, cordZ, Map.BOMB, this);
            bombLeft--;
        }
    }

    private void rotate(float angle) {
        Quaternion rot = spatial.getLocalRotation();
        rot.slerp(new Quaternion().fromAngleAxis(angle + offsetAngle, Vector3f.UNIT_Y), 0.25f);
        spatial.setLocalRotation(rot);
    }

    public void onUpdate(float tpf) {
        onCoolDownBomb(tpf);
        onSpeedBuffEffect(tpf);
        onBombBuffEffect(tpf);
        onShieldBuffEffect(tpf);
        onFlameBuffEffect(tpf);
        gui3d.setMaxCount(bombMax);
        gui3d.setCount(bombLeft);
        gui3d.setCoolDown(bombCoolDownCurrent);
        gui3d.onUpdate();
    }

    protected void onSpeedBuffEffect(float tpf) {
        speedBuffDuration -= tpf;
        if (speedBuffDuration <= 0) {
            speedBuffActivated = false;
            speedBuffDuration = 0;
            speed = DEFAULT_SPEED;
            return;
        }
        if (!speedBuffActivated) {
            speedBuffActivated = true;
            speed *= 2;
        }
        LocationGui.centerXObject(speedBuffGUI.getText(), speedBuffGUI.getBackground(), speedBuffGUI.getText().getPosY());
    }

    protected void onBombBuffEffect(float tpf) {
        bombBuffDuration -= tpf;
        if (bombBuffDuration <= 0) {
            bombBuffActivated = false;
            bombBuffDuration = 0;
            bombMax = DEFAULT_BOMB_MAX;
            if (bombLeft > bombMax) bombLeft = bombMax;
            return;
        }
        if (!bombBuffActivated) {
            bombBuffActivated = true;
            bombLeft += 1;
            bombMax += 1;
        }
        LocationGui.centerXObject(bombExtendBuffGui.getText(), bombExtendBuffGui.getBackground(), bombExtendBuffGui.getText().getPosY());
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
        LocationGui.centerXObject(shieldBuffGUI.getText(), shieldBuffGUI.getBackground(), shieldBuffGUI.getText().getPosY());
    }

    protected void onFlameBuffEffect(float tpf) {
        flameBuffDuration -= tpf;
        if (flameBuffDuration <= 0) {
            flameBuffActivated = false;
            flameBuffDuration = 0;
            bombExplodeLength = DEFAULT_BOMB_LENGTH;
            return;
        }
        if (!flameBuffActivated) {
            flameBuffActivated = true;
            bombExplodeLength += 1;
        }
        LocationGui.centerXObject(flameBuffGui.getText(), flameBuffGui.getBackground(), flameBuffGui.getText().getPosY());
    }

    protected void onCoolDownBomb(float tpf) {
        if (bombMax == bombLeft) {
            bombCoolDownCurrent = 0;
            return;
        }
        bombCoolDownCurrent += tpf;
        if (bombCoolDownCurrent >= 4) {
            bombCoolDownCurrent = 0;
            bombLeft++;
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

    public float getBombCoolDownCurrent() {
        return bombCoolDownCurrent;
    }

    public void setBombCoolDownCurrent(float bombCoolDownCurrent) {
        this.bombCoolDownCurrent = bombCoolDownCurrent;
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
