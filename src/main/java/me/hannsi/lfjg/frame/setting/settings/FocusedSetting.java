package me.hannsi.lfjg.frame.setting.settings;

import me.hannsi.lfjg.frame.Frame;
import me.hannsi.lfjg.frame.setting.system.FrameSettingBase;
import me.hannsi.lfjg.frame.setting.system.ReflectionsLevel;
import me.hannsi.lfjg.util.GLFWUtil;
import org.lwjgl.glfw.GLFW;

@ReflectionsLevel(level = 13)
public class FocusedSetting extends FrameSettingBase<Boolean> {
    public FocusedSetting(Frame frame) {
        super(frame, "FocusedSetting",13,true,true);
    }

    @Override
    public void updateSetting() {
        GLFWUtil.windowHintBoolean(GLFW.GLFW_FOCUSED, getValue());

        super.updateSetting();
    }
}
