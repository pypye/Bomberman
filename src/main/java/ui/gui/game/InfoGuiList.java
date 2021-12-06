package ui.gui.game;

import com.jme3.math.Vector2f;
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
        level = new InfoGui("Level: " + ((Game) SceneController.getCurrentScene()).getLevel(), new Vector2f(20, Main.HEIGHT - 70));
        enemy = new InfoGui("Enemy remain: " + Enemy.getCount(), new Vector2f(20, Main.HEIGHT - 130));
        time = new InfoGui("Time: " + (int) timeCount + 's', new Vector2f(20, Main.HEIGHT - 190));
        show();
    }

    public static void onUpdate(float tpf) {
        timeCount += tpf;
        level.setText("Level: " + ((Game) SceneController.getCurrentScene()).getLevel());
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
