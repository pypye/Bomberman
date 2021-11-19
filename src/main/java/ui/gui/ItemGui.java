package ui.gui;

import com.jme3.ui.Picture;
import cores.Main;

public class ItemGui {
    private Picture item;
    protected float x;
    protected float y;
    protected float posX;
    protected float posY;

    public ItemGui(float sizeX, float sizeY, String path) {
        item = new Picture(path);
        item.setImage(Main.ASSET_MANAGER, path, true);
        this.setX(sizeX);
        this.setY(sizeY);
    }

    protected ItemGui(float posX, float posY) {
        this.posX = posX;
        this.posY = posY;
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

    public void setPosition(float x, float y) {
        this.posX = x;
        this.posY = y;
        this.item.setPosition(x, y);
    }

    public float getPosX() {
        return posX;
    }

    public float getPosY() {
        return posY;
    }
}
