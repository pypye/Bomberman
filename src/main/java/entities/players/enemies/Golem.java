package entities.players.enemies;

import algorithms.FindPathAI;
import com.jme3.bounding.BoundingBox;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import cores.Debugger;
import entities.players.Player;
import entities.players.PlayerList;
import scenes.Game;
import scenes.SceneController;

public class Golem extends Enemy {
    public Golem(Vector3f position) {
        super(position, "Models/Monster/golem.obj");
        this.spatial.setLocalScale(0.7f, 0.7f, 0.7f);
        this.spatial.setModelBound(new BoundingBox());
        this.offsetAngle = 0;
    }

    @Override
    public void setNextMove(Vector2f enemy) {
        Player player = PlayerList.getMainPlayer();
        if (player != null) {
            FindPathAI a = new FindPathAI(SceneController.getCurrentScene().getLevel());
            Debugger.log(Debugger.EVENT, "Golem move case is " + a.moveCase((int) this.getCord().x, (int) this.getCord().y, (int) player.getCord().x, (int) player.getCord().y));
            this.nextMove = a.nextMove((int) this.getCord().x, (int) this.getCord().y, (int) player.getCord().x, (int) player.getCord().y);
        }

    }
}
