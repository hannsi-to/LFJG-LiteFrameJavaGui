package me.hannsi.lfjg.frame.setting.settings;

import me.hannsi.lfjg.frame.frame.Frame;
import me.hannsi.lfjg.frame.setting.system.FrameSettingBase;

/**
 * Represents a setting for enabling or disabling OpenGL debug mode in a frame.
 */
public class OpenGLDebugSetting extends FrameSettingBase<Boolean> {

    /**
     * Constructs a new OpenGLDebugSetting with the specified frame.
     *
     * @param frame the frame to associate with this setting
     */
    public OpenGLDebugSetting(Frame frame) {
        super(frame, "OpenGLDebugSetting", 20, true);
    }
}