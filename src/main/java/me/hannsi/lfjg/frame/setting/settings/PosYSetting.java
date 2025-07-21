package me.hannsi.lfjg.frame.setting.settings;

import me.hannsi.lfjg.core.utils.graphics.GLFWUtil;
import me.hannsi.lfjg.frame.Frame;
import me.hannsi.lfjg.frame.setting.system.FrameSettingBase;
import me.hannsi.lfjg.frame.setting.system.ReflectionsLevel;

/**
 * Represents a setting for the Y position of a frame.
 */
@ReflectionsLevel(level = 17)
public class PosYSetting extends FrameSettingBase<Integer> {

    /**
     * Constructs a new PosYSetting with the specified frame.
     *
     * @param frame the frame to associate with this setting
     */
    public PosYSetting(Frame frame) {
        super(frame, "PosYSetting", 17, 0);
    }

    /**
     * Updates the Y position setting.
     * Sets the GLFW window position based on the current value and the X position setting.
     * If the monitor type is borderless, the Y position is set to 0.
     */
    @Override
    public void updateSetting() {
        if (getFrame().getWindowID() != -1L) {
            if (getFrame().getFrameSettingBase(MonitorSetting.class).getValue() == MonitorType.BORDERLESS) {
                GLFWUtil.setWindowPosition(getFrame().getWindowID(), getFrame().getFrameSettingValue(PosXSetting.class), 0);
            } else {
                GLFWUtil.setWindowPosition(getFrame().getWindowID(), getFrame().getFrameSettingValue(PosXSetting.class), getValue());
            }
        }

        super.updateSetting();
    }
}