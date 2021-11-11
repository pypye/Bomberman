package entities.players;

import com.jme3.math.Vector3f;
import cores.Main;
import cores.Map;
import entities.Entity;

public class Player extends Entity {
    public static final float SPEED = 1.5f;
    public Player(Vector3f position) {
        super(position, "Models/Player/player.obj");
    }

    public void moveForward(float value) {
        Vector3f v = this.getPosition();
        if (v.x + value * SPEED > 39.0f) return;
        int cordX = (int) ((v.x + value * SPEED + Entity.BLOCK_SIZE / 2) / Entity.BLOCK_SIZE);
        int cordZ = (int) ((v.z + Entity.BLOCK_SIZE / 2) / Entity.BLOCK_SIZE);
        if (Map.isBlock(cordX, cordZ)) return;
        this.setPosition(new Vector3f(v.x + value * SPEED, v.y, v.z));
    }

    public void moveBackward(float value) {
        Vector3f v = this.getPosition();
        if (v.x - value * SPEED < -1.0f) return;
        int cordX = (int) ((v.x - value * SPEED + Entity.BLOCK_SIZE / 2) / Entity.BLOCK_SIZE);
        int cordZ = (int) ((v.z + Entity.BLOCK_SIZE / 2) / Entity.BLOCK_SIZE);
        if (Map.isBlock(cordX, cordZ)) return;
        this.setPosition(new Vector3f(v.x - value * SPEED, v.y, v.z));
    }

    public void moveLeft(float value) {
        Vector3f v = this.getPosition();
        if (v.z - value * SPEED < -1.0f) return;
        int cordX = (int) ((v.x + Entity.BLOCK_SIZE / 2) / Entity.BLOCK_SIZE);
        int cordZ = (int) ((v.z - value * SPEED + Entity.BLOCK_SIZE / 2) / Entity.BLOCK_SIZE);
        if (Map.isBlock(cordX, cordZ)) return;
        this.setPosition(new Vector3f(v.x, v.y, v.z - value * SPEED));
    }

    public void moveRight(float value) {
        Vector3f v = this.getPosition();
        if (v.z + value * SPEED > 39.0f) return;
        int cordX = (int) ((v.x + Entity.BLOCK_SIZE / 2) / Entity.BLOCK_SIZE);
        int cordZ = (int) ((v.z + value * SPEED + Entity.BLOCK_SIZE / 2) / Entity.BLOCK_SIZE);
        if (Map.isBlock(cordX, cordZ)) return;
        this.setPosition(new Vector3f(v.x, v.y, v.z + value * SPEED));
    }

    public void setBomb() {
        Vector3f v = this.getPosition();
        int cordX = (int) ((v.x + Entity.BLOCK_SIZE / 2) / Entity.BLOCK_SIZE);
        int cordZ = (int) ((v.z + Entity.BLOCK_SIZE / 2) / Entity.BLOCK_SIZE);
        System.out.println(cordX + " " + cordZ);
        if(Map.getObject(cordX, cordZ) != Map.GRASS) return;
        Map.setObject(cordX, cordZ, Map.BOMB);
    }
}
