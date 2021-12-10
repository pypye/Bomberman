package ui.gui;

import cores.Config;

public class LocationGui {
    public static final int PADDING = 20;

    public static void centerX(ItemGui gui, float height) {
        gui.setPosition(Config.WIDTH / 2f - gui.getSize().x / 2f, height);
    }

    public static void centerY(ItemGui gui, float width) {
        gui.setPosition(width, Config.HEIGHT / 2f - gui.getSize().y / 2f);
    }

    public static void anchorBottomLeft(ItemGui gui, float x, float y) {
        gui.setPosition(x, y);
    }

    public static void anchorBottomRight(ItemGui gui, float x, float y) {
        gui.setPosition(Config.WIDTH - gui.getSize().x - x, y);
    }

    public static void anchorTopLeft(ItemGui gui, float x, float y) {
        gui.setPosition(x, Config.HEIGHT - gui.getSize().y - y);
    }

    public static void anchorTopRight(ItemGui gui, float x, float y) {
        gui.setPosition(Config.WIDTH - gui.getSize().x - x, Config.HEIGHT - gui.getSize().y - y);
    }

    public static void centerXObject(ItemGui child, ItemGui parent, float height) {
        child.setPosition(parent.getPosition().x + (parent.getSize().x / 2 - child.getSize().x / 2), height);
    }

    public static void centerYObject(ItemGui child, ItemGui parent, float width) {
        child.setPosition(width, parent.getPosition().y + (parent.getSize().y / 2 - child.getSize().y / 2));
    }

    public static void centerObject(ItemGui child, ItemGui parent) {
        child.setPosition(parent.getPosition().x + (parent.getSize().x / 2 - child.getSize().x / 2), parent.getPosition().y + (parent.getSize().y / 2 - child.getSize().y / 2));
    }

    public static void anchorTopRightObject(ItemGui child, ItemGui parent, float x, float y) {
        child.setPosition(parent.getPosition().x + parent.getSize().x - child.getSize().x - x, parent.getPosition().y + parent.getSize().y - child.getSize().y - y);
    }

    public static void anchorTopLeftObject(ItemGui child, ItemGui parent, float x, float y) {
        child.setPosition(parent.getPosition().x + x, parent.getPosition().y + parent.getSize().y - child.getSize().y - y);
    }

    public static void anchorBottomLeftObject(ItemGui child, ItemGui parent, float x, float y) {
        child.setPosition(parent.getPosition().x + x, parent.getPosition().y + y);
    }

    public static void anchorBottomRightObject(ItemGui child, ItemGui parent, float x, float y) {
        child.setPosition(parent.getPosition().x + parent.getSize().x - child.getSize().x - x, parent.getPosition().y + y);
    }
}
