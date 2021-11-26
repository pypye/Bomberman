package input;

import com.jme3.input.MouseInput;
import com.jme3.input.controls.MouseButtonTrigger;
import cores.Main;

public class Input {
    public static void init() {
        Main.INPUT_MANAGER.addMapping("LClick", new MouseButtonTrigger(MouseInput.BUTTON_LEFT));
        Main.INPUT_MANAGER.addMapping("RClick", new MouseButtonTrigger(MouseInput.BUTTON_RIGHT));
    }
}
