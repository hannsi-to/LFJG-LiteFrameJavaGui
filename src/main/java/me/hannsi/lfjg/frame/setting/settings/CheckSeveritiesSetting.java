package me.hannsi.lfjg.frame.setting.settings;

import me.hannsi.lfjg.frame.Frame;
import me.hannsi.lfjg.frame.setting.system.FrameSettingBase;
import me.hannsi.lfjg.frame.setting.system.ReflectionsLevel;
import me.hannsi.lfjg.utils.type.types.SeverityType;

@ReflectionsLevel(level = 19)
public class CheckSeveritiesSetting extends FrameSettingBase<SeverityType[]> {
    public CheckSeveritiesSetting(Frame frame) {
        super(frame, "CheckSeveritiesSetting", 19, new SeverityType[]{SeverityType.Low, SeverityType.Medium, SeverityType.High});
    }

    @Override
    public void updateSetting() {
        super.updateSetting();
    }
}
