package ui.gui;

import com.jme3.math.ColorRGBA;

public class ButtonGui extends ItemGui {
    private final TextGui text;
    private final ImageGui button;

    public ButtonGui(float posX, float posY, String _text) {
        text = new TextGui(_text, ColorRGBA.Black, posX, posY);
        button = new ImageGui(text.getSizeX() + LocationGui.PADDING, text.getSizeY() + LocationGui.PADDING, posX, posY, "Textures/Menu/Button.png");
        setPosition(posX, posY);
        show();
    }

    @Override
    public void show() {
        button.show();
        text.show();
    }

    @Override
    public void hide() {
        button.hide();
        text.hide();
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
        button.setPosition(posX, posY);
        LocationGui.centerObject(text, button);
        text.setPosition(text.posX, text.posY + LocationGui.PADDING);
    }
}
