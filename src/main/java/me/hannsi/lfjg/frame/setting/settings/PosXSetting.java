package me.hannsi.lfjg.frame.setting.settings;

import me.hannsi.lfjg.frame.Frame;
import me.hannsi.lfjg.frame.setting.system.FrameSettingBase;
import me.hannsi.lfjg.frame.setting.system.ReflectionsLevel;
import me.hannsi.lfjg.util.type.types.MonitorType;
import org.lwjgl.glfw.GLFW;

@ReflectionsLevel(level = 16)
public class PosXSetting extends FrameSettingBase<Integer> {
    public PosXSetting(Frame frame) {
        super(frame, "PosXSetting", 16, 0);
    }

    @Override
    public void updateSetting() {
        if (getFrame().getWindowID() != -1L) {
            if (getFrame().getFrameSettingBase(MonitorSetting.class).getValue() == MonitorType.Borderless) {
                GLFW.glfwSetWindowPos(getFrame().getWindowID(), 0, getFrame().getFrameSettingValue(PosYSetting.class));
            } else {
                GLFW.glfwSetWindowPos(getFrame().getWindowID(), getValue(), getFrame().getFrameSettingValue(PosYSetting.class));
            }
        }

        super.updateSetting();
    }
}
