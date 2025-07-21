package me.hannsi.lfjg.frame.setting.settings;

import me.hannsi.lfjg.frame.Frame;
import me.hannsi.lfjg.frame.setting.system.FrameSettingBase;
import me.hannsi.lfjg.frame.setting.system.ReflectionsLevel;

/**
 * Represents a setting for anti-aliasing in a frame.
 */
@ReflectionsLevel(level = 9)
public class AntiAliasingSetting extends FrameSettingBase<AntiAliasingType> {

    /**
     * Constructs a new AntiAliasingSetting with the specified frame.
     *
     * @param frame the frame to associate with this setting
     */
    public AntiAliasingSetting(Frame frame) {
        super(frame, "AntiAliasingSetting", 9, AntiAliasingType.MSAA);
    }

    /**
     * Updates the anti-aliasing setting.
     */
    @Override
    public void updateSetting() {
        super.updateSetting();
    }
}