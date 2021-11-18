package ui.gui3d;

import com.jme3.material.Material;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import cores.Main;
import ui.gui.buffs.BuffGui;
import ui.gui.buffs.SpeedBuffGui;

public class ItemGui3d {
    private final Spatial fakePoint;
    private final Spatial link;
    private final BuffGui buffGUI;

    public ItemGui3d(Spatial link) {
        this.link = link;
        Material material = new Material(Main.ASSET_MANAGER, "Common/MatDefs/Misc/Unshaded.j3md");
        fakePoint = new Geometry("Box", new Box(0f, 0f, 0f));
        fakePoint.setMaterial(material);
        fakePoint.setLocalTranslation(link.getLocalTranslation());
        buffGUI = new SpeedBuffGui(0, 0);
        buffGUI.show();
        Main.ROOT_NODE.attachChild(fakePoint);
    }

    public void onUpdate() {
        fakePoint.setLocalTranslation(link.getLocalTranslation());
        Vector3f screenCoords = Main.CAM.getScreenCoordinates(fakePoint.getWorldTranslation());
        if (screenCoords.z > 1) screenCoords.x = screenCoords.y = -10000;
        buffGUI.getItem().setPosition(screenCoords.x, screenCoords.y);
    }
}
