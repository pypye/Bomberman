package entities.players;

import com.jme3.math.Vector3f;
import entities.EntityList;

public class PlayerList extends EntityList {
    public static void init() {
        add(new MainPlayer(new Vector3f(0f, 1f, 0f)));
    }

}
