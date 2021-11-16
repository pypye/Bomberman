package entities.players;

import com.jme3.anim.AnimComposer;
import com.jme3.input.ChaseCamera;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import cores.Main;
import events.PlayerInput;
import gui.*;
import utils.AnimUtils;

public class MainPlayer extends Player {
    private final AnimComposer composer;
    private final BuffGUI speedBuffGUI = new SpeedBuffGUI(-1, 90);
    private final BuffGUI bombExtendBuffGUI = new BombExtendBuffGUI(-1, 90);
    private final BuffGUI shieldBuffGUI = new ShieldBuffGUI(-1, 90);
    private final BuffGUI flameBuffGUI = new FlameBuffGUI(-1, 90);

    public MainPlayer(Vector3f position) {
        super(position, "Models/Player/player.gltf");
        spatial = AnimUtils.getAnimRoot(spatial);
        composer = spatial.getControl(AnimComposer.class);
        composer.setCurrentAction("stand");
        PlayerInput.initKeys(this);
        ChaseCamera chaseCam = new ChaseCamera(Main.CAM, spatial, Main.INPUT_MANAGER);
        chaseCam.setDefaultHorizontalRotation(FastMath.PI);
        chaseCam.setDefaultVerticalRotation(FastMath.PI / 3);
        chaseCam.setDefaultDistance(16);
        chaseCam.setMinDistance(10);
        chaseCam.setMaxDistance(20);
        chaseCam.setZoomSensitivity(0.25f);
    }

    public AnimComposer getComposer() {
        return composer;
    }

    @Override
    protected void onSpeedBuffEffect(float tpf) {
        speedBuffDuration -= tpf;
        if (speedBuffDuration <= 0) {
            if (BuffListGUI.getBuffList().contains(speedBuffGUI)) BuffListGUI.removeBuff(speedBuffGUI);
            speedBuffActivated = false;
            speedBuffDuration = 0;
            speed = 2f;
            return;
        }
        if (!speedBuffActivated) {
            BuffListGUI.addBuff(speedBuffGUI);
            speedBuffActivated = true;
            speed *= 2;
        }
    }

    @Override
    protected void onBombBuffEffect(float tpf) {
        bombBuffDuration -= tpf;
        if (bombBuffDuration <= 0) {
            if (BuffListGUI.getBuffList().contains(bombExtendBuffGUI)) BuffListGUI.removeBuff(bombExtendBuffGUI);
            bombBuffActivated = false;
            bombBuffDuration = 0;
            bombMax = 1;
            if (bombLeft > bombMax) bombLeft = bombMax;
            return;
        }
        if (!bombBuffActivated) {
            BuffListGUI.addBuff(bombExtendBuffGUI);
            bombBuffActivated = true;
            bombLeft += 1;
            bombMax += 1;
        }
    }

    @Override
    protected void onShieldBuffEffect(float tpf) {
        shieldBuffDuration -= tpf;
        if (shieldBuffDuration <= 0) {
            if (BuffListGUI.getBuffList().contains(shieldBuffGUI)) BuffListGUI.removeBuff(shieldBuffGUI);
            shieldBuffActivated = false;
            shieldBuffDuration = 0;
            return;
        }
        if (!shieldBuffActivated) {
            BuffListGUI.addBuff(shieldBuffGUI);
            shieldBuffActivated = true;
        }
    }

    @Override
    protected void onFlameBuffEffect(float tpf) {
        flameBuffDuration -= tpf;
        if (flameBuffDuration <= 0) {
            if (BuffListGUI.getBuffList().contains(flameBuffGUI)) BuffListGUI.removeBuff(flameBuffGUI);
            flameBuffActivated = false;
            flameBuffDuration = 0;
            bombExplodeLength = 1;
            return;
        }
        if (!flameBuffActivated) {
            BuffListGUI.addBuff(flameBuffGUI);
            flameBuffActivated = true;
            bombExplodeLength += 1;
        }
    }
}
