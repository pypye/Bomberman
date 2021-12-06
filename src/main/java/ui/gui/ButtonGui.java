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
    public static final String BUTTON_CANCEL = "Textures/Menu/button_long_cancel.png";
    public static final String BUTTON_INPUT = "Textures/Menu/button_long_input.png";


    public ButtonGui(float posX, float posY, String _text) {
        text = new TextGui(_text, ColorRGBA.Black, posX, posY);
        button = new ImageGui(text.getSizeX() + LocationGui.PADDING, text.getSizeY() + LocationGui.PADDING, posX, posY, BUTTON_DEFAULT);
        setPosition(posX, posY);
        Main.INPUT_MANAGER.removeListener(actionListener);
        Main.INPUT_MANAGER.addListener(actionListener, "LClick");
    }

    public ButtonGui(float posX, float posY, String _text, int sizeX, int sizeY) {
        text = new TextGui(_text, ColorRGBA.Black, posX, posY);
        button = new ImageGui(text.getSizeX() + LocationGui.PADDING, text.getSizeY() + LocationGui.PADDING, posX, posY, BUTTON_DEFAULT);
        if (sizeX >= 0) setSizeX(sizeX);
        if (sizeY >= 0) setSizeY(sizeY);
        setPosition(posX, posY);
        Main.INPUT_MANAGER.addListener(actionListener, "LClick");
    }
    public ButtonGui(float posX, float posY, String _text, int sizeX, int sizeY, String path) {
        text = new TextGui(_text, ColorRGBA.Black, posX, posY);
        button = new ImageGui(text.getSizeX() + LocationGui.PADDING, text.getSizeY() + LocationGui.PADDING, posX, posY, path);
        if (sizeX >= 0) setSizeX(sizeX);
        if (sizeY >= 0) setSizeY(sizeY);
        setPosition(posX, posY);
        Main.INPUT_MANAGER.addListener(actionListener, "LClick");
    }

    private final ActionListener actionListener = new ActionListener() {
        @Override
        public void onAction(String name, boolean keyPressed, float tpf) {
            if (active) {
                if (keyPressed && name.equals("LClick")) {
                    Vector2f mousePos = Main.INPUT_MANAGER.getCursorPosition();
                    if (getPosX() <= mousePos.x && getPosY() <= mousePos.y
                            && getPosX() + getSizeX() >= mousePos.x
                            && getPosY() + getSizeY() >= mousePos.y) {
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
        text.setPosition(text.posX, text.posY + LocationGui.PADDING - 2.5f);
    }
}
