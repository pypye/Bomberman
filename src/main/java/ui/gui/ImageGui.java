package ui.gui;

import com.jme3.math.Vector2f;
import com.jme3.ui.Picture;
import cores.Main;

public class ImageGui extends ItemGui {
    private final Picture item;

    public ImageGui(Vector2f position, Vector2f size, String path) {
        item = new Picture(path);
        item.setImage(Main.ASSET_MANAGER, path, true);
        setSize(size.x, size.y);
        setPosition(position.x, position.y);
    }

    public ImageGui(Vector2f size, String path) {
        item = new Picture(path);
        item.setImage(Main.ASSET_MANAGER, path, true);
        setSize(size.x, size.y);
    }

    public Picture getItem() {
        return item;
    }

    @Override
    public void show() {
        Main.GUI_NODE.attachChild(item);
    }

    @Override
    public void remove() {
        Main.GUI_NODE.detachChild(item);
    }

    @Override
    public void setSizeX(float sizeX) {
        super.setSizeX(sizeX);
        this.item.setWidth(sizeX);
    }

    @Override
    public void setSizeY(float sizeY) {
        super.setSizeY(sizeY);
        this.item.setHeight(sizeY);
    }
    @Override
    public void setSize(float sizeX, float sizeY) {
        setSizeX(sizeX);
        setSizeY(sizeY);
    }

    @Override
    public void setPosition(float posX, float posY) {
        super.setPosition(posX, posY);
        this.item.setPosition(posX, posY);
    }

    @Override
    public void setPositionX(float posX) {
        super.setPositionX(posX);
        this.item.setPosition(posX, this.getPosition().y);
    }

    @Override
    public void setPositionY(float posY) {
        super.setPositionY(posY);
        this.item.setPosition(this.getPosition().x, posY);
    }
}
