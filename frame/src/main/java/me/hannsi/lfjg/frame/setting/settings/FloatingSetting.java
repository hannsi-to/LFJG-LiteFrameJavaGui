package me.hannsi.lfjg.frame.setting.settings;

import me.hannsi.lfjg.frame.Frame;
import me.hannsi.lfjg.frame.setting.system.FrameSettingBase;
import me.hannsi.lfjg.frame.setting.system.ReflectionsLevel;
import me.hannsi.lfjg.frame.system.GLFWUtil;
import org.lwjgl.glfw.GLFW;

/**
 * Represents a setting for floating windows in a frame.
 */
@ReflectionsLevel(level = 14)
public class FloatingSetting extends FrameSettingBase<Boolean> {

    /**
     * Constructs a new FloatingSetting with the specified frame.
     *
     * @param frame the frame to associate with this setting
     */
    public FloatingSetting(Frame frame) {
        super(frame, "FloatingSetting", 14, false, true);
    }

    /**
     * Updates the floating setting.
     * Sets the GLFW window hint for floating based on the current value.
     */
    @Override
    public void updateSetting() {
        GLFWUtil.windowHintBoolean(GLFW.GLFW_FLOATING, getValue());

        super.updateSetting();
    }
}