package ui.gui;

import cores.Main;
import scenes.Game;
import scenes.Menu;
import scenes.SceneController;

public class AnnouncementGui {
    private final ImageGui background;
    private final ImageGui image;
    private final ButtonGui btn1;
    private final ButtonGui btn2;

    public AnnouncementGui(boolean isWin, int x) {
        SceneController.getCurrentScene().setActive(false); //pause game
        background = new ImageGui(1100, 600, Main.WIDTH / 2f - 550, Main.HEIGHT / 2f - 300, "Textures/Menu/announcement_background.png");
        if (isWin) {
            image = new ImageGui(1000, 414, Main.WIDTH / 2f - 500, Main.HEIGHT / 2f - 160, "Textures/Menu/win.png");
            btn1 = new ButtonGui(Main.WIDTH / 2f - 210, Main.HEIGHT / 2f - 200, "Next to level " + x, 200, 50) {
                @Override
                public void onClick() {
                    AnnouncementGui.this.hide();
                    SceneController.setScene(new Game());
                }
            };
        } else {
            image = new ImageGui(764, 373, Main.WIDTH / 2f - 382, Main.HEIGHT / 2f - 120, "Textures/Menu/lose.png");
            btn1 = new ButtonGui(Main.WIDTH / 2f - 210, Main.HEIGHT / 2f - 200, "New game", 200, 50) {
                @Override
                public void onClick() {
                    AnnouncementGui.this.hide();
                    SceneController.setScene(new Game());
                }
            };
        }
        btn2 = new ButtonGui(Main.WIDTH / 2f + 10, Main.HEIGHT / 2f - 200, "Back to main menu", 200, 50) {
            @Override
            public void onClick() {
                AnnouncementGui.this.hide();
                SceneController.setScene(new Menu());
            }
        };
        show();
    }

    public void show() {
        background.show();
        image.show();
        btn1.show();
        btn2.show();
    }

    public void hide() {
        background.hide();
        image.hide();
        btn1.hide();
        btn2.hide();
    }
}
