package me.hannsi.lfjg.frame.setting.settings;

import me.hannsi.lfjg.frame.Frame;
import me.hannsi.lfjg.frame.setting.system.FrameSettingBase;
import me.hannsi.lfjg.frame.setting.system.ReflectionsLevel;
import me.hannsi.lfjg.util.DisplayUtil;
import org.lwjgl.glfw.GLFW;

@ReflectionsLevel(level = 3)
public class HeightSetting extends FrameSettingBase<Integer> {
    public HeightSetting(Frame frame) {
        super(frame, "HeightSetting", 3, DisplayUtil.getScreenSize().height);
    }

    @Override
    public void updateSetting() {
        if (getFrame().getWindowID() != -1L) {
            GLFW.glfwSetWindowSize(getFrame().getWindowID(), getFrame().getFrameSettingValue(WidthSetting.class), getValue());
        }

        super.updateSetting();
    }
}
