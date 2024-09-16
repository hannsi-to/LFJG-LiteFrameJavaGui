package me.hannsi.lfjg.frame.setting.settings;

import me.hannsi.lfjg.frame.Frame;
import me.hannsi.lfjg.frame.setting.system.FrameSettingBase;
import me.hannsi.lfjg.frame.setting.system.ReflectionsLevel;
import me.hannsi.lfjg.util.GLFWUtil;
import org.lwjgl.glfw.GLFW;

@ReflectionsLevel(level = 14)
public class FloatingSetting extends FrameSettingBase<Boolean> {
    public FloatingSetting(Frame frame) {
        super(frame, "FloatingSetting", 14, false, true);
    }

    @Override
    public void updateSetting() {
        GLFWUtil.windowHintBoolean(GLFW.GLFW_FLOATING, getValue());

        super.updateSetting();
    }
}
