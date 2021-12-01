package ui.gui.buffs;

import com.jme3.math.ColorRGBA;
import ui.gui.ImageGui;
import ui.gui.ItemGui;
import ui.gui.LocationGui;
import ui.gui.TextGui;

public class BuffGui extends ItemGui {
    public static final float SIZE = 64f;
    private final ImageGui background;
    private final ImageGui item;
    private final TextGui textGui;

    public BuffGui(float posX, float posY, String path) {
        background = new ImageGui(64, 64, "Textures/Buffs/background.png");
        item = new ImageGui(56, 56, path);
        textGui = new TextGui("0.0s", ColorRGBA.White, posX, posY);
        setPosition(posX, posY);
    }

    @Override
    public void show() {
        background.show();
        item.show();
        textGui.show();
    }

    @Override
    public void remove() {
        background.remove();
        item.remove();
        textGui.remove();
    }

    @Override
    public void setSizeX(float sizeX) {
    }

    @Override
    public void setSizeY(float sizeY) {
    }

    @Override
    public void setPosition(float posX, float posY) {
        super.setPosition(posX, posY);
        LocationGui.anchorBottomLeft(background, posX, posY);
        LocationGui.centerObject(item, background);
    }

    public ItemGui getBackground() {
        return background;
    }

    public ItemGui getItem() {
        return item;
    }

    public TextGui getText() {
        return textGui;
    }
}
