package me.hannsi.lfjg.frame.setting.settings;

import me.hannsi.lfjg.frame.Frame;
import me.hannsi.lfjg.frame.setting.system.FrameSettingBase;
import me.hannsi.lfjg.frame.setting.system.ReflectionsLevel;
import me.hannsi.lfjg.frame.system.GLFWUtil;
import org.lwjgl.glfw.GLFW;

/**
 * Represents a setting for window decoration in a frame.
 */
@ReflectionsLevel(level = 12)
public class DecoratedSetting extends FrameSettingBase<Boolean> {

    /**
     * Constructs a new DecoratedSetting with the specified frame.
     *
     * @param frame the frame to associate with this setting
     */
    public DecoratedSetting(Frame frame) {
        super(frame, "DecoratedSetting", 12, true, true);
    }

    /**
     * Updates the decorated setting.
     * Sets the GLFW window hint for decoration based on the current value.
     */
    @Override
    public void updateSetting() {
        GLFWUtil.windowHintBoolean(GLFW.GLFW_DECORATED, getValue());

        super.updateSetting();
    }
}