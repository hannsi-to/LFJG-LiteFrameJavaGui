package me.hannsi.lfjg.frame.setting.settings;

import me.hannsi.lfjg.frame.Frame;
import me.hannsi.lfjg.frame.setting.system.FrameSettingBase;
import me.hannsi.lfjg.frame.setting.system.ReflectionsLevel;

import static org.lwjgl.glfw.GLFW.GLFW_REFRESH_RATE;
import static org.lwjgl.glfw.GLFW.glfwWindowHint;

/**
 * Represents a setting for the refresh rate of a frame.
 */
@ReflectionsLevel(level = 6)
public class RefreshRateSetting extends FrameSettingBase<Integer> {

    /**
     * Constructs a new RefreshRateSetting with the specified frame.
     *
     * @param frame the frame to associate with this setting
     */
    public RefreshRateSetting(Frame frame) {
        super(frame, "RefreshRateSetting", 6, 60, true);
    }

    /**
     * Updates the refresh rate setting.
     * Sets the GLFW window hint for refresh rate based on the current value.
     */
    @Override
    public void updateSetting() {
        glfwWindowHint(GLFW_REFRESH_RATE, getValue());
    }
}