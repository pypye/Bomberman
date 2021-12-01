package ui.gui;

import com.jme3.ui.Picture;
import cores.Main;

public class ImageGui extends ItemGui {
    private final Picture item;

    public ImageGui(float sizeX, float sizeY, float posX, float posY, String path) {
        item = new Picture(path);
        item.setImage(Main.ASSET_MANAGER, path, true);
        setSizeX(sizeX);
        setSizeY(sizeY);
        setPosition(posX, posY);
    }

    public ImageGui(float sizeX, float sizeY, String path) {
        item = new Picture(path);
        item.setImage(Main.ASSET_MANAGER, path, true);
        setSizeX(sizeX);
        setSizeY(sizeY);
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

    public Picture getItem() {
        return item;
    }

    @Override
    public void setPosition(float posX, float posY) {
        super.setPosition(posX, posY);
        this.item.setPosition(posX, posY);
    }
}
