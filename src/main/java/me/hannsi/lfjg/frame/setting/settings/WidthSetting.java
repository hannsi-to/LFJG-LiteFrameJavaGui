package me.hannsi.lfjg.frame.setting.settings;

import me.hannsi.lfjg.frame.Frame;
import me.hannsi.lfjg.frame.setting.system.FrameSettingBase;
import me.hannsi.lfjg.frame.setting.system.ReflectionsLevel;
import me.hannsi.lfjg.util.DisplayUtil;
import org.lwjgl.glfw.GLFW;

@ReflectionsLevel(level = 2)
public class WidthSetting extends FrameSettingBase<Integer> {
    public WidthSetting(Frame frame) {
        super(frame, "Width", 2, DisplayUtil.getScreenSize().width);
    }

    @Override
    public void updateSetting() {
        if (getFrame().getWindowID() != -1L) {
            GLFW.glfwSetWindowSize(getFrame().getWindowID(), getValue(), getFrame().getFrameSettingValue(HeightSetting.class));
        }

        super.updateSetting();
    }
}
