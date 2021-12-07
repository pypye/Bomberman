package ui.gui.buffs;

import cores.Config;
import cores.Main;

import java.util.ArrayList;

public class BuffListGui {
    private static final ArrayList<BuffGui> buffList = new ArrayList<>();
    private static float TOTAL_SIZE = 0f;
    private static final float MARGIN = 10f;

    public static void addBuff(BuffGui buff) {
        if (buffList.size() != 0) TOTAL_SIZE += MARGIN;
        TOTAL_SIZE += BuffGui.SIZE;
        buffList.add(buff);
        buff.show();
        reLocateBuffGUI();
    }

    public static void removeBuff(BuffGui buff) {
        buffList.remove(buff);
        TOTAL_SIZE -= MARGIN;
        TOTAL_SIZE -= BuffGui.SIZE;
        if (buffList.size() == 0) TOTAL_SIZE = 0;
        buff.remove();
        reLocateBuffGUI();
    }

    public static void show() {
        for (BuffGui buff : buffList) {
            buff.show();
        }
    }

    public static void remove() {
        for (BuffGui buff : buffList) {
            buff.remove();
        }
    }

    public static ArrayList<BuffGui> getBuffList() {
        return buffList;
    }

    public static void reLocateBuffGUI() {
        int START = (int) ((Config.WIDTH - TOTAL_SIZE) / 2);
        for (BuffGui buffGui : buffList) {
            buffGui.setPosition(START, buffGui.getPosition().y);
            START += BuffGui.SIZE + MARGIN;
        }
    }
}
