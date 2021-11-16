package gui;

import cores.Main;

public class LocationGUI {
    public static void centerX(ItemGUI gui, float height) {
        gui.getItem().setPosition(Main.WIDTH / 2 - gui.getX() / 2, height);
    }

    public static void centerY(ItemGUI gui, float width) {
        gui.getItem().setPosition(width, Main.HEIGHT / 2 - gui.getY() / 2);
    }

    public static void anchorBottomLeft(ItemGUI gui, float x, float y) {
        gui.getItem().setPosition(x, y);
    }

    public static void anchorBottomRight(ItemGUI gui, float x, float y) {
        gui.getItem().setPosition(Main.WIDTH - gui.getX() - x, y);
    }

    public static void anchorTopLeft(ItemGUI gui, float x, float y) {
        gui.getItem().setPosition(x, Main.HEIGHT - gui.getY() - y);
    }

    public static void anchorTopRight(ItemGUI gui, float x, float y) {
        gui.getItem().setPosition(Main.WIDTH - gui.getX() - x, Main.HEIGHT - gui.getY() - y);
    }
}
