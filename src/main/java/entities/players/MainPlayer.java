package entities.players;

import com.jme3.anim.AnimComposer;
import com.jme3.input.ChaseCamera;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import cores.Main;
import events.PlayerInput;
import hud.gui.*;
import hud.Text;
import utils.AnimUtils;

public class MainPlayer extends Player {
    private final AnimComposer composer;

    private final BuffGUI speedBuffGUI = new SpeedBuffGUI(-1, 90);
    private final BuffGUI bombExtendBuffGUI = new BombExtendBuffGUI(-1, 90);
    private final BuffGUI shieldBuffGUI = new ShieldBuffGUI(-1, 90);
    private final BuffGUI flameBuffGUI = new FlameBuffGUI(-1, 90);

    private final Text speedBuffText = new Text("0.0s", 16, ColorRGBA.White, -1, 90);
    private final Text bombExtendBuffText = new Text("0.0s", 16, ColorRGBA.White, -1, 90);
    private final Text shieldBuffText = new Text("0.0s", 16, ColorRGBA.White, -1, 90);
    private final Text flameBuffText = new Text("0.0s", 16, ColorRGBA.White, -1, 90);

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
            if (BuffListGUI.getBuffList().contains(speedBuffGUI)) BuffListGUI.removeBuff(speedBuffGUI, speedBuffText);

            speedBuffActivated = false;
            speedBuffDuration = 0;
            speed = 2f;
            return;
        }
        if (!speedBuffActivated) {
            BuffListGUI.addBuff(speedBuffGUI,speedBuffText);
            speedBuffActivated = true;
            speed *= 2;
        }
        speedBuffText.setText(String.format("%.1fs", speedBuffDuration));
    }

    @Override
    protected void onBombBuffEffect(float tpf) {
        bombBuffDuration -= tpf;
        if (bombBuffDuration <= 0) {
            if (BuffListGUI.getBuffList().contains(bombExtendBuffGUI)) BuffListGUI.removeBuff(bombExtendBuffGUI, bombExtendBuffText);
            bombBuffActivated = false;
            bombBuffDuration = 0;
            bombMax = 1;
            if (bombLeft > bombMax) bombLeft = bombMax;
            return;
        }
        if (!bombBuffActivated) {
            BuffListGUI.addBuff(bombExtendBuffGUI, bombExtendBuffText);
            bombBuffActivated = true;
            bombLeft += 1;
            bombMax += 1;
        }
        bombExtendBuffText.setText(String.format("%.1fs", bombBuffDuration));
    }

    @Override
    protected void onShieldBuffEffect(float tpf) {
        shieldBuffDuration -= tpf;
        if (shieldBuffDuration <= 0) {
            if (BuffListGUI.getBuffList().contains(shieldBuffGUI)) BuffListGUI.removeBuff(shieldBuffGUI, shieldBuffText);
            shieldBuffActivated = false;
            shieldBuffDuration = 0;
            return;
        }
        if (!shieldBuffActivated) {
            BuffListGUI.addBuff(shieldBuffGUI, shieldBuffText);
            shieldBuffActivated = true;
        }
        shieldBuffText.setText(String.format("%.1fs", shieldBuffDuration));
    }

    @Override
    protected void onFlameBuffEffect(float tpf) {
        flameBuffDuration -= tpf;
        if (flameBuffDuration <= 0) {
            if (BuffListGUI.getBuffList().contains(flameBuffGUI)) {
                BuffListGUI.removeBuff(flameBuffGUI, flameBuffText);
            }
            flameBuffActivated = false;
            flameBuffDuration = 0;
            bombExplodeLength = 1;
            return;
        }
        if (!flameBuffActivated) {
            BuffListGUI.addBuff(flameBuffGUI, flameBuffText);
            flameBuffActivated = true;
            bombExplodeLength += 1;
        }
        flameBuffText.setText(String.format("%.1fs", flameBuffDuration));
    }
}
