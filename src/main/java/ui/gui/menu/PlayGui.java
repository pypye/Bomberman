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
    private static ImageGui playNormal, playAIMode, playMultiplayerCreate, playMultiplayerJoin;
    private static TextGui playNormalText, playAIModeText, playMultiplayerCreateText, playMultiplayerJoinText;
    private static TextGui playNormalDescription, playAIModeDescription, playMultiplayerCreateDescription, playMultiplayerJoinDescription;
    private static ButtonGui playNormalBtn, playAIModeBtn, playMultiplayerCreateBtn, playMultiplayerJoinBtn;
    private static boolean enabled;

    public static void initialize() {
        filter = new ImageGui(new Vector2f(Config.WIDTH, Config.HEIGHT), "Textures/Menu/announcement_background.png");
        background = new ImageGui(new Vector2f(100, 100), new Vector2f((Config.WIDTH - 200), (Config.HEIGHT - 200)), "Textures/Settings/mainwindow.png");
        close = new ImageButtonGui(new Vector2f(), new Vector2f(32, 32), "Textures/Settings/X.png") {
            @Override
            public void onClick() {
                PlayGui.remove();
            }
        };
        createNormal();
        createAIMode();
        createMultiplayerCreate();
        createMultiplayerJoin();

        returnBtn = new ButtonGui("Return to main menu", new Vector2f(), new Vector2f(200, 50)) {
            @Override
            public void onClick() {
                PlayGui.remove();
            }
        };
        LocationGui.anchorBottomRightObject(returnBtn, background, 32, 16);
        show();
        enabled = true;
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

        playMultiplayerCreate.show();
        playMultiplayerCreateText.show();
        playMultiplayerCreateDescription.show();
        playMultiplayerCreateBtn.show();

        playMultiplayerJoin.show();
        playMultiplayerJoinText.show();
        playMultiplayerJoinDescription.show();
        playMultiplayerJoinBtn.show();

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

        playMultiplayerCreate.remove();
        playMultiplayerCreateText.remove();
        playMultiplayerCreateDescription.remove();
        playMultiplayerCreateBtn.remove();

        playMultiplayerJoin.remove();
        playMultiplayerJoinText.remove();
        playMultiplayerJoinDescription.remove();
        playMultiplayerJoinBtn.remove();

        returnBtn.remove();
        enabled = false;
        SceneController.getCurrentScene().setActive(true);
    }

    public static boolean isEnabled() {
        return enabled;
    }

    private static void createNormal() {
        playText = new TextGui("Play", ColorRGBA.White, 32);
        LocationGui.anchorTopRightObject(close, background, 16, 16);
        LocationGui.anchorTopLeftObject(playText, background, 64, 0);
        playNormal = new ImageGui(new Vector2f(), new Vector2f(background.getSize().x - 128, 72), "Textures/Settings/part.png");
        LocationGui.anchorTopLeftObject(playNormal, background, 64, 80);
        playNormalText = new TextGui("Normal mode", ColorRGBA.White, 32);
        LocationGui.anchorTopLeftObject(playNormalText, playNormal, 16, -32);
        playNormalDescription = new TextGui("Eliminate all the enemy to complete the level", ColorRGBA.White);
        LocationGui.anchorTopLeftObject(playNormalDescription, playNormal, 16, 32);
        playNormalBtn = new ButtonGui("Play", new Vector2f(), new Vector2f(100, 50)) {
            @Override
            public void onClick() {
                SceneController.setScene(new Game(1));
            }
        };
        LocationGui.anchorTopRightObject(playNormalBtn, playNormal, 32, 0);
        LocationGui.centerYObject(playNormalBtn, playNormal, playNormalBtn.getPosition().x);
    }

    private static void createAIMode() {
        playAIMode = new ImageGui(new Vector2f(), new Vector2f(background.getSize().x - 128, 72), "Textures/Settings/part.png");
        LocationGui.anchorTopLeftObject(playAIMode, background, 64, 172);
        playAIModeText = new TextGui("AI mode", ColorRGBA.White, 32);
        LocationGui.anchorTopLeftObject(playAIModeText, playAIMode, 16, -32);
        playAIModeDescription = new TextGui("Watch AI bot play the game", ColorRGBA.White);
        LocationGui.anchorTopLeftObject(playAIModeDescription, playAIMode, 16, 32);
        playAIModeBtn = new ButtonGui("Watch", new Vector2f(), new Vector2f(100, 50)) {
            @Override
            public void onClick() {
                SceneController.setScene(new GameAI(1));
            }
        };
        LocationGui.anchorTopRightObject(playAIModeBtn, playAIMode, 32, 0);
        LocationGui.centerYObject(playAIModeBtn, playAIMode, playAIModeBtn.getPosition().x);
    }

    private static void createMultiplayerCreate(){
        playMultiplayerCreate = new ImageGui(new Vector2f(), new Vector2f(background.getSize().x - 128, 72), "Textures/Settings/part.png");
        LocationGui.anchorTopLeftObject(playMultiplayerCreate, background, 64, 272);
        playMultiplayerCreateText = new TextGui("Create room", ColorRGBA.White, 32);
        LocationGui.anchorTopLeftObject(playMultiplayerCreateText, playMultiplayerCreate, 16, -32);
        playMultiplayerCreateDescription = new TextGui("Create a new multiplayer game", ColorRGBA.White);
        LocationGui.anchorTopLeftObject(playMultiplayerCreateDescription, playMultiplayerCreate, 16, 32);
        playMultiplayerCreateBtn = new ButtonGui("Create", new Vector2f(), new Vector2f(100, 50)) {
            @Override
            public void onClick() {

            }
        };
        LocationGui.anchorTopRightObject(playMultiplayerCreateBtn, playMultiplayerCreate, 32, 0);
        LocationGui.centerYObject(playMultiplayerCreateBtn, playMultiplayerCreate, playMultiplayerCreateBtn.getPosition().x);
    }

    private static void createMultiplayerJoin(){
        playMultiplayerJoin = new ImageGui(new Vector2f(), new Vector2f(background.getSize().x - 128, 72), "Textures/Settings/part.png");
        LocationGui.anchorTopLeftObject(playMultiplayerJoin, background, 64, 372);
        playMultiplayerJoinText = new TextGui("Join room", ColorRGBA.White, 32);
        LocationGui.anchorTopLeftObject(playMultiplayerJoinText, playMultiplayerJoin, 16, -32);
        playMultiplayerJoinDescription = new TextGui("Join a multiplayer game", ColorRGBA.White);
        LocationGui.anchorTopLeftObject(playMultiplayerJoinDescription, playMultiplayerJoin, 16, 32);
        playMultiplayerJoinBtn = new ButtonGui("Join", new Vector2f(), new Vector2f(100, 50)) {
            @Override
            public void onClick() {

            }
        };
        LocationGui.anchorTopRightObject(playMultiplayerJoinBtn, playMultiplayerJoin, 32, 0);
        LocationGui.centerYObject(playMultiplayerJoinBtn, playMultiplayerJoin, playMultiplayerJoinBtn.getPosition().x);
    }
}
