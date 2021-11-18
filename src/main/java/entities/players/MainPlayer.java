package entities.players;

import com.jme3.anim.AnimComposer;
import com.jme3.input.ChaseCamera;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import cores.Main;
import events.PlayerInput;
import ui.gui.buffs.*;
import ui.gui3d.ItemGui3d;
import utils.AnimUtils;

public class MainPlayer extends Player {
    private final AnimComposer composer;

    private final BuffGui speedBuffGUI = new SpeedBuffGui(-1, 90);
    private final BuffGui bombExtendBuffGui = new BombExtendBuffGui(-1, 90);
    private final BuffGui shieldBuffGUI = new ShieldBuffGui(-1, 90);
    private final BuffGui flameBuffGui = new FlameBuffGui(-1, 90);

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
            if (BuffListGui.getBuffList().contains(speedBuffGUI)) BuffListGui.removeBuff(speedBuffGUI);
            speedBuffActivated = false;
            speedBuffDuration = 0;
            speed = 2f;
            return;
        }
        if (!speedBuffActivated) {
            BuffListGui.addBuff(speedBuffGUI);
            speedBuffActivated = true;
            speed *= 2;
        }
        speedBuffGUI.getText().setText(String.format("%.1fs", speedBuffDuration));
    }

    @Override
    protected void onBombBuffEffect(float tpf) {
        bombBuffDuration -= tpf;
        if (bombBuffDuration <= 0) {
            if (BuffListGui.getBuffList().contains(bombExtendBuffGui)) BuffListGui.removeBuff(bombExtendBuffGui);
            bombBuffActivated = false;
            bombBuffDuration = 0;
            bombMax = 1;
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
    }

    @Override
    protected void onFlameBuffEffect(float tpf) {
        flameBuffDuration -= tpf;
        if (flameBuffDuration <= 0) {
            if (BuffListGui.getBuffList().contains(flameBuffGui)) {
                BuffListGui.removeBuff(flameBuffGui);
            }
            flameBuffActivated = false;
            flameBuffDuration = 0;
            bombExplodeLength = 1;
            return;
        }
        if (!flameBuffActivated) {
            BuffListGui.addBuff(flameBuffGui);
            flameBuffActivated = true;
            bombExplodeLength += 1;
        }
        flameBuffGui.getText().setText(String.format("%.1fs", flameBuffDuration));
    }
}
