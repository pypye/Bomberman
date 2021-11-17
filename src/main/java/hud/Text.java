package hud;

import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import cores.Main;

public class Text {
    private final BitmapText hudText;

    public Text(String text, int size, ColorRGBA color, float x, float y) {
        BitmapFont guiFont = Main.ASSET_MANAGER.loadFont("Fonts/debussy.ttf.fnt");
        hudText = new BitmapText(guiFont, false);
        hudText.setSize(size);
        hudText.setColor(color);
        hudText.setText(text);
        hudText.setLocalTranslation(x, y, 0);
    }

    public void show() {
        Main.GUI_NODE.attachChild(hudText);
    }

    public void hide() {
        Main.GUI_NODE.detachChild(hudText);
    }

    public void setText(String text) {
        hudText.setText(text);
    }

    public void setPosition(float x, float y) {
        hudText.setLocalTranslation(x, y, 0);
    }

    public Vector3f getPosition() {
        return hudText.getLocalTranslation();
    }

    public float getX() {
        return hudText.getLocalTranslation().x;
    }

    public float getY() {
        return hudText.getLocalTranslation().y;
    }
    public float getTextWidth(){
        return hudText.getLineWidth();
    }
    public float getTextHeight(){
        return hudText.getLineHeight();
    }
}
