package ui.gui;

import cores.Main;
import entities.players.enemies.Enemy;
import scenes.Game;
import scenes.SceneController;

public class InfoGuiList {
    private static InfoGui level;
    private static InfoGui enemy;
    private static InfoGui time;
    private static double timeCount = 0f;

    public static void init() {
        timeCount = 0f;
        level = new InfoGui(20, Main.HEIGHT - 50, "Level: " + ((Game) SceneController.getCurrentScene()).getLevel());
        enemy = new InfoGui(20, Main.HEIGHT - 100, "Enemy remain: " + Enemy.getCount());
        time = new InfoGui(20, Main.HEIGHT - 150, "Time: " + (int) timeCount + 's');
        show();
    }

    public static void onUpdate(float tpf) {
        timeCount += tpf;
        enemy.setText("Enemy remain: " + Enemy.getCount());
        time.setText("Time: " + (int) timeCount + 's');
    }

    public static void show() {
        level.show();
        enemy.show();
        time.show();
    }

    public static void hide() {
        level.hide();
        enemy.hide();
        time.hide();
    }
}
