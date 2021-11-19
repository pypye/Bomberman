package entities.buffs;

import com.jme3.math.Vector3f;
import entities.players.Player;

public class BombExtendItem extends BuffItem {
    public BombExtendItem(Vector3f position) {
        super(position, "Models/Buffs/pup_bomb.obj");
    }

    @Override
    public void buff(Player player) {
        super.buff(player);
        player.setBombBuffDuration(player.getBombBuffDuration() + 10);
    }
}
