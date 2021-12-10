package scenes;

import cores.Debugger;
import cores.Main;
import org.json.JSONArray;

public class SceneController {
    private static Scene currentScene;
    public static boolean trigger = false;
    public static boolean multi = false;
    public static JSONArray map;
    public static JSONArray player;
    public static void setScene(Scene scene) {
        if (currentScene != null) currentScene.remove();
        Debugger.log(Debugger.SCENE, "Delete all data from " + currentScene);
        Main.ROOT_NODE.detachAllChildren();
        Main.GUI_NODE.detachAllChildren();
        Debugger.log(Debugger.SCENE, "Delete all data from world");
        Debugger.log(Debugger.SCENE, "Set scene from " + currentScene + " to " + scene);
        currentScene = scene;
        multi = currentScene instanceof GameMulti;
        currentScene.show();
    }

    public static Scene getCurrentScene() {
        return currentScene;
    }

    public static void update(float tpf) {
        if (currentScene != null && currentScene.isActive()) currentScene.update(tpf);
        if (trigger) {
            SceneController.setScene(new GameMulti(map, player));
            trigger = false;
        }
    }
}
