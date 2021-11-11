package entities.players;

import com.jme3.math.Vector3f;
import events.PlayerInput;

public class MainPlayer extends Player {
    public MainPlayer(Vector3f position) {
        super(position);
        PlayerInput.initKeys(this);
    }
}
