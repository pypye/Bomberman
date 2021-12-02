package entities.players.enemies;

import com.jme3.bounding.BoundingBox;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;

public class Golem extends Enemy {

    public Golem(Vector3f position) {
        super(position, "Models/Monster/golem.obj");
        this.spatial.setLocalScale(0.7f, 0.7f, 0.7f);
        this.spatial.setModelBound(new BoundingBox());
    }

    @Override
    public void setNextMove(Vector2f enemy) {

    }
}
