package me.hannsi.lfjg.frame.setting.settings;

import me.hannsi.lfjg.frame.Frame;
import me.hannsi.lfjg.frame.setting.system.FrameSettingBase;
import me.hannsi.lfjg.frame.setting.system.ReflectionsLevel;
import me.hannsi.lfjg.util.GLFWUtil;
import org.lwjgl.glfw.GLFW;

@ReflectionsLevel(level = 15)
public class TransparentFramebufferSetting extends FrameSettingBase<Boolean> {
    public TransparentFramebufferSetting(Frame frame) {
        super(frame, "TransparentFramebufferSetting",15,false,true);
    }

    @Override
    public void updateSetting() {
        GLFWUtil.windowHintBoolean(GLFW.GLFW_TRANSPARENT_FRAMEBUFFER,getValue());

        super.updateSetting();
    }
}
