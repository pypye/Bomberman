package ui.gui.buffs;

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
        buff.hide();
        reLocateBuffGUI();
    }

    public static ArrayList<BuffGui> getBuffList() {
        return buffList;
    }

    private static void reLocateBuffGUI() {
        int START = (int) ((Main.WIDTH - TOTAL_SIZE) / 2);
        for (BuffGui buffGui : buffList) {
            buffGui.locate(START, buffGui.getPosY());
            START += BuffGui.SIZE + MARGIN;
        }
    }
}
