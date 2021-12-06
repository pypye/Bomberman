package ui.gui3d;

import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;
import cores.Main;

public class ItemGui3d {
    private final Spatial link;
    Vector3f screenCoords;

    public ItemGui3d(Spatial link) {
        this.link = link;
        screenCoords = Main.CAM.getScreenCoordinates(link.getWorldTranslation());
        if (screenCoords.z > 1) screenCoords.x = screenCoords.y = -10000;
        screenCoords.x -= 30;
        screenCoords.y += 70;
    }

    public void onUpdate() {
        screenCoords = Main.CAM.getScreenCoordinates(link.getWorldTranslation());
        if (screenCoords.z > 1) screenCoords.x = screenCoords.y = -10000;
        screenCoords.x -= 30;
        screenCoords.y += 70;
    }
}
