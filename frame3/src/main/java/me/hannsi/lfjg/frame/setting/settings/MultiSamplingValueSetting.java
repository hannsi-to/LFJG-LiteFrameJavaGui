package me.hannsi.lfjg.frame.setting.settings;

import me.hannsi.lfjg.frame.Frame;
import me.hannsi.lfjg.frame.setting.system.FrameSettingBase;
import me.hannsi.lfjg.frame.setting.system.ReflectionsLevel;
import org.lwjgl.glfw.GLFW;

/**
 * Represents a setting for the multi-sampling value of a frame.
 */
@ReflectionsLevel(level = 10)
public class MultiSamplingValueSetting extends FrameSettingBase<Integer> {

    /**
     * Constructs a new MultiSamplingValueSetting with the specified frame.
     *
     * @param frame the frame to associate with this setting
     */
    public MultiSamplingValueSetting(Frame frame) {
        super(frame, "MultiSamplingValueSetting", 10, 64, true);
    }

    /**
     * Updates the multi-sampling value setting.
     * Sets the GLFW window hint for samples based on the current value.
     */
    @Override
    public void updateSetting() {
        GLFW.glfwWindowHint(GLFW.GLFW_SAMPLES, getValue());
    }
}