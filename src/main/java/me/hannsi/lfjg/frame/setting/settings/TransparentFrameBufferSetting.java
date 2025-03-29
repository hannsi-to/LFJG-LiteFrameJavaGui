package me.hannsi.lfjg.frame.setting.settings;

import me.hannsi.lfjg.frame.frame.Frame;
import me.hannsi.lfjg.frame.setting.system.FrameSettingBase;
import me.hannsi.lfjg.frame.setting.system.ReflectionsLevel;
import me.hannsi.lfjg.utils.graphics.GLFWUtil;
import org.lwjgl.glfw.GLFW;

/**
 * Represents a setting for enabling or disabling the transparent framebuffer property of a frame.
 */
@ReflectionsLevel(level = 15)
public class TransparentFrameBufferSetting extends FrameSettingBase<Boolean> {

    /**
     * Constructs a new TransparentFramebufferSetting with the specified frame.
     *
     * @param frame the frame to associate with this setting
     */
    public TransparentFrameBufferSetting(Frame frame) {
        super(frame, "TransparentFrameBufferSetting", 15, false, true);
    }

    /**
     * Updates the transparent framebuffer setting.
     * Sets the GLFW window hint for transparent framebuffer based on the current value.
     */
    @Override
    public void updateSetting() {
        GLFWUtil.windowHintBoolean(GLFW.GLFW_TRANSPARENT_FRAMEBUFFER, getValue());

        super.updateSetting();
    }
}