package me.hannsi.lfjg.frame.setting.settings;

import me.hannsi.lfjg.frame.frame.Frame;
import me.hannsi.lfjg.frame.setting.system.FrameSettingBase;

/**
 * Represents a setting for enabling or disabling GLFW debug mode in a frame.
 */
public class GLFWDebugSetting extends FrameSettingBase<Boolean> {

    /**
     * Constructs a new GLFWDebugSetting with the specified frame.
     *
     * @param frame the frame to associate with this setting
     */
    public GLFWDebugSetting(Frame frame) {
        super(frame, "GLFWDebugSetting", 22, true);
    }
}