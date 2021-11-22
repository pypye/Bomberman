package ui.gui3d;


import com.jme3.scene.Spatial;
import ui.gui.ImageGui;
import ui.gui.ItemGui;

import java.util.ArrayList;

public class StatusBarGui3d extends ItemGui3d {
    private final ArrayList<ItemGui> part = new ArrayList<>();
    private final ArrayList<ItemGui> fill = new ArrayList<>();
    private final ItemGui background;
    private final ItemGui coolDown;
    private final ItemGui foreground;

    public StatusBarGui3d(Spatial link, int count, int current) {
        super(link);
        background = new ImageGui(60, 16, "Textures/Bar/Status/background.png");
        for (int i = 0; i < current; ++i) {
            ItemGui item = new ImageGui(60f / count, 16, "Textures/Bar/Status/fill.png");
            fill.add(item);
        }
        coolDown = new ImageGui(0, 16, "Textures/Bar/Status/cool_down.png");
        for (int i = 0; i < count; ++i) {
            ItemGui item = new ImageGui(60f / count, 16, "Textures/Bar/Status/part.png");
            part.add(item);
        }
        foreground = new ImageGui(60, 16, "Textures/Bar/Status/border.png");
    }

    public void setMaxCount(int count) {
        if (count > part.size()) {
            while (count != part.size()) {
                ItemGui item = new ImageGui(60f / count, 16, "Textures/Bar/Status/part.png");
                part.add(item);
            }
            for (ItemGui itemGui : part) {
                itemGui.setSizeX(60f / count);
            }
            for (ItemGui itemGui : fill) {
                itemGui.setSizeX(60f / count);
            }
        } else if (count < part.size()) {
            while (count != part.size()) {
                part.get(part.size() - 1).hide();
                part.remove(part.size() - 1);
            }
            for (ItemGui itemGui : part) {
                itemGui.setSizeX(60f / count);
            }
            for (ItemGui itemGui : fill) {
                itemGui.setSizeX(60f / count);
            }
        }

    }

    public void setCount(int count) {
        if (count > fill.size()) {
            while (count != fill.size()) {
                ItemGui item = new ImageGui(60f / part.size(), 16,"Textures/Bar/Status/fill.png");
                fill.add(item);
            }
        } else if (count < fill.size()) {
            while (count != fill.size()) {
                fill.get(fill.size() - 1).hide();
                fill.remove(fill.size() - 1);
            }
        }
    }

    public void setCoolDown(float size) {
        coolDown.setSizeX((60f / part.size()) * (size / 4));
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        hide();
        background.setPosition(screenCoords.x, screenCoords.y);
        foreground.setPosition(screenCoords.x, screenCoords.y);
        for (int i = 0; i < fill.size(); ++i) {
            ((ImageGui) fill.get(i)).getItem().setPosition(screenCoords.x + i * (60f / part.size()), screenCoords.y);
        }
        coolDown.setPosition(screenCoords.x + fill.size() * (60f / part.size()), screenCoords.y);
        for (int i = 0; i < part.size(); ++i) {
            ((ImageGui) part.get(i)).getItem().setPosition(screenCoords.x + i * (60f / part.size()), screenCoords.y);
        }
        show();
    }

    private void hidePart() {
        for (ItemGui itemGui : part) {
            itemGui.hide();
        }
    }

    private void showPart() {
        for (ItemGui itemGui : part) {
            itemGui.show();
        }
    }

    private void hideFill() {
        for (ItemGui itemGui : fill) {
            itemGui.hide();
        }
    }

    private void showFill() {
        for (ItemGui itemGui : fill) {
            itemGui.show();
        }
    }

    public void hide() {
        background.hide();
        coolDown.hide();
        hideFill();
        hidePart();
        foreground.hide();
    }
    public void show() {
        background.show();
        coolDown.show();
        showFill();
        showPart();
        foreground.show();
    }
}
