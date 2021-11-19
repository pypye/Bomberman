package entities.players.enemies;

import com.jme3.math.Vector3f;
import entities.players.Player;

public abstract class Enemy extends Player {
    public Enemy(Vector3f position, String path) {
        super(position, path);
    }
    public abstract int nextMove();
}
