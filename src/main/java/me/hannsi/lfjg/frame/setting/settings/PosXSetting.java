package me.hannsi.lfjg.frame.setting.settings;

import me.hannsi.lfjg.frame.Frame;
import me.hannsi.lfjg.frame.setting.system.FrameSettingBase;
import me.hannsi.lfjg.frame.setting.system.ReflectionsLevel;
import me.hannsi.lfjg.utils.graphics.GLFWUtil;
import me.hannsi.lfjg.utils.type.types.MonitorType;
import org.lwjgl.glfw.GLFW;

/**
 * Represents a setting for the X position of a frame.
 */
@ReflectionsLevel(level = 16)
public class PosXSetting extends FrameSettingBase<Integer> {

    /**
     * Constructs a new PosXSetting with the specified frame.
     *
     * @param frame the frame to associate with this setting
     */
    public PosXSetting(Frame frame) {
        super(frame, "PosXSetting", 16, 0);
    }

    /**
     * Updates the X position setting.
     * Sets the GLFW window position based on the current value and the Y position setting.
     * If the monitor type is borderless, the X position is set to 0.
     */
    @Override
    public void updateSetting() {
        if (getFrame().getWindowID() != -1L) {
            if (getFrame().getFrameSettingBase(MonitorSetting.class).getValue() == MonitorType.Borderless) {
                GLFWUtil.setWindowPosition(getFrame().getWindowID(), 0, getFrame().getFrameSettingValue(PosYSetting.class));
            } else {
                GLFWUtil.setWindowPosition(getFrame().getWindowID(), getValue(), getFrame().getFrameSettingValue(PosYSetting.class));
            }
        }

        super.updateSetting();
    }
}