package me.hannsi.lfjg.frame.setting.settings;

import me.hannsi.lfjg.frame.Frame;
import me.hannsi.lfjg.frame.setting.system.FrameSettingBase;
import me.hannsi.lfjg.frame.setting.system.ReflectionsLevel;
import me.hannsi.lfjg.utils.type.types.SeverityType;

/**
 * Represents a setting for checking severities in a frame.
 */
@ReflectionsLevel(level = 19)
public class CheckSeveritiesSetting extends FrameSettingBase<SeverityType[]> {

    /**
     * Constructs a new CheckSeveritiesSetting with the specified frame.
     *
     * @param frame the frame to associate with this setting
     */
    public CheckSeveritiesSetting(Frame frame) {
        super(frame, "CheckSeveritiesSetting", 19, new SeverityType[]{SeverityType.Low, SeverityType.Medium, SeverityType.High});
    }

    /**
     * Updates the check severities setting.
     */
    @Override
    public void updateSetting() {
        super.updateSetting();
    }
}