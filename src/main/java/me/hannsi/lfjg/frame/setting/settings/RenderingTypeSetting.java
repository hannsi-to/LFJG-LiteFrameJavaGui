package me.hannsi.lfjg.frame.setting.settings;

import me.hannsi.lfjg.frame.Frame;
import me.hannsi.lfjg.frame.setting.system.FrameSettingBase;
import me.hannsi.lfjg.frame.setting.system.ReflectionsLevel;
import me.hannsi.lfjg.utils.type.types.RenderingType;

@ReflectionsLevel(level = 18)
public class RenderingTypeSetting extends FrameSettingBase<RenderingType> {
    public RenderingTypeSetting(Frame frame) {
        super(frame, "RenderingTypeSetting", 18, RenderingType.OpenGL);
    }

    @Override
    public void updateSetting() {

        super.updateSetting();
    }
}
