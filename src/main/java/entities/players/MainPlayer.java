package entities.players;

import com.jme3.anim.AnimComposer;
import com.jme3.input.ChaseCamera;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;
import cores.Main;
import entities.bombs.Bomb;
import events.PlayerInput;
import ui.gui.LocationGui;
import ui.gui.buffs.*;
import ui.gui3d.StatusBarGui3d;
import utils.AnimUtils;

public class MainPlayer extends Player {
    private final AnimComposer composer;
    private final Spatial child;
    public MainPlayer(Vector3f position) {
        super(position, "Models/Player/player.gltf");
        child = AnimUtils.getAnimRoot(spatial);
        composer = child.getControl(AnimComposer.class);
        composer.setCurrentAction("stand");
        PlayerInput.initKeys(this);
        ChaseCamera chaseCam = new ChaseCamera(Main.CAM, spatial, Main.INPUT_MANAGER);
        chaseCam.setDefaultHorizontalRotation(FastMath.PI);
        chaseCam.setDefaultVerticalRotation(FastMath.PI / 3);
        chaseCam.setDefaultDistance(16);
        chaseCam.setMinDistance(10);
        chaseCam.setMaxDistance(20);
        chaseCam.setZoomSensitivity(0.25f);
        gui3d.unlink();
        gui3d = new StatusBarGui3d(spatial, bombMax, bombLeft);
    }

    public AnimComposer getComposer() {
        return composer;
    }

    @Override
    protected void onSpeedBuffEffect(float tpf) {
        speedBuffDuration -= tpf;
        if (speedBuffDuration <= 0) {
            if (BuffListGui.getBuffList().contains(speedBuffGUI)) BuffListGui.removeBuff(speedBuffGUI);
            speedBuffActivated = false;
            speedBuffDuration = 0;
            speed = DEFAULT_SPEED;
            return;
        }
        if (!speedBuffActivated) {
            BuffListGui.addBuff(speedBuffGUI);
            speedBuffActivated = true;
            speed *= 2;
        }
        speedBuffGUI.getText().setText(String.format("%.1fs", speedBuffDuration));
        LocationGui.centerXObject(speedBuffGUI.getText(), speedBuffGUI.getBackground(), speedBuffGUI.getText().getPosY());
    }

    @Override
    protected void onBombBuffEffect(float tpf) {
        bombBuffDuration -= tpf;
        if (bombBuffDuration <= 0) {
            if (BuffListGui.getBuffList().contains(bombExtendBuffGui)) BuffListGui.removeBuff(bombExtendBuffGui);
            bombBuffActivated = false;
            bombBuffDuration = 0;
            bombMax = DEFAULT_BOMB_MAX;
            if (bombLeft > bombMax) bombLeft = bombMax;
            return;
        }
        if (!bombBuffActivated) {
            BuffListGui.addBuff(bombExtendBuffGui);
            bombBuffActivated = true;
            bombLeft += 1;
            bombMax += 1;
        }
        bombExtendBuffGui.getText().setText(String.format("%.1fs", bombBuffDuration));
        LocationGui.centerXObject(bombExtendBuffGui.getText(), bombExtendBuffGui.getBackground(), bombExtendBuffGui.getText().getPosY());
    }

    @Override
    protected void onShieldBuffEffect(float tpf) {
        shieldBuffDuration -= tpf;
        if (shieldBuffDuration <= 0) {
            if (BuffListGui.getBuffList().contains(shieldBuffGUI)) BuffListGui.removeBuff(shieldBuffGUI);
            shieldBuffActivated = false;
            shieldBuffDuration = 0;
            return;
        }
        if (!shieldBuffActivated) {
            BuffListGui.addBuff(shieldBuffGUI);
            shieldBuffActivated = true;
        }
        shieldBuffGUI.getText().setText(String.format("%.1fs", shieldBuffDuration));
        LocationGui.centerXObject(shieldBuffGUI.getText(), shieldBuffGUI.getBackground(), shieldBuffGUI.getText().getPosY());
    }

    @Override
    protected void onFlameBuffEffect(float tpf) {
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
        if (!flameBuffActivated) {
            BuffListGui.addBuff(flameBuffGui);
            Bomb.path = Bomb.BOMB_UPGRADE;
            flameBuffActivated = true;
            bombExplodeLength += 1;
        }
        flameBuffGui.getText().setText(String.format("%.1fs", flameBuffDuration));
        LocationGui.centerXObject(flameBuffGui.getText(), flameBuffGui.getBackground(), flameBuffGui.getText().getPosY());
    }
}
