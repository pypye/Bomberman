package entities.players;

import com.jme3.math.Vector3f;

import java.util.ArrayList;

public class PlayerList {
    public static final ArrayList<Player> players = new ArrayList<>();

    public static void init() {
        add(new MainPlayer(new Vector3f(0f, 1f, 0f)));
    }

    public static void add(Player player) {
        players.add(player);
    }

    public static void onUpdate(float tpf) {
        for (Player player : players) {
            player.onUpdate(tpf);
        }
    }
}
