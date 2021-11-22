package ui.gui;

import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.math.ColorRGBA;
import cores.Main;

public class TextGui extends ItemGui {
    private final BitmapText hudText;

    public TextGui(String text, ColorRGBA color, float posX, float posY) {
        BitmapFont guiFont = Main.ASSET_MANAGER.loadFont("Fonts/debussy.ttf.fnt");
        hudText = new BitmapText(guiFont, false);
        hudText.setSize(16);
        hudText.setColor(color);
        hudText.setText(text);
        hudText.setLocalTranslation(posX, posY, 0);
        this.sizeX = hudText.getLineWidth();
        this.sizeY = hudText.getLineHeight();
        setPosition(posX, posY);
    }

    @Override
    public void show() {
        Main.GUI_NODE.attachChild(hudText);
    }

    @Override
    public void hide() {
        Main.GUI_NODE.detachChild(hudText);
    }

    @Override
    public void setSizeX(float sizeX) {
    }

    @Override
    public void setSizeY(float sizeY) {
    }

    public void setText(String text) {
        hudText.setText(text);
    }

    @Override
    public float getSizeX() {
        return hudText.getLineWidth();
    }

    @Override
    public float getSizeY() {
        return hudText.getLineHeight();
    }

    @Override
    public void setPosition(float posX, float posY) {
        super.setPosition(posX, posY);
        hudText.setLocalTranslation(posX, posY, 0);
    }

}
