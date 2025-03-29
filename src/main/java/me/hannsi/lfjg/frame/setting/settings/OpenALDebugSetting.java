package me.hannsi.lfjg.frame.setting.settings;

import me.hannsi.lfjg.frame.frame.Frame;
import me.hannsi.lfjg.frame.setting.system.FrameSettingBase;

/**
 * Represents a setting for enabling or disabling OpenAL debug mode in a frame.
 */
public class OpenALDebugSetting extends FrameSettingBase<Boolean> {

    /**
     * Constructs a new OpenALDebugSetting with the specified frame.
     *
     * @param frame the frame to associate with this setting
     */
    public OpenALDebugSetting(Frame frame) {
        super(frame, "OpenALDebugSetting", 21, true);
    }
}