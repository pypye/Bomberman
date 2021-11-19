package entities.players;

import java.util.ArrayList;

public class PlayerList {
    public static final ArrayList<Player> players = new ArrayList<>();

    public static void add(Player player) {
        players.add(player);
    }

    public static void onUpdate(float tpf) {
        for (Player player : players) {
            player.onUpdate(tpf);
        }
    }
}
