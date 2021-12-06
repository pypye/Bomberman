package ui.gui.buffs;

import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector2f;
import ui.gui.ImageGui;
import ui.gui.ItemGui;
import ui.gui.LocationGui;
import ui.gui.TextGui;

public class BuffGui extends ItemGui {
    public static final float SIZE = 64f;
    private final ImageGui background;
    private final ImageGui item;
    private final TextGui textGui;

    public BuffGui(Vector2f position, String path) {
        background = new ImageGui(new Vector2f(64, 64), "Textures/Buffs/background.png");
        item = new ImageGui(new Vector2f(56, 56), path);
        textGui = new TextGui("0.0s", ColorRGBA.White, position);
        setPosition(position.x, position.y);
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
