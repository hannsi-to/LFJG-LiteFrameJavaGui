package me.hannsi.lfjg.frame.setting.settings;

import me.hannsi.lfjg.frame.Frame;
import me.hannsi.lfjg.frame.setting.system.FrameSettingBase;
import me.hannsi.lfjg.frame.setting.system.ReflectionsLevel;
import org.lwjgl.glfw.GLFW;

/**
 * Represents a setting for the title of a frame.
 */
@ReflectionsLevel(level = 4)
public class TitleSetting extends FrameSettingBase<String> {

    /**
     * Constructs a new TitleSetting with the specified frame.
     *
     * @param frame the frame to associate with this setting
     */
    public TitleSetting(Frame frame) {
        super(frame, "TitleSetting", 4, "LFJG-Frame");
    }

    /**
     * Updates the title setting.
     * Sets the GLFW window title based on the current value.
     */
    @Override
    public void updateSetting() {
        if (getFrame().getWindowID() != -1L) {
            GLFW.glfwSetWindowTitle(getFrame().getWindowID(), getValue());
        }
    }
}