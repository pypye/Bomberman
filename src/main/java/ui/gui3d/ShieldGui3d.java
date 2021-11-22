package ui.gui3d;

import com.jme3.scene.Spatial;
import ui.gui.ImageGui;
import ui.gui.ItemGui;

public class ShieldGui3d extends ItemGui3d {
    private final ItemGui shield;

    public ShieldGui3d(Spatial link) {
        super(link);
        shield = new ImageGui(120, 120, "Textures/Buffs/shield_active.png");
        show();
    }

    public void show() {
        shield.show();
    }

    public void hide() {
        shield.hide();
    }

    public void onUpdate(boolean shieldBuffActivated) {
        super.onUpdate();
        if (shieldBuffActivated) shield.show();
        else shield.hide();
        screenCoords.x -= 30;
        screenCoords.y -= 100;
        shield.setPosition(screenCoords.x, screenCoords.y);
    }
}
