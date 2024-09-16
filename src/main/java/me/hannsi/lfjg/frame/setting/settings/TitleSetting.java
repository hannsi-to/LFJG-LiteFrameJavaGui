package me.hannsi.lfjg.frame.setting.settings;

import me.hannsi.lfjg.frame.Frame;
import me.hannsi.lfjg.frame.setting.system.FrameSettingBase;
import me.hannsi.lfjg.frame.setting.system.ReflectionsLevel;
import org.lwjgl.glfw.GLFW;

@ReflectionsLevel(level = 4)
public class TitleSetting extends FrameSettingBase<String> {
    public TitleSetting(Frame frame) {
        super(frame, "TitleSetting", 4, "LFJG-Frame");
    }

    @Override
    public void updateSetting() {
        if (getFrame().getWindowID() != -1L) {
            GLFW.glfwSetWindowTitle(getFrame().getWindowID(), getValue());
        }
    }
}
