package me.hannsi.lfjg.frame.setting.settings;

import me.hannsi.lfjg.frame.Frame;
import me.hannsi.lfjg.frame.setting.system.FrameSettingBase;
import me.hannsi.lfjg.frame.setting.system.ReflectionsLevel;
import me.hannsi.lfjg.utils.type.types.RenderingType;

/**
 * Represents a setting for the rendering type of a frame.
 */
@Deprecated
@ReflectionsLevel(level = 18)
public class RenderingTypeSetting extends FrameSettingBase<RenderingType> {

    /**
     * Constructs a new RenderingTypeSetting with the specified frame.
     *
     * @param frame the frame to associate with this setting
     */
    public RenderingTypeSetting(Frame frame) {
        super(frame, "RenderingTypeSetting", 18, RenderingType.OPEN_GL);
    }

    /**
     * Updates the rendering type setting.
     * This method is currently empty and calls the superclass implementation.
     */
    @Override
    public void updateSetting() {
        super.updateSetting();
    }
}