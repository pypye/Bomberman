package ui.gui3d;

import com.jme3.math.Vector2f;
import com.jme3.scene.Spatial;
import ui.gui.ImageGui;
import ui.gui.ItemGui;

public class ShieldGui3d extends ItemGui3d {
    private final ItemGui shield;

    public ShieldGui3d(Spatial link) {
        super(link);
        shield = new ImageGui(new Vector2f(120, 120), "Textures/Buffs/shield_active.png");
        show();
    }

    public void show() {
        shield.show();
    }

    public void remove() {
        shield.remove();
    }

    public void onUpdate(boolean shieldBuffActivated) {
        super.onUpdate();
        if (shieldBuffActivated) shield.show();
        else shield.remove();
        screenCoords.x -= 30;
        screenCoords.y -= 100;
        shield.setPosition(screenCoords.x, screenCoords.y);
    }
}
