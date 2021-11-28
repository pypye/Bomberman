package scenes;

import cores.Main;

public class SceneController {
    private static Scene currentScene;

    public static void setScene(Scene scene) {
        System.out.println("[Debug/Scene] Set scene from " + currentScene + " to " + scene);
        if (currentScene != null) currentScene.hide();
        Main.ROOT_NODE.detachAllChildren();
        Main.GUI_NODE.detachAllChildren();
        currentScene = scene;
        currentScene.show();
    }

    public static Scene getCurrentScene() {
        return currentScene;
    }

    public static void update(float tpf) {
        currentScene.update(tpf);
    }
}
