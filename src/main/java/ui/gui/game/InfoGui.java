package ui.gui.game;

import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector2f;
import ui.gui.ImageGui;
import ui.gui.ItemGui;
import ui.gui.LocationGui;
import ui.gui.TextGui;

public class InfoGui extends ItemGui {
    private final TextGui text;
    private final ImageGui background;

    public InfoGui(String _text, Vector2f position) {
        text = new TextGui(_text, ColorRGBA.White, position, 24);
        background = new ImageGui(position, new Vector2f(text.getSize().x + LocationGui.PADDING, text.getSize().y + LocationGui.PADDING), "Textures/Menu/announcement_background.png");
        setPosition(position.x, position.y);
    }

    public InfoGui(String _text, Vector2f position, Vector2f size) {
        this(_text, position);
        setSize(size.x, size.y);
    }

    public void setText(String _text) {
        text.setText(_text);
        background.setSize(text.getSize().x + LocationGui.PADDING, text.getSize().y + LocationGui.PADDING);
        LocationGui.centerObject(text, background);
        text.setPosition(text.getPosition().x, text.getPosition().y + text.getSize().y);
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
        text.setPosition(text.getPosition().x, text.getPosition().y + LocationGui.PADDING + 5);
    }
}
