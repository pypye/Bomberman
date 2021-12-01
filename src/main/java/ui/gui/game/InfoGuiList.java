package ui.gui.game;

import cores.Main;
import entities.players.enemies.Enemy;
import scenes.Game;
import scenes.SceneController;

public class InfoGuiList {
    private static InfoGui level;
    private static InfoGui enemy;
    private static InfoGui time;
    private static double timeCount = 0f;

    public static void initialize() {
        timeCount = 0f;
        level = new InfoGui(20, Main.HEIGHT - 70, "Level: " + ((Game) SceneController.getCurrentScene()).getLevel());
        enemy = new InfoGui(20, Main.HEIGHT - 130, "Enemy remain: " + Enemy.getCount());
        time = new InfoGui(20, Main.HEIGHT - 190, "Time: " + (int) timeCount + 's');
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

    public static void remove() {
        level.remove();
        enemy.remove();
        time.remove();
    }
}
