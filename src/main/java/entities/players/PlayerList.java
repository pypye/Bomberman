package entities.players;

import events.PlayerInput;

import java.util.ArrayList;

public class PlayerList {
    public static final ArrayList<Player> players = new ArrayList<>();

    public static void add(Player player) {
        players.add(player);
    }

    public static void remove(Player player) {
        if (!player.shieldBuffActivated) {
            player.dead();
            if (player instanceof MainPlayer) {
                PlayerInput.destroyKeys();
                System.out.println("Player died, back to main menu adding soon!");
                System.exit(0);
            }
            players.remove(player);
        } else {
            player.shieldBuffActivated = false;
            player.shieldBuffDuration = 0;
        }
    }

    public static void onUpdate(float tpf) {
        for (Player player : players) {
            player.onUpdate(tpf);
        }
    }
}
