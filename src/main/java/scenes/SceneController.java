package scenes;

import cores.Main;

public class SceneController {
    private static Scene currentScene;

    public static void setScene(Scene scene) {
        if (currentScene != null) currentScene.remove();
        System.out.println("[Debug/Scene] Delete all data from " + currentScene);
        Main.ROOT_NODE.detachAllChildren();
        Main.GUI_NODE.detachAllChildren();
        System.out.println("[Debug/Scene] Delete all data from world");
        System.out.println("[Debug/Scene] Set scene from " + currentScene + " to " + scene);
        currentScene = scene;
        currentScene.show();
    }

    public static Scene getCurrentScene() {
        return currentScene;
    }

    public static void update(float tpf) {
        if (currentScene != null && currentScene.isActive()) currentScene.update(tpf);
    }
}
