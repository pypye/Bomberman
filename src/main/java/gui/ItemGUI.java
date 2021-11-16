package gui;

import com.jme3.ui.Picture;
import cores.Main;

public class ItemGUI {
    private final Picture item;
    private float x;
    private float y;

    public ItemGUI(float sizeX, float sizeY, String path) {
        item = new Picture("HUD");
        item.setImage(Main.ASSET_MANAGER, path, true);
        this.setX(sizeX);
        this.setY(sizeY);

    }

    public void show() {
        Main.GUI_NODE.attachChild(item);
    }

    public void hide() {
        Main.GUI_NODE.detachChild(item);
    }

    public Picture getItem() {
        return item;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
        this.item.setWidth(x);
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
        this.item.setHeight(y);
    }
}
