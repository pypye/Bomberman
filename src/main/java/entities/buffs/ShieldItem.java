package entities.buffs;

import com.jme3.math.Vector3f;
import entities.players.Player;

public class ShieldItem extends BuffItem {
    public ShieldItem(Vector3f position) {
        super(position, "Models/PowerUp/pup_shield.obj");
    }

    @Override
    public void buff(Player player) {
        super.buff(player);
        player.setShieldBuffDuration(player.getShieldBuffDuration() + 5);
    }
}
