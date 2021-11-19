package ui.gui.buffs;

import com.jme3.math.ColorRGBA;
import ui.gui.ItemGui;
import ui.gui.LocationGui;
import ui.gui.TextGui;

public class BuffGui {
    public static final float SIZE = 64f;
    private final ItemGui background;
    private final ItemGui item;
    private final TextGui textGui;
    private final float posX;
    private final float posY;

    public BuffGui(float posX, float posY, String path) {
        background = new ItemGui(64, 64, "Textures/Buffs/background.png");
        item = new ItemGui(56, 56, path);
        textGui = new TextGui("0.0s", 16, ColorRGBA.White, posX, posY);
        this.posX = posX;
        this.posY = posY;
    }

    public void show() {
        background.show();
        item.show();
        textGui.show();
    }

    public void hide() {
        background.hide();
        item.hide();
        textGui.hide();
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

    public float getPosX() {
        return posX;
    }

    public float getPosY() {
        return posY;
    }

    public void locate(float posX, float posY) {
        LocationGui.anchorBottomLeft(background, posX, posY);
        LocationGui.centerObject(item, background);
    }
}
