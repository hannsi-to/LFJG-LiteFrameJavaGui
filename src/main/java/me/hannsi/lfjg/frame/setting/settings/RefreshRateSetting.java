package me.hannsi.lfjg.frame.setting.settings;

import me.hannsi.lfjg.frame.Frame;
import me.hannsi.lfjg.frame.setting.system.FrameSettingBase;
import me.hannsi.lfjg.frame.setting.system.ReflectionsLevel;
import org.lwjgl.glfw.GLFW;

@ReflectionsLevel(level = 6)
public class RefreshRateSetting extends FrameSettingBase<Integer> {
    public RefreshRateSetting(Frame frame) {
        super(frame, "RefreshRateSetting", 6, 60, true);
    }

    @Override
    public void updateSetting() {
        GLFW.glfwWindowHint(GLFW.GLFW_REFRESH_RATE, getValue());
    }
}
