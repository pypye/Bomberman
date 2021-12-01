package ui.gui.game;

import com.jme3.math.ColorRGBA;
import ui.gui.ImageGui;
import ui.gui.ItemGui;
import ui.gui.LocationGui;
import ui.gui.TextGui;

public class InfoGui extends ItemGui {
    private final TextGui text;
    private final ImageGui background;

    public InfoGui(float posX, float posY, String _text) {
        text = new TextGui(_text, ColorRGBA.White, posX, posY, 24);
        background = new ImageGui(text.getSizeX() + LocationGui.PADDING, text.getSizeY() + LocationGui.PADDING, posX, posY, "Textures/Menu/announcement_background.png");
        setPosition(posX, posY);
    }

    public InfoGui(float posX, float posY, String _text, int sizeX, int sizeY) {
        text = new TextGui(_text, ColorRGBA.White, posX, posY, 24);
        background = new ImageGui(text.getSizeX() + LocationGui.PADDING, text.getSizeY() + LocationGui.PADDING, posX, posY, "Textures/Menu/announcement_background.png");
        if (sizeX >= 0) setSizeX(sizeX);
        if (sizeY >= 0) setSizeY(sizeY);
        setPosition(posX, posY);
    }

    public void setText(String _text) {
        text.setText(_text);
        setSizeX(text.getSizeX() + LocationGui.PADDING);
        setSizeY(text.getSizeY() + LocationGui.PADDING);
        LocationGui.centerObject(text, background);
        text.setPosition(text.getPosX(), text.getPosY() + LocationGui.PADDING + 5);
    }

    @Override
    public void show() {
        background.show();
        text.show();
    }

    @Override
    public void remove() {
        background.remove();
        text.remove();
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
        text.setPosition(text.getPosX(), text.getPosY() + LocationGui.PADDING + 5);
    }
}
