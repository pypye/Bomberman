package entities.players;

import com.jme3.anim.AnimComposer;
import com.jme3.input.ChaseCamera;
import com.jme3.math.FastMath;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;
import cores.Main;
import cores.Map;
import entities.Entity;
import entities.bombs.Bomb;
import entities.players.enemies.Enemy;
import entities.terrains.Portal;
import ui.gui.game.AnnouncementGui;
import ui.gui.LocationGui;
import ui.gui.buffs.*;
import ui.gui3d.StatusBarGui3d;
import utils.AnimUtils;

public class MainPlayer extends Player {
    private final AnimComposer composer;
    public final BuffGui speedBuffGUI = new SpeedBuffGui(-1, 90);
    public final BuffGui bombExtendBuffGui = new BombExtendBuffGui(-1, 90);
    public final BuffGui shieldBuffGUI = new ShieldBuffGui(-1, 90);
    public final BuffGui flameBuffGui = new FlameBuffGui(-1, 90);

    public MainPlayer(Vector3f position) {
        super(position, "Models/Player/player.gltf");
        Spatial child = AnimUtils.getAnimRoot(spatial);
        composer = child.getControl(AnimComposer.class);
        composer.setCurrentAction("stand");
        ChaseCamera chaseCam = new ChaseCamera(Main.CAM, spatial, Main.INPUT_MANAGER);
        chaseCam.setDefaultHorizontalRotation(FastMath.PI);
        chaseCam.setDefaultVerticalRotation(FastMath.PI / 3);
        chaseCam.setDefaultDistance(16);
        chaseCam.setMinDistance(10);
        chaseCam.setMaxDistance(20);
        chaseCam.setZoomSensitivity(0.25f);
        chaseCam.cleanupWithInput(Main.INPUT_MANAGER);
        statusBarGui3d.remove();
        statusBarGui3d = new StatusBarGui3d(spatial, bombMax, bombLeft);
    }

    public AnimComposer getComposer() {
        return composer;
    }

    @Override
    public void moveForward(float tpf) {
        super.moveForward(tpf);
        checkPortal();
    }

    @Override
    public void moveBackward(float tpf) {
        super.moveBackward(tpf);
        checkPortal();
    }

    @Override
    public void moveLeft(float tpf) {
        super.moveLeft(tpf);
        checkPortal();
    }

    @Override
    public void moveRight(float tpf) {
        super.moveRight(tpf);
        checkPortal();
    }

    @Override
    protected void onSpeedBuffEffect(float tpf) {
        if (!speedBuffActivated) {
            if (speedBuffDuration > 0) {
                BuffListGui.addBuff(speedBuffGUI);
                speedBuffActivated = true;
                speed *= 2;
            }
        } else {
            speedBuffDuration -= tpf;
            if (speedBuffDuration <= 0) {
                if (BuffListGui.getBuffList().contains(speedBuffGUI)) BuffListGui.removeBuff(speedBuffGUI);
                speedBuffActivated = false;
                speedBuffDuration = 0;
                speed = DEFAULT_SPEED;
                return;
            }
        }
        speedBuffGUI.getText().setText(String.format("%.1fs", speedBuffDuration));
        LocationGui.centerXObject(speedBuffGUI.getText(), speedBuffGUI.getBackground(), speedBuffGUI.getText().getPosY());
    }

    @Override
    protected void onBombBuffEffect(float tpf) {
        if (!bombBuffActivated) {
            if (bombBuffDuration > 0) {
                BuffListGui.addBuff(bombExtendBuffGui);
                bombBuffActivated = true;
                bombLeft += 1;
                bombMax += 1;
            }
        } else {
            bombBuffDuration -= tpf;
            if (bombBuffDuration <= 0) {
                if (BuffListGui.getBuffList().contains(bombExtendBuffGui)) BuffListGui.removeBuff(bombExtendBuffGui);
                bombBuffActivated = false;
                bombBuffDuration = 0;
                bombMax = DEFAULT_BOMB_MAX;
                if (bombLeft > bombMax) bombLeft = bombMax;
                return;
            }
        }
        bombExtendBuffGui.getText().setText(String.format("%.1fs", bombBuffDuration));
        LocationGui.centerXObject(bombExtendBuffGui.getText(), bombExtendBuffGui.getBackground(), bombExtendBuffGui.getText().getPosY());
    }

    @Override
    protected void onShieldBuffEffect(float tpf) {
        if (!shieldBuffActivated) {
            if (shieldBuffDuration > 0) {
                BuffListGui.addBuff(shieldBuffGUI);
                shieldBuffActivated = true;
            }
        } else {
            shieldBuffDuration -= tpf;
            if (shieldBuffDuration <= 0) {
                if (BuffListGui.getBuffList().contains(shieldBuffGUI)) BuffListGui.removeBuff(shieldBuffGUI);
                shieldBuffActivated = false;
                shieldBuffDuration = 0;
                return;
            }
        }
        shieldBuffGUI.getText().setText(String.format("%.1fs", shieldBuffDuration));
        LocationGui.centerXObject(shieldBuffGUI.getText(), shieldBuffGUI.getBackground(), shieldBuffGUI.getText().getPosY());
    }

    @Override
    protected void onFlameBuffEffect(float tpf) {
        if (!flameBuffActivated) {
            if (flameBuffDuration > 0) {
                BuffListGui.addBuff(flameBuffGui);
                Bomb.path = Bomb.BOMB_UPGRADE;
                flameBuffActivated = true;
                bombExplodeLength += 1;
            }
        } else {
            flameBuffDuration -= tpf;
            if (flameBuffDuration <= 0) {
                if (BuffListGui.getBuffList().contains(flameBuffGui)) {
                    BuffListGui.removeBuff(flameBuffGui);
                }
                Bomb.path = Bomb.BOMB_DEFAULT;
                flameBuffActivated = false;
                flameBuffDuration = 0;
                bombExplodeLength = DEFAULT_BOMB_LENGTH;
                return;
            }
        }
        flameBuffGui.getText().setText(String.format("%.1fs", flameBuffDuration));
        LocationGui.centerXObject(flameBuffGui.getText(), flameBuffGui.getBackground(), flameBuffGui.getText().getPosY());
    }

    private void checkPortal() {
        Vector2f a = Entity.getCordFromPosition(this.getPosition().x, this.getPosition().z);
        if (Map.getEntity((int) a.x, (int) a.y) instanceof Portal) {
            if (Enemy.getCount() == 0) {
                System.out.println("[Debug/Event] Player win");
                new AnnouncementGui(true);
            }
        }
    }
}
