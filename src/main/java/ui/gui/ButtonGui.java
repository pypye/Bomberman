package ui.gui;

import com.jme3.input.controls.ActionListener;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector2f;
import cores.Main;

public abstract class ButtonGui extends ItemGui {
    private final TextGui text;
    private final ImageGui button;

    public ButtonGui(float posX, float posY, String _text) {
        text = new TextGui(_text, ColorRGBA.Black, posX, posY);
        button = new ImageGui(text.getSizeX() + LocationGui.PADDING, text.getSizeY() + LocationGui.PADDING, posX, posY, "Textures/Menu/button_long.png");
        setPosition(posX, posY);
        Main.INPUT_MANAGER.addListener(actionListener, "LClick");
    }

    public ButtonGui(float posX, float posY, String _text, int sizeX, int sizeY) {
        text = new TextGui(_text, ColorRGBA.Black, posX, posY);
        button = new ImageGui(text.getSizeX() + LocationGui.PADDING, text.getSizeY() + LocationGui.PADDING, posX, posY, "Textures/Menu/button_long.png");
        if (sizeX >= 0) setSizeX(sizeX);
        if (sizeY >= 0) setSizeY(sizeY);
        setPosition(posX, posY);
        Main.INPUT_MANAGER.addListener(actionListener, "LClick");
    }

    private final ActionListener actionListener = new ActionListener() {
        @Override
        public void onAction(String name, boolean keyPressed, float tpf) {
            if (keyPressed) {
                Vector2f mousePos = Main.INPUT_MANAGER.getCursorPosition();
                if (getPosX() <= mousePos.x && getPosY() <= mousePos.y
                        && getPosX() + getSizeX() >= mousePos.x
                        && getPosY() + getSizeY() >= mousePos.y) {
                    onClick();
                    System.out.println("[Debug/Event] Clicked " + this + ": " + text.getText());
                }
            }
        }
    };

    public abstract void onClick();


    @Override
    public void show() {
        button.show();
        text.show();
    }

    @Override
    public void remove() {
        button.remove();
        text.remove();
        Main.INPUT_MANAGER.removeListener(actionListener);
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
        text.setPosition(text.posX, text.posY + LocationGui.PADDING);
    }
}
