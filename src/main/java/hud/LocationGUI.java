package hud;

import cores.Main;

public class LocationGUI {
    public static void centerX(ItemGUI gui, float height) {
        gui.setPosition(Main.WIDTH / 2 - gui.getX() / 2, height);
    }

    public static void centerY(ItemGUI gui, float width) {
        gui.setPosition(width, Main.HEIGHT / 2 - gui.getY() / 2);
    }

    public static void anchorBottomLeft(ItemGUI gui, float x, float y) {
        gui.setPosition(x, y);
    }

    public static void anchorBottomRight(ItemGUI gui, float x, float y) {
        gui.setPosition(Main.WIDTH - gui.getX() - x, y);
    }

    public static void anchorTopLeft(ItemGUI gui, float x, float y) {
        gui.setPosition(x, Main.HEIGHT - gui.getY() - y);
    }

    public static void anchorTopRight(ItemGUI gui, float x, float y) {
        gui.setPosition(Main.WIDTH - gui.getX() - x, Main.HEIGHT - gui.getY() - y);
    }

    public static void centerXObject(Object gui, ItemGUI parent, float height) {
        if (gui instanceof ItemGUI) {
            ((ItemGUI) gui).setPosition(parent.getPosX() + (parent.getX() / 2 - ((ItemGUI) gui).getX() / 2), height);
        }
        if (gui instanceof Text) {
            ((Text) gui).setPosition(parent.getPosX() + (parent.getX() / 2 - ((Text) gui).getTextWidth() / 2), height);
        }
    }

    public static void centerYObject(Object gui, ItemGUI parent, float width) {
        if (gui instanceof ItemGUI) {
            ((ItemGUI) gui).setPosition(width, parent.getPosY() + (parent.getY() / 2 - ((ItemGUI) gui).getY() / 2));
        }
        if (gui instanceof Text) {
            ((Text) gui).setPosition(width, parent.getPosY() + (parent.getY() / 2 - ((Text) gui).getTextHeight() / 2));
        }
    }

    public static void centerObject(Object gui, ItemGUI parent) {
        if (gui instanceof ItemGUI) {
            ((ItemGUI) gui).setPosition(parent.getPosX() + (parent.getX() / 2 - ((ItemGUI) gui).getX() / 2), parent.getPosY() + (parent.getY() / 2 - ((ItemGUI) gui).getY() / 2));
        }
        if (gui instanceof Text) {
            ((Text) gui).setPosition(parent.getPosX() + (parent.getX() / 2 - ((Text) gui).getTextWidth() / 2), parent.getPosY() + (parent.getY() / 2 - ((Text) gui).getTextHeight() / 2));
        }
    }
}
