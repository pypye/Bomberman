package ui.gui;

import com.jme3.input.controls.ActionListener;
import com.jme3.math.Vector2f;
import cores.Debugger;
import cores.Main;

public abstract class ImageButtonGui extends ItemGui {
    private final ImageGui button;
    private boolean active = true;
    public ImageButtonGui(int sizeX, int sizeY, float posX, float posY, String path) {
        button = new ImageGui(sizeX, sizeY, posX, posY, path);
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
                        Debugger.log(Debugger.EVENT, "Clicked " + this);
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
    }

    @Override
    public void remove() {
        button.remove();
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
    }
}
