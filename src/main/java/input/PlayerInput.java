package input;

import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.KeyTrigger;
import cores.Main;
import entities.players.MainPlayer;
import entities.players.PlayerList;

import java.util.HashSet;

public class PlayerInput {
    public static MainPlayer player;
    private static boolean paused = false;

    private static final HashSet<String> keys = new HashSet<>();

    public static void initialize() {
        player = (MainPlayer) PlayerList.getMainPlayer();
        Main.INPUT_MANAGER.addMapping("Forward", new KeyTrigger(KeyInput.KEY_W));
        Main.INPUT_MANAGER.addMapping("Backward", new KeyTrigger(KeyInput.KEY_S));
        Main.INPUT_MANAGER.addMapping("Left", new KeyTrigger(KeyInput.KEY_A));
        Main.INPUT_MANAGER.addMapping("Right", new KeyTrigger(KeyInput.KEY_D));
        Main.INPUT_MANAGER.addMapping("SetBomb", new KeyTrigger(KeyInput.KEY_SPACE));

        Main.INPUT_MANAGER.addListener(actionListener, "Forward", "Backward", "Left", "Right");
        Main.INPUT_MANAGER.addListener(analogListener, "Forward", "Backward", "Left", "Right", "SetBomb");

    }

    private static final ActionListener actionListener = new ActionListener() {
        @Override
        public void onAction(String name, boolean keyPressed, float tpf) {
            if (keyPressed) {
                keys.add(name);
                player.getComposer().setCurrentAction("move");
                player.getComposer().setGlobalSpeed(player.getSpeed());
            } else {
                keys.remove(name);
            }
        }
    };

    private static final AnalogListener analogListener = new AnalogListener() {
        @Override
        public void onAnalog(String name, float value, float tpf) {
            if (!paused) {
                if (name.equals("Forward")) player.moveForward(value);
                if (name.equals("Backward")) player.moveBackward(value);
                if (name.equals("Left")) player.moveLeft(value);
                if (name.equals("Right")) player.moveRight(value);
                if (name.equals("SetBomb")) player.setBomb();
            }
        }
    };


    public static void onUpdate() {
        if (keys.size() == 0) {
            player.getComposer().setCurrentAction("stand");
        }
    }

    public static void remove() {
        Main.INPUT_MANAGER.removeListener(actionListener);
        Main.INPUT_MANAGER.removeListener(analogListener);
    }

    public static boolean isPaused() {
        return paused;
    }

    public static void setPaused(boolean paused) {
        PlayerInput.paused = paused;
    }


}
