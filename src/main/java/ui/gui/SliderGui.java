package ui.gui;

import com.jme3.math.ColorRGBA;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SliderGui extends ItemGui {
    private final ImageButtonGui sliderLeft;
    private final TextGui sliderText;
    private final ImageButtonGui sliderRight;
    private final ArrayList<String> sliderOptions;
    private int currentOption = 0;

    public SliderGui(float posX, float posY, List<String> options, String curOption) {
        sliderOptions = new ArrayList<>(options);
        for (int i = 0; i < options.size(); i++) {
            if (options.get(i).equals(curOption)) {
                currentOption = i;
                break;
            }
        }
        sliderLeft = new ImageButtonGui(16, 16, -1, -1, "Textures/Setting/slider_left.png") {
            @Override
            public void onClick() {
                if (currentOption > 0) {
                    currentOption--;
                    sliderText.setText(sliderOptions.get(currentOption));
                    LocationGui.centerObject(sliderText, SliderGui.this);
                    sliderText.setPosition(sliderText.posX, sliderText.posY + 15);
                }
            }
        };
        sliderText = new TextGui(sliderOptions.get(currentOption), ColorRGBA.White, -1, -1);
        sliderRight = new ImageButtonGui(16, 16, -1, -1, "Textures/Setting/slider_right.png") {
            @Override
            public void onClick() {
                if (currentOption < sliderOptions.size() - 1) {
                    currentOption++;
                    sliderText.setText(sliderOptions.get(currentOption));
                    LocationGui.centerObject(sliderText, SliderGui.this);
                    sliderText.setPosition(sliderText.posX, sliderText.posY + 15);
                }
            }
        };
        this.setSizeX(216);
        this.setSizeY(16);
        this.setPosition(posX, posY);
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
        sliderText.setPosition(sliderText.posX, sliderText.posY + 15);
        sliderRight.setPosition(posX + 200, posY);
    }

    public String getCurrentOption() {
        return sliderOptions.get(currentOption);
    }

    public void setCurrentOption(int option) {
        currentOption = option;
        sliderText.setText(sliderOptions.get(currentOption));
        LocationGui.centerObject(sliderText, this);
        sliderText.setPosition(sliderText.posX, sliderText.posY + 15);
    }
}
