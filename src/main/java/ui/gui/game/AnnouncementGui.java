package ui.gui.game;

import cores.Main;
import scenes.Game;
import scenes.Menu;
import scenes.SceneController;
import ui.gui.ButtonGui;
import ui.gui.ImageGui;
import ui.gui.buffs.BuffListGui;
import ui.gui.game.InfoGuiList;

public class AnnouncementGui {
    private final ImageGui background;
    private final ImageGui image;
    private final ButtonGui btn1;
    private final ButtonGui btn2;

    public AnnouncementGui(boolean win) {
        SceneController.getCurrentScene().setActive(false); //pause game
        background = new ImageGui(1100, 600, Main.WIDTH / 2f - 550, Main.HEIGHT / 2f - 300, "Textures/Menu/announcement_background.png");
        if (win) {
            int nextLevel = ((Game) SceneController.getCurrentScene()).getLevel() + 1;
            image = new ImageGui(1000, 414, Main.WIDTH / 2f - 500, Main.HEIGHT / 2f - 160, "Textures/Menu/win.png");
            btn1 = new ButtonGui(Main.WIDTH / 2f - 210, Main.HEIGHT / 2f - 200, "Next to level " + nextLevel, 200, 50) {
                @Override
                public void onClick() {
                    SceneController.setScene(new Game(nextLevel));
                }
            };
        } else {
            image = new ImageGui(764, 373, Main.WIDTH / 2f - 382, Main.HEIGHT / 2f - 120, "Textures/Menu/lose.png");
            btn1 = new ButtonGui(Main.WIDTH / 2f - 210, Main.HEIGHT / 2f - 200, "New game", 200, 50) {
                @Override
                public void onClick() {
                    SceneController.setScene(new Game(1));
                }
            };
        }
        btn2 = new ButtonGui(Main.WIDTH / 2f + 10, Main.HEIGHT / 2f - 200, "Back to main menu", 200, 50) {
            @Override
            public void onClick() {
                SceneController.setScene(new Menu());
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