package entities.players;

import com.jme3.math.Vector3f;
import cores.Map;
import entities.Entity;

public class Player extends Entity {
    public static final float SPEED = 1.5f;
    public static final float OFFSET = 0.5f;

    public Player(Vector3f position) {
        super(position, "Models/Player/player.obj");
    }

    public void moveForward(float value) {
        Vector3f v = this.getPosition();
        if (v.x + value * SPEED + OFFSET > 39.0f) return;
        int cordX = (int) ((v.x + value * SPEED + OFFSET + Entity.BLOCK_SIZE / 2) / Entity.BLOCK_SIZE);
        int cordZ1 = (int) ((v.z + OFFSET + Entity.BLOCK_SIZE / 2) / Entity.BLOCK_SIZE);
        int cordZ2 = (int) ((v.z - OFFSET + Entity.BLOCK_SIZE / 2) / Entity.BLOCK_SIZE);
        if (Map.isBlock(cordX, cordZ1) || Map.isBlock(cordX, cordZ2)) return;
        this.setPosition(new Vector3f(v.x + value * SPEED, v.y, v.z));
    }

    public void moveBackward(float value) {
        Vector3f v = this.getPosition();
        if (v.x - value * SPEED - OFFSET < -1.0f) return;
        int cordX = (int) ((v.x - value * SPEED - OFFSET + Entity.BLOCK_SIZE / 2) / Entity.BLOCK_SIZE);
        int cordZ1 = (int) ((v.z - OFFSET + Entity.BLOCK_SIZE / 2) / Entity.BLOCK_SIZE);
        int cordZ2 = (int) ((v.z + OFFSET + Entity.BLOCK_SIZE / 2) / Entity.BLOCK_SIZE);
        if (Map.isBlock(cordX, cordZ1) || Map.isBlock(cordX, cordZ2)) return;
        this.setPosition(new Vector3f(v.x - value * SPEED, v.y, v.z));
    }

    public void moveLeft(float value) {
        Vector3f v = this.getPosition();
        if (v.z - value * SPEED - OFFSET < -1.0f) return;
        int cordX1 = (int) ((v.x - OFFSET + Entity.BLOCK_SIZE / 2) / Entity.BLOCK_SIZE);
        int cordX2 = (int) ((v.x + OFFSET + Entity.BLOCK_SIZE / 2) / Entity.BLOCK_SIZE);
        int cordZ = (int) ((v.z - value * SPEED - OFFSET + Entity.BLOCK_SIZE / 2) / Entity.BLOCK_SIZE);
        if (Map.isBlock(cordX1, cordZ) || Map.isBlock(cordX2, cordZ)) return;
        this.setPosition(new Vector3f(v.x, v.y, v.z - value * SPEED));
    }

    public void moveRight(float value) {
        Vector3f v = this.getPosition();
        if (v.z + value * SPEED + OFFSET > 39.0f) return;
        int cordX1 = (int) ((v.x + OFFSET + Entity.BLOCK_SIZE / 2) / Entity.BLOCK_SIZE);
        int cordX2 = (int) ((v.x - OFFSET + Entity.BLOCK_SIZE / 2) / Entity.BLOCK_SIZE);
        int cordZ = (int) ((v.z + value * SPEED + OFFSET + Entity.BLOCK_SIZE / 2) / Entity.BLOCK_SIZE);
        if (Map.isBlock(cordX1, cordZ) || Map.isBlock(cordX2, cordZ)) return;
        this.setPosition(new Vector3f(v.x, v.y, v.z + value * SPEED));
    }

    public void setBomb() {
        int cordX = (int) this.getCord().x;
        int cordZ = (int) this.getCord().y;
        if (Map.getObject(cordX, cordZ) != Map.GRASS) return;
        Map.setObject(cordX, cordZ, Map.BOMB);
    }
}
