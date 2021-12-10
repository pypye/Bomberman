package ui.gui.menu;

import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector2f;
import cores.Config;
import scenes.Game;
import scenes.GameAI;
import scenes.SceneController;
import ui.gui.*;

public class PlayGui {
    private static ImageGui filter, background;
    private static ImageButtonGui close;
    private static TextGui playText;
    private static ButtonGui returnBtn;
    private static ImageGui playNormal, playAIMode;
    private static TextGui playNormalText, playAIModeText;
    private static TextGui playNormalDescription, playAIModeDescription;
    private static ButtonGui playNormalBtn, playAIModeBtn;

    public static void initialize() {
        filter = new ImageGui(new Vector2f(Config.WIDTH, Config.HEIGHT), "Textures/Menu/announcement_background.png");
        background = new ImageGui(new Vector2f(100, 100), new Vector2f((Config.WIDTH - 200), (Config.HEIGHT - 200)), "Textures/Settings/mainwindow.png");
        close = new ImageButtonGui(new Vector2f(), new Vector2f(32, 32), "Textures/Settings/X.png") {
            @Override
            public void onClick() {
                PlayGui.remove();
            }
        };
        playText = new TextGui("Play", ColorRGBA.White, 32);
        LocationGui.anchorTopRightObject(close, background, 16, 16);
        LocationGui.anchorTopLeftObject(playText, background, 64, 0);
        createNormal();
        createAIMode();

        returnBtn = new ButtonGui("Return to main menu", new Vector2f(), new Vector2f(200, 50)) {
            @Override
            public void onClick() {
                PlayGui.remove();
            }
        };
        LocationGui.anchorBottomRightObject(returnBtn, background, 32, 32);
        show();
        SceneController.getCurrentScene().setActive(false);
    }

    private static void show() {
        filter.show();
        background.show();
        close.show();
        playText.show();
        playNormal.show();
        playNormalText.show();
        playNormalDescription.show();
        playNormalBtn.show();

        playAIMode.show();
        playAIModeText.show();
        playAIModeDescription.show();
        playAIModeBtn.show();

        returnBtn.show();
    }

    public static void remove() {
        filter.remove();
        background.remove();
        close.remove();
        playText.remove();
        playNormal.remove();
        playNormalText.remove();
        playNormalDescription.remove();
        playNormalBtn.remove();

        playAIMode.remove();
        playAIModeText.remove();
        playAIModeDescription.remove();
        playAIModeBtn.remove();

        returnBtn.remove();
        SceneController.getCurrentScene().setActive(true);
    }

    private static void createNormal() {
        playNormal = new ImageGui(new Vector2f(), new Vector2f(background.getSize().x - 128, 72), "Textures/Settings/part.png");
        LocationGui.anchorTopLeftObject(playNormal, background, 64, 80);
        playNormalText = new TextGui("Normal mode", ColorRGBA.White, 32);
        LocationGui.anchorTopLeftObject(playNormalText, playNormal, 16, -32);
        playNormalDescription = new TextGui("Eliminate all the enemy to complete the level", ColorRGBA.White);
        LocationGui.anchorTopLeftObject(playNormalDescription, playNormal, 16, 32);
        playNormalBtn = new ButtonGui("Play", new Vector2f(), new Vector2f(100, 50)) {
            @Override
            public void onClick() {
                SceneController.setScene(new Game(2));
            }
        };
        LocationGui.anchorTopRightObject(playNormalBtn, playNormal, 32, 0);
        LocationGui.centerYObject(playNormalBtn, playNormal, playNormalBtn.getPosition().x);
    }

    private static void createAIMode() {
        playAIMode = new ImageGui(new Vector2f(), new Vector2f(background.getSize().x - 128, 72), "Textures/Settings/part.png");
        LocationGui.anchorTopLeftObject(playAIMode, background, 64, 162);
        playAIModeText = new TextGui("AI mode", ColorRGBA.White, 32);
        LocationGui.anchorTopLeftObject(playAIModeText, playAIMode, 16, -32);
        playAIModeDescription = new TextGui("Watch AI bot play the game", ColorRGBA.White);
        LocationGui.anchorTopLeftObject(playAIModeDescription, playAIMode, 16, 32);
        playAIModeBtn = new ButtonGui("Watch", new Vector2f(), new Vector2f(100, 50)) {
            @Override
            public void onClick() {
                SceneController.setScene(new GameAI(2));
            }
        };
        LocationGui.anchorTopRightObject(playAIModeBtn, playAIMode, 32, 0);
        LocationGui.centerYObject(playAIModeBtn, playAIMode, playAIModeBtn.getPosition().x);
    }
}
