package me.hannsi.lfjg.frame.setting.settings;

import me.hannsi.lfjg.frame.Frame;
import me.hannsi.lfjg.frame.setting.system.FrameSettingBase;
import me.hannsi.lfjg.frame.setting.system.ReflectionsLevel;
import me.hannsi.lfjg.frame.system.GLFWUtil;

import static org.lwjgl.glfw.GLFW.GLFW_FOCUSED;

/**
 * Represents a setting for focused windows in a frame.
 */
@ReflectionsLevel(level = 13)
public class FocusedSetting extends FrameSettingBase<Boolean> {

    /**
     * Constructs a new FocusedSetting with the specified frame.
     *
     * @param frame the frame to associate with this setting
     */
    public FocusedSetting(Frame frame) {
        super(frame, "FocusedSetting", 13, true, true);
    }

    /**
     * Updates the focused setting.
     * Sets the GLFW window hint for focused based on the current value.
     */
    @Override
    public void updateSetting() {
        GLFWUtil.windowHintBoolean(GLFW_FOCUSED, getValue());

        super.updateSetting();
    }
}