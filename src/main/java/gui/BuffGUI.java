package gui;

public class BuffGUI {
    public static final float SIZE = 64f;
    protected ItemGUI bg;
    protected ItemGUI item;
    protected float x;
    protected float y;

    public BuffGUI(float x, float y, String path) {
        bg = new ItemGUI(64, 64, "Textures/PowerUp/background.png");
        item = new ItemGUI(56, 56, path);
        this.x = x;
        this.y = y;

    }
}
