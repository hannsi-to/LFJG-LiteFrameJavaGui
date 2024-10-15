package me.hannsi.lfjg.frame.setting.settings;

import me.hannsi.lfjg.frame.Frame;
import me.hannsi.lfjg.frame.setting.system.FrameSettingBase;
import me.hannsi.lfjg.frame.setting.system.ReflectionsLevel;
import me.hannsi.lfjg.util.type.types.MonitorType;
import org.lwjgl.glfw.GLFW;

@ReflectionsLevel(level = 5)
public class MonitorSetting extends FrameSettingBase<MonitorType> {
    public MonitorSetting(Frame frame) {
        super(frame, "MonitorSetting", 5, MonitorType.Window, true);
    }

    @Override
    public void updateSetting() {
        if (getValue() == MonitorType.FullScreen) {
            GLFW.glfwWindowHint(GLFW.GLFW_AUTO_ICONIFY, GLFW.GLFW_TRUE);
        }
        if (getValue() == MonitorType.Borderless && getValue() == MonitorType.Window) {
            GLFW.glfwWindowHint(GLFW.GLFW_AUTO_ICONIFY, GLFW.GLFW_FALSE);
        }
    }
}