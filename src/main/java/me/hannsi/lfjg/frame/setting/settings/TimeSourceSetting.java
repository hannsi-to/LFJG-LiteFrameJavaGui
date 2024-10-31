package me.hannsi.lfjg.frame.setting.settings;

import me.hannsi.lfjg.frame.Frame;
import me.hannsi.lfjg.frame.setting.system.FrameSettingBase;
import me.hannsi.lfjg.frame.setting.system.ReflectionsLevel;
import me.hannsi.lfjg.utils.type.types.TimeSourceType;

@ReflectionsLevel(level = 11)
public class TimeSourceSetting extends FrameSettingBase<TimeSourceType> {
    public TimeSourceSetting(Frame frame) {
        super(frame, "TimeSource", 11, TimeSourceType.SystemTime);
    }

    @Override
    public void updateSetting() {
        super.updateSetting();
    }
}
