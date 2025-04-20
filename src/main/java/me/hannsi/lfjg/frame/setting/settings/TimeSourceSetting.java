package me.hannsi.lfjg.frame.setting.settings;

import me.hannsi.lfjg.frame.frame.Frame;
import me.hannsi.lfjg.frame.setting.system.FrameSettingBase;
import me.hannsi.lfjg.frame.setting.system.ReflectionsLevel;
import me.hannsi.lfjg.utils.type.types.TimeSourceType;

/**
 * Represents a setting for the time source of a frame.
 */
@ReflectionsLevel(level = 11)
public class TimeSourceSetting extends FrameSettingBase<TimeSourceType> {

    /**
     * Constructs a new TimeSourceSetting with the specified frame.
     *
     * @param frame the frame to associate with this setting
     */
    public TimeSourceSetting(Frame frame) {
        super(frame, "TimeSource", 11, TimeSourceType.SYSTEM_TIME);
    }

    /**
     * Updates the time source setting.
     * This method is currently empty and calls the superclass implementation.
     */
    @Override
    public void updateSetting() {
        super.updateSetting();
    }
}