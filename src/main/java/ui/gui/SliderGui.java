package ui.gui;

import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector2f;

import java.util.ArrayList;
import java.util.List;

public class SliderGui extends ItemGui {
    private final ImageButtonGui sliderLeft;
    private final TextGui sliderText;
    private final ImageButtonGui sliderRight;
    private final ArrayList<String> sliderOptions;
    private int currentOption = 0;

    public SliderGui(Vector2f position, List<String> options, String curOption) {
        sliderOptions = new ArrayList<>(options);
        for (int i = 0; i < options.size(); i++) {
            if (options.get(i).equals(curOption)) {
                currentOption = i;
                break;
            }
        }
        sliderLeft = new ImageButtonGui(new Vector2f(), new Vector2f(16, 16), "Textures/Setting/slider_left.png") {
            @Override
            public void onClick() {
                if (currentOption > 0) {
                    currentOption--;
                    sliderText.setText(sliderOptions.get(currentOption));
                    LocationGui.centerObject(sliderText, SliderGui.this);
                    sliderText.setPosition(sliderText.getPosition().x, sliderText.getPosition().y + 15);
                }
            }
        };
        sliderText = new TextGui(sliderOptions.get(currentOption), ColorRGBA.White, new Vector2f());
        sliderRight = new ImageButtonGui(new Vector2f(), new Vector2f(16, 16), "Textures/Setting/slider_right.png") {
            @Override
            public void onClick() {
                if (currentOption < sliderOptions.size() - 1) {
                    currentOption++;
                    sliderText.setText(sliderOptions.get(currentOption));
                    LocationGui.centerObject(sliderText, SliderGui.this);
                    sliderText.setPosition(sliderText.getPosition().x, sliderText.getPosition().y + 15);
                }
            }
        };
        this.setSizeX(216);
        this.setSizeY(16);
        this.setPosition(position.x, position.y);
    }

    public void show() {
        sliderText.show();
        sliderLeft.show();
        sliderRight.show();
    }

    public void remove() {
        sliderText.remove();
        sliderLeft.remove();
        sliderRight.remove();
    }

    @Override
    public void setPosition(float posX, float posY) {
        super.setPosition(posX, posY);
        sliderLeft.setPosition(posX, posY);
        LocationGui.centerObject(sliderText, this);
        sliderText.setPosition(sliderText.getPosition().x, sliderText.getPosition().y + 15);
        sliderRight.setPosition(posX + 200, posY);
    }

    public String getCurrentOption() {
        return sliderOptions.get(currentOption);
    }

    public void setCurrentOption(int option) {
        currentOption = option;
        sliderText.setText(sliderOptions.get(currentOption));
        LocationGui.centerObject(sliderText, this);
        sliderText.setPosition(sliderText.getPosition().x, sliderText.getPosition().y + 15);
    }
}
