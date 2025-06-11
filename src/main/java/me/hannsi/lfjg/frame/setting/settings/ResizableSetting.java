package me.hannsi.lfjg.frame.setting.settings;

import me.hannsi.lfjg.frame.Frame;
import me.hannsi.lfjg.frame.setting.system.FrameSettingBase;
import me.hannsi.lfjg.frame.setting.system.ReflectionsLevel;
import me.hannsi.lfjg.utils.graphics.GLFWUtil;

/**
 * Represents a setting for enabling or disabling the resizable property of a frame.
 */
@ReflectionsLevel(level = 1)
public class ResizableSetting extends FrameSettingBase<Boolean> {

    /**
     * Constructs a new ResizableSetting with the specified frame.
     *
     * @param frame the frame to associate with this setting
     */
    public ResizableSetting(Frame frame) {
        super(frame, "ResizableSetting", 1, true, true);
    }

    /**
     * Updates the resizable setting.
     * Sets the GLFW window hint for resizable based on the current value.
     */
    @Override
    public void updateSetting() {
        GLFWUtil.setWindowResizable(getValue());

        super.updateSetting();
    }
}