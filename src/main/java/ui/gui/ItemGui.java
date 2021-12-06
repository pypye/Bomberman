package ui.gui;

import com.jme3.math.Vector2f;

public abstract class ItemGui {
    protected Vector2f position = new Vector2f(0, 0);
    protected Vector2f size = new Vector2f(0, 0);

    public abstract void show();

    public abstract void remove();

    public Vector2f getSize() {
        return size;
    }

    public void setSizeX(float sizeX) {
        this.size.setX(sizeX);
    }

    public void setSizeY(float sizeY) {
        this.size.setY(sizeY);
    }

    public void setSize(float sizeX, float sizeY) {
        this.size.setX(sizeX);
        this.size.setY(sizeY);
    }

    public Vector2f getPosition() {
        return position;
    }

    public void setPosition(float posX, float posY) {
        this.position.set(posX, posY);
    }

    public void setPositionX(float posX) {
        this.position.setX(posX);
    }

    public void setPositionY(float posY) {
        this.position.setY(posY);
    }
}
