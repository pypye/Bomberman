package ui.gui;

import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector2f;
import cores.Main;

public class TextGui extends ItemGui {
    private final BitmapText hudText;

    public TextGui(String text, ColorRGBA color) {
        BitmapFont guiFont = Main.ASSET_MANAGER.loadFont("Fonts/debussy_small.ttf.fnt");
        hudText = new BitmapText(guiFont, false);
        hudText.setSize(16);
        hudText.setColor(color);
        hudText.setText(text);
    }

    public TextGui(String text, ColorRGBA color, Vector2f position) {
        BitmapFont guiFont = Main.ASSET_MANAGER.loadFont("Fonts/debussy_small.ttf.fnt");
        hudText = new BitmapText(guiFont, false);
        hudText.setSize(16);
        hudText.setColor(color);
        hudText.setText(text);
        hudText.setLocalTranslation(position.x, position.y, 0);
        setPosition(position.x, position.y);
    }

    public TextGui(String text, ColorRGBA color, int fontSize) {
        BitmapFont guiFont = Main.ASSET_MANAGER.loadFont("Fonts/debussy.ttf.fnt");
        hudText = new BitmapText(guiFont, false);
        hudText.setSize(fontSize);
        hudText.setColor(color);
        hudText.setText(text);
    }

    public TextGui(String text, ColorRGBA color, Vector2f position, int fontSize) {
        BitmapFont guiFont = Main.ASSET_MANAGER.loadFont("Fonts/debussy.ttf.fnt");
        hudText = new BitmapText(guiFont, false);
        hudText.setSize(fontSize);
        hudText.setColor(color);
        hudText.setText(text);
        hudText.setLocalTranslation(position.x, position.y, 0);
        setPosition(position.x, position.y);
    }

    @Override
    public void show() {
        Main.GUI_NODE.attachChild(hudText);
    }

    @Override
    public void remove() {
        Main.GUI_NODE.detachChild(hudText);
    }


    public void setText(String text) {
        hudText.setText(text);
    }

    public String getText() {
        return hudText.getText();
    }

    @Override
    public void setSize(float sizeX, float sizeY) {
    }

    @Override
    public void setSizeX(float sizeX) {
    }

    @Override
    public void setSizeY(float sizeY) {
    }

    @Override
    public Vector2f getSize() {
        return new Vector2f(hudText.getLineWidth(), hudText.getLineHeight());
    }

    @Override
    public void setPosition(float posX, float posY) {
        super.setPosition(posX, posY);
        hudText.setLocalTranslation(posX, posY, 0);
    }

    @Override
    public void setPositionX(float posX) {
        super.setPositionX(posX);
        hudText.setLocalTranslation(posX, hudText.getLocalTranslation().getY(), 0);
    }

    @Override
    public void setPositionY(float posY) {
        super.setPositionY(posY);
        hudText.setLocalTranslation(hudText.getLocalTranslation().getX(), posY, 0);
    }

}
