package ui.gui;

import cores.Main;

public class LocationGui {
    public static final int PADDING = 20;

    public static void centerX(ItemGui gui, float height) {
        gui.setPosition(Main.WIDTH / 2f - gui.getSizeX() / 2f, height);
    }

    public static void centerY(ItemGui gui, float width) {
        gui.setPosition(width, Main.HEIGHT / 2f - gui.getSizeY() / 2f);
    }

    public static void anchorBottomLeft(ItemGui gui, float x, float y) {
        gui.setPosition(x, y);
    }

    public static void anchorBottomRight(ItemGui gui, float x, float y) {
        gui.setPosition(Main.WIDTH - gui.getSizeX() - x, y);
    }

    public static void anchorTopLeft(ItemGui gui, float x, float y) {
        gui.setPosition(x, Main.HEIGHT - gui.getSizeY() - y);
    }

    public static void anchorTopRight(ItemGui gui, float x, float y) {
        gui.setPosition(Main.WIDTH - gui.getSizeX() - x, Main.HEIGHT - gui.getSizeY() - y);
    }

    public static void centerXObject(ItemGui child, ItemGui parent, float height) {
        child.setPosition(parent.getPosX() + (parent.getSizeX() / 2 - child.getSizeX() / 2), height);
    }

    public static void centerYObject(ItemGui child, ItemGui parent, float width) {
        child.setPosition(width, parent.getPosY() + (parent.getSizeY() / 2 - child.getSizeY() / 2));
    }

    public static void centerObject(ItemGui child, ItemGui parent) {
        child.setPosition(parent.getPosX() + (parent.getSizeX() / 2 - child.getSizeX() / 2), parent.getPosY() + (parent.getSizeY() / 2 - child.getSizeY() / 2));
    }

    public static void anchorTopRightObject(ItemGui child, ItemGui parent, float x, float y) {
        child.setPosition(parent.getPosX() + parent.getSizeX() - child.getSizeX() - x, parent.getPosY() + parent.getSizeY() - child.getSizeY() - y);
    }

    public static void anchorTopLeftObject(ItemGui child, ItemGui parent, float x, float y) {
        child.setPosition(parent.getPosX() + x, parent.getPosY() + parent.getSizeY() - child.getSizeY() - y);
    }

    public static void anchorBottomLeftObject(ItemGui child, ItemGui parent, float x, float y) {
        child.setPosition(parent.getPosX() + x, parent.getPosY() + y);
    }

    public static void anchorBottomRightObject(ItemGui child, ItemGui parent, float x, float y) {
        child.setPosition(parent.getPosX() + parent.getSizeX() - child.getSizeX() - x, parent.getPosY() + y);
    }
}
