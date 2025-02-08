package me.hannsi.lfjg.frame.setting.settings;

import me.hannsi.lfjg.frame.Frame;
import me.hannsi.lfjg.frame.setting.system.FrameSettingBase;
import me.hannsi.lfjg.frame.setting.system.ReflectionsLevel;
import org.lwjgl.glfw.GLFW;

/**
 * Represents a setting for the visibility of a frame.
 */
@ReflectionsLevel(level = 0)
public class VisibleSetting extends FrameSettingBase<Boolean> {

    /**
     * Constructs a new VisibleSetting with the specified frame.
     *
     * @param frame the frame to associate with this setting
     */
    public VisibleSetting(Frame frame) {
        super(frame, "Visible", 0, false, true);
    }

    /**
     * Updates the visibility setting.
     * Sets the GLFW window hint for visibility based on the current value.
     */
    @Override
    public void updateSetting() {
        GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, getValue() ? GLFW.GLFW_TRUE : GLFW.GLFW_FALSE);
        super.updateSetting();
    }
}