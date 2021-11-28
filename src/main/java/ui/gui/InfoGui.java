package ui.gui;

import com.jme3.math.ColorRGBA;

public class InfoGui extends ItemGui {
    private final TextGui text;
    private final ImageGui background;

    public InfoGui(float posX, float posY, String _text) {
        text = new TextGui(_text, ColorRGBA.White, posX, posY);
        background = new ImageGui(text.getSizeX() + LocationGui.PADDING, text.getSizeY() + LocationGui.PADDING, posX, posY, "Textures/Menu/announcement_background.png");
        setPosition(posX, posY);
    }

    public InfoGui(float posX, float posY, String _text, int sizeX, int sizeY) {
        text = new TextGui(_text, ColorRGBA.White, posX, posY);
        background = new ImageGui(text.getSizeX() + LocationGui.PADDING, text.getSizeY() + LocationGui.PADDING, posX, posY, "Textures/Menu/announcement_background.png");
        if (sizeX >= 0) setSizeX(sizeX);
        if (sizeY >= 0) setSizeY(sizeY);
        setPosition(posX, posY);
    }

    public void setText(String _text) {
        text.setText(_text);
        LocationGui.centerObject(text, background);
        text.setPosition(text.posX, text.posY + LocationGui.PADDING);
    }

    @Override
    public void show() {
        background.show();
        text.show();
    }

    @Override
    public void hide() {
        background.hide();
        text.hide();
    }

    @Override
    public void setSizeX(float sizeX) {
        super.setSizeX(sizeX);
        this.background.setSizeX(sizeX);
    }

    @Override
    public void setSizeY(float sizeY) {
        super.setSizeY(sizeY);
        this.background.setSizeY(sizeY);
    }

    @Override
    public void setPosition(float posX, float posY) {
        super.setPosition(posX, posY);
        background.setPosition(posX, posY);
        LocationGui.centerObject(text, background);
        text.setPosition(text.posX, text.posY + LocationGui.PADDING);
    }
}
