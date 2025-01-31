package me.hannsi.lfjg.frame.setting.settings;

import me.hannsi.lfjg.frame.Frame;
import me.hannsi.lfjg.frame.setting.system.FrameSettingBase;

public class OpenALDebugSetting extends FrameSettingBase<Boolean> {
    public OpenALDebugSetting(Frame frame) {
        super(frame, "OpenALDebugSetting", 21, true);
    }
}
