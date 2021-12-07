package ui.gui.game;

import com.jme3.math.Vector2f;
import cores.Config;
import cores.Debugger;
import cores.Main;
import input.PlayerInput;
import scenes.Game;
import scenes.Menu;
import scenes.SceneController;
import ui.gui.ButtonGui;
import ui.gui.ImageGui;
import ui.gui.buffs.BuffListGui;

public class AnnouncementGui {
    private final ImageGui background;
    private final ImageGui image;
    private final ButtonGui btn1;
    private final ButtonGui btn2;

    public AnnouncementGui(boolean win) {
        SceneController.getCurrentScene().setActive(false); //pause game
        PlayerInput.setActive(false); //pause input
        background = new ImageGui(new Vector2f(Config.WIDTH / 2f - 550, Config.HEIGHT / 2f - 300), new Vector2f(1100, 600), "Textures/Menu/announcement_background.png");
        if (win) {
            int nextLevel = ((Game) SceneController.getCurrentScene()).getLevel() + 1;
            image = new ImageGui(new Vector2f(Config.WIDTH / 2f - 500, Config.HEIGHT / 2f - 160), new Vector2f(1000, 414), "Textures/Menu/win.png");
            btn1 = new ButtonGui("Next to level " + nextLevel, new Vector2f(Config.WIDTH / 2f - 210, Config.HEIGHT / 2f - 200), new Vector2f(200, 50)) {
                @Override
                public void onClick() {
                    Debugger.log(Debugger.EVENT, "Win next accepted. Next level = " + nextLevel);
                    SceneController.setScene(new Game(nextLevel));
                    this.remove();

                }
            };
        } else {
            image = new ImageGui(new Vector2f(Config.WIDTH / 2f - 382, Config.HEIGHT / 2f - 120), new Vector2f(764, 373), "Textures/Menu/lose.png");
            btn1 = new ButtonGui("New game", new Vector2f(Config.WIDTH / 2f - 210, Config.HEIGHT / 2f - 200), new Vector2f(200, 50)) {
                @Override
                public void onClick() {
                    Debugger.log(Debugger.EVENT, "Lose next accepted. Next level = 1");
                    SceneController.setScene(new Game(1));
                    this.remove();
                }
            };
        }
        btn2 = new ButtonGui("Back to main menu", new Vector2f(Config.WIDTH / 2f + 10, Config.HEIGHT / 2f - 200), new Vector2f(200, 50)) {
            @Override
            public void onClick() {
                Debugger.log(Debugger.EVENT, "Back to main menu accepted");
                SceneController.setScene(new Menu());
                this.remove();
            }
        };
        InfoGuiList.remove();
        BuffListGui.remove();
        this.show();
    }

    public void show() {
        background.show();
        image.show();
        btn1.show();
        btn2.show();
    }

    public void remove() {
        background.remove();
        image.remove();
        btn1.remove();
        btn2.remove();
    }
}
