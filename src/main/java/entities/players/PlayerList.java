package entities.players;

import com.jme3.input.ChaseCamera;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import cores.Main;

import java.util.ArrayList;

public class PlayerList {
    public static final ArrayList<Player> players = new ArrayList<>();

    public static void init() {
        add(new MainPlayer(new Vector3f(0f, 1f, 0f)));
        ChaseCamera chaseCam = new ChaseCamera(Main.CAM, PlayerList.players.get(0).getSpatial(), Main.INPUT_MANAGER);
        chaseCam.setDefaultHorizontalRotation(FastMath.PI);
        chaseCam.setDefaultVerticalRotation(FastMath.PI/3);
        chaseCam.setDefaultDistance(12);
        chaseCam.setMinDistance(10);
        chaseCam.setMaxDistance(20);
        chaseCam.setZoomSensitivity(0.25f);
    }

    public static void add(Player player) {
        players.add(player);
    }
}
