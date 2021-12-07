package ui.gui;

import com.jme3.input.controls.ActionListener;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector2f;
import cores.Debugger;
import cores.Main;

public abstract class ButtonGui extends ItemGui {
    private final TextGui text;
    private final ImageGui button;
    private boolean active = true;

    public static final String BUTTON_DEFAULT = "Textures/Menu/button_long.png";

    public ButtonGui(String _text, Vector2f position) {
        text = new TextGui(_text, ColorRGBA.Black, position);
        button = new ImageGui(position, text.getSize().add(new Vector2f(20, 20)), BUTTON_DEFAULT);
        setPosition(position.x, position.y);
        Main.INPUT_MANAGER.removeListener(actionListener);
        Main.INPUT_MANAGER.addListener(actionListener, "LClick");
    }

    public ButtonGui(String _text, Vector2f position, Vector2f size) {
        text = new TextGui(_text, ColorRGBA.Black, position);
        button = new ImageGui(position, text.getSize().add(new Vector2f(20, 20)), BUTTON_DEFAULT);
        setSize(size.x, size.y);
        setPosition(position.x, position.y);
        Main.INPUT_MANAGER.removeListener(actionListener);
        Main.INPUT_MANAGER.addListener(actionListener, "LClick");
    }

    private final ActionListener actionListener = new ActionListener() {
        @Override
        public void onAction(String name, boolean keyPressed, float tpf) {
            if (active) {
                if (keyPressed && name.equals("LClick")) {
                    Vector2f mousePos = Main.INPUT_MANAGER.getCursorPosition();
                    if (getPosition().x <= mousePos.x && getPosition().y <= mousePos.y
                            && getPosition().x + getSize().x >= mousePos.x
                            && getPosition().y + getSize().y >= mousePos.y) {
                        onClick();
                        Debugger.log(Debugger.EVENT, "Clicked " + this + ": " + text.getText());
                    }
                }
            }
        }
    };

    public abstract void onClick();

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public void show() {
        button.show();
        text.show();
        active = true;
    }

    @Override
    public void remove() {
        button.remove();
        text.remove();
        active = false;
    }

    @Override
    public void setSize(float sizeX, float sizeY) {
        setSizeX(sizeX);
        setSizeY(sizeY);
    }

    @Override
    public void setSizeX(float sizeX) {
        super.setSizeX(sizeX);
        this.button.setSizeX(sizeX);
    }

    @Override
    public void setSizeY(float sizeY) {
        super.setSizeY(sizeY);
        this.button.setSizeY(sizeY);
    }

    @Override
    public void setPosition(float posX, float posY) {
        super.setPosition(posX, posY);
        button.setPosition(posX, posY);
        LocationGui.centerObject(text, button);
        text.setPosition(text.getPosition().x, text.getPosition().y + text.getSize().y - 2.5f);
    }
}
