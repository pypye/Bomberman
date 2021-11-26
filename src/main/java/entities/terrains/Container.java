package entities.terrains;

import com.jme3.math.Vector3f;
import entities.Entity;

public class Container extends Entity {
    private static int count = 0;

    public Container(Vector3f position) {
        super(position, "Models/Container/container.obj");
        this.blocked = true;
        count++;
    }

    @Override
    public void remove() {
        super.remove();
        count--;
    }

    public static int getCount() {
        return count;
    }
}
