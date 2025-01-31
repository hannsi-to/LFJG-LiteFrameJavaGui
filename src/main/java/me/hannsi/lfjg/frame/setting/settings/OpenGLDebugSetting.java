package me.hannsi.lfjg.frame.setting.settings;

import me.hannsi.lfjg.frame.Frame;
import me.hannsi.lfjg.frame.setting.system.FrameSettingBase;

public class OpenGLDebugSetting extends FrameSettingBase<Boolean> {
    public OpenGLDebugSetting(Frame frame) {
        super(frame, "OpenGLDebugSetting", 20, true);
    }
}
