package scenes;

public class SceneController {
    private static Scene currentScene;

    public static void setScene(Scene scene) {
        System.out.println("[Debug/Scene] Set scene from "+ currentScene + " to " + scene);
        if (currentScene != null) currentScene.hide();
        currentScene = scene;
        currentScene.show();
    }

    public static void update(float tpf) {
        currentScene.update(tpf);
    }
}
