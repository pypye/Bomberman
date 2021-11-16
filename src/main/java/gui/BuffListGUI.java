package gui;

import cores.Main;

import java.util.ArrayList;

public class BuffListGUI {
    private static final ArrayList<BuffGUI> buffList = new ArrayList<>();
    private static float TOTAL_SIZE = 0f;
    private static final float MARGIN = 10f;

    public static void addBuff(BuffGUI buff) {
        if (buffList.size() != 0) TOTAL_SIZE += MARGIN;
        TOTAL_SIZE += BuffGUI.SIZE;
        buffList.add(buff);
        buff.bg.show();
        buff.item.show();
        System.out.println("[DEBUG/Buff] Add buff " + buff);
        reLocateBuffGUI();
    }

    public static void removeBuff(BuffGUI buff) {
        buffList.remove(buff);
        TOTAL_SIZE -= MARGIN;
        TOTAL_SIZE -= BuffGUI.SIZE;
        if (buffList.size() == 0) TOTAL_SIZE = 0;
        buff.bg.hide();
        buff.item.hide();
        System.out.println("[DEBUG/Buff] Remove buff " + buff);
        reLocateBuffGUI();
    }

    public static ArrayList<BuffGUI> getBuffList() {
        return buffList;
    }

    private static void reLocateBuffGUI() {
        int START = (int) ((Main.WIDTH - TOTAL_SIZE) / 2);
        for (BuffGUI buffGUI : buffList) {
            LocationGUI.anchorBottomLeft(buffGUI.item, START + 4, buffGUI.y);
            LocationGUI.anchorBottomLeft(buffGUI.bg, START, buffGUI.y);
            START += BuffGUI.SIZE + MARGIN;
        }
    }
}
