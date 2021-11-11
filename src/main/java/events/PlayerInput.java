package events;

import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.math.Vector3f;
import cores.Main;
import cores.Map;
import entities.Entity;
import entities.players.Player;

public class PlayerInput {
    private static Player player;

    public static void initKeys(Player _player) {
        player = _player;
        Main.INPUT_MANAGER.addMapping("Forward", new KeyTrigger(KeyInput.KEY_U));
        Main.INPUT_MANAGER.addMapping("Backward", new KeyTrigger(KeyInput.KEY_J));
        Main.INPUT_MANAGER.addMapping("Left", new KeyTrigger(KeyInput.KEY_H));
        Main.INPUT_MANAGER.addMapping("Right", new KeyTrigger(KeyInput.KEY_K));
        Main.INPUT_MANAGER.addMapping("SetBomb", new KeyTrigger(KeyInput.KEY_SPACE));
        Main.INPUT_MANAGER.addListener(analogListener, "Forward", "Backward", "Left", "Right", "SetBomb");
    }

    private static final AnalogListener analogListener = new AnalogListener() {
        @Override
        public void onAnalog(String name, float value, float tpf) {
            if (name.equals("Forward")) player.moveForward(value);
            if (name.equals("Backward")) player.moveBackward(value);
            if (name.equals("Left")) player.moveLeft(value);
            if (name.equals("Right")) player.moveRight(value);
            if (name.equals("SetBomb")) player.setBomb();
            //System.out.println(player.getPosition());
        }
    };
}
