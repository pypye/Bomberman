package ui.gui;

import cores.Main;

public class LocationGui {
    public static void centerX(ItemGui gui, float height) {
        gui.setPosition(Main.WIDTH / 2 - gui.getX() / 2, height);
    }

    public static void centerY(ItemGui gui, float width) {
        gui.setPosition(width, Main.HEIGHT / 2 - gui.getY() / 2);
    }

    public static void anchorBottomLeft(ItemGui gui, float x, float y) {
        gui.setPosition(x, y);
    }

    public static void anchorBottomRight(ItemGui gui, float x, float y) {
        gui.setPosition(Main.WIDTH - gui.getX() - x, y);
    }

    public static void anchorTopLeft(ItemGui gui, float x, float y) {
        gui.setPosition(x, Main.HEIGHT - gui.getY() - y);
    }

    public static void anchorTopRight(ItemGui gui, float x, float y) {
        gui.setPosition(Main.WIDTH - gui.getX() - x, Main.HEIGHT - gui.getY() - y);
    }

    public static void centerXObject(ItemGui child, ItemGui parent, float height) {
        child.setPosition(parent.getPosX() + (parent.getX() / 2 - child.getX() / 2), height);
    }

    public static void centerYObject(ItemGui child, ItemGui parent, float width) {
        child.setPosition(width, parent.getPosY() + (parent.getY() / 2 - child.getY() / 2));
    }

    public static void centerObject(ItemGui child, ItemGui parent) {
        child.setPosition(parent.getPosX() + (parent.getX() / 2 - child.getX() / 2), parent.getPosY() + (parent.getY() / 2 - child.getY() / 2));

    }
}
