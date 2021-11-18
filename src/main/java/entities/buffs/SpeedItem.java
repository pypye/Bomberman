package entities.buffs;

import com.jme3.math.Vector3f;
import entities.players.Player;

public class SpeedItem extends BuffItem {
    public SpeedItem(Vector3f position) {
        super(position, "Models/PowerUp/pup_speed.obj");
    }

    @Override
    public void buff(Player player) {
        super.buff(player);
        player.setSpeedBuffDuration(player.getSpeedBuffDuration() + 10);
    }
}
