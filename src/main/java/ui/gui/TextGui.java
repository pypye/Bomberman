package ui.gui;

import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.math.ColorRGBA;
import cores.Main;

public class TextGui extends ItemGui {
    private final BitmapText hudText;

    public TextGui(String text, int size, ColorRGBA color, float x, float y) {
        super(x, y);
        BitmapFont guiFont = Main.ASSET_MANAGER.loadFont("Fonts/debussy.ttf.fnt");
        hudText = new BitmapText(guiFont, false);
        hudText.setSize(size);
        hudText.setColor(color);
        hudText.setText(text);
        hudText.setLocalTranslation(x, y, 0);
        this.x = hudText.getLineWidth();
        this.y = hudText.getLineHeight();
    }

    @Override
    public void show() {
        Main.GUI_NODE.attachChild(hudText);
    }

    @Override
    public void hide() {
        Main.GUI_NODE.detachChild(hudText);
    }

    public void setText(String text) {
        hudText.setText(text);
    }

    @Override
    public void setX(float x) {
    }

    @Override
    public void setY(float y) {
    }

    @Override
    public void setPosition(float x, float y) {
        this.posX = x;
        this.posY = y;
        hudText.setLocalTranslation(x, y, 0);
    }

}
