package input;

import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseButtonTrigger;
import cores.Main;
import ui.gui.menu.SettingGui;

public class SystemInput {
    private static boolean active = true;
    public static void initialize() {
        Main.INPUT_MANAGER.deleteMapping(Main.INPUT_MAPPING_EXIT);
        Main.INPUT_MANAGER.addMapping("Setting", new KeyTrigger(KeyInput.KEY_ESCAPE));
        Main.INPUT_MANAGER.addMapping("LClick", new MouseButtonTrigger(MouseInput.BUTTON_LEFT));
        Main.INPUT_MANAGER.addMapping("RClick", new MouseButtonTrigger(MouseInput.BUTTON_RIGHT));
        Main.INPUT_MANAGER.addListener(settingListener, "Setting");
    }
    private static final ActionListener settingListener = new ActionListener() {
        @Override
        public void onAction(String name, boolean keyPressed, float tpf) {
            if (active) {
                if (keyPressed & name.equals("Setting")) {
                    if (SettingGui.isEnabled()) {
                        SettingGui.remove();
                    } else {
                        SettingGui.initialize();
                    }
                }
            }

        }
    };
    public static boolean isActive() {
        return active;
    }

    public static void setActive(boolean active) {
        SystemInput.active = active;
    }
}
