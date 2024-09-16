package me.hannsi.lfjg.frame.setting.settings;

import me.hannsi.lfjg.frame.Frame;
import me.hannsi.lfjg.frame.setting.system.FrameSettingBase;
import me.hannsi.lfjg.frame.setting.system.ReflectionsLevel;
import me.hannsi.lfjg.util.type.types.AntiAliasingType;

@ReflectionsLevel(level = 9)
public class AntiAliasingSetting extends FrameSettingBase<AntiAliasingType> {
    public AntiAliasingSetting(Frame frame) {
        super(frame, "AntiAliasingSetting", 9, AntiAliasingType.MSAA);
    }

    @Override
    public void updateSetting() {

        super.updateSetting();
    }
}
