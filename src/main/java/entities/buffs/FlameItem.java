package entities.buffs;

import com.jme3.math.Vector3f;
import entities.players.Player;

public class FlameItem extends BuffItem {

    public FlameItem(Vector3f position) {
        super(position, "Models/PowerUp/pup_flame.obj");
    }

    @Override
    public void buff(Player player) {
        super.buff(player);
        player.setFlameBuffDuration(player.getFlameBuffDuration() + 10);
    }
}
