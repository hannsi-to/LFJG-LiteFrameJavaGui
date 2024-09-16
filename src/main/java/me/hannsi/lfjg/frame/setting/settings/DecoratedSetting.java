package me.hannsi.lfjg.frame.setting.settings;

import me.hannsi.lfjg.frame.Frame;
import me.hannsi.lfjg.frame.setting.system.FrameSettingBase;
import me.hannsi.lfjg.frame.setting.system.ReflectionsLevel;
import me.hannsi.lfjg.util.GLFWUtil;
import org.lwjgl.glfw.GLFW;

@ReflectionsLevel(level = 12)
public class DecoratedSetting extends FrameSettingBase<Boolean> {
    public DecoratedSetting(Frame frame) {
        super(frame, "DecoratedSetting", 12, true,true);
    }

    @Override
    public void updateSetting() {
        GLFWUtil.windowHintBoolean(GLFW.GLFW_DECORATED, getValue());

        super.updateSetting();
    }
}
