package me.hannsi.lfjg.frame.setting.settings;

import me.hannsi.lfjg.frame.frame.Frame;
import me.hannsi.lfjg.frame.setting.system.FrameSettingBase;
import me.hannsi.lfjg.frame.setting.system.ReflectionsLevel;
import me.hannsi.lfjg.utils.type.types.MonitorType;
import org.lwjgl.glfw.GLFW;

/**
 * Represents a setting for the monitor type of a frame.
 */
@ReflectionsLevel(level = 5)
public class MonitorSetting extends FrameSettingBase<MonitorType> {

    /**
     * Constructs a new MonitorSetting with the specified frame.
     *
     * @param frame the frame to associate with this setting
     */
    public MonitorSetting(Frame frame) {
        super(frame, "MonitorSetting", 5, MonitorType.Window, true);
    }

    /**
     * Updates the monitor setting.
     * Sets the GLFW window hints based on the current monitor type value.
     */
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