package me.hannsi.lfjg.frame.setting.settings;

import me.hannsi.lfjg.frame.Frame;
import me.hannsi.lfjg.frame.setting.system.FrameSettingBase;
import me.hannsi.lfjg.frame.setting.system.ReflectionsLevel;
import org.lwjgl.glfw.GLFW;

@ReflectionsLevel(level = 0)
public class VisibleSetting extends FrameSettingBase<Boolean> {
    public VisibleSetting(Frame frame) {
        super(frame, "Visible", 0, false, true);
    }

    @Override
    public void updateSetting() {
        GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, getValue() ? GLFW.GLFW_TRUE : GLFW.GLFW_FALSE);
        super.updateSetting();
    }
}
