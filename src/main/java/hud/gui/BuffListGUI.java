package hud.gui;

import cores.Main;
import hud.LocationGUI;
import hud.Text;

import java.util.ArrayList;

public class BuffListGUI {
    private static final ArrayList<BuffGUI> buffList = new ArrayList<>();
    private static final ArrayList<Text> buffText = new ArrayList<>();
    private static float TOTAL_SIZE = 0f;
    private static final float MARGIN = 10f;


    public static void addBuff(BuffGUI buff, Text text) {
        if (buffList.size() != 0) TOTAL_SIZE += MARGIN;
        TOTAL_SIZE += BuffGUI.SIZE;
        buffList.add(buff);
        buffText.add(text);
        buff.bg.show();
        buff.item.show();
        text.show();
        System.out.println("[DEBUG/Buff] Add buff " + buff);
        reLocateBuffGUI();
    }

    public static void removeBuff(BuffGUI buff, Text text) {
        buffList.remove(buff);
        buffText.remove(text);
        TOTAL_SIZE -= MARGIN;
        TOTAL_SIZE -= BuffGUI.SIZE;
        if (buffList.size() == 0) TOTAL_SIZE = 0;
        buff.bg.hide();
        buff.item.hide();
        text.hide();
        System.out.println("[DEBUG/Buff] Remove buff " + buff);
        reLocateBuffGUI();
    }

    public static ArrayList<BuffGUI> getBuffList() {
        return buffList;
    }

    private static void reLocateBuffGUI() {
        int START = (int) ((Main.WIDTH - TOTAL_SIZE) / 2);
        for (int i = 0; i < buffList.size(); ++i) {
            LocationGUI.anchorBottomLeft(buffList.get(i).bg, START, buffList.get(i).y);
            LocationGUI.centerObject(buffList.get(i).item, buffList.get(i).bg);
            LocationGUI.centerXObject(buffText.get(i), buffList.get(i).bg, buffText.get(i).getY());
            START += BuffGUI.SIZE + MARGIN;
        }
    }
}
