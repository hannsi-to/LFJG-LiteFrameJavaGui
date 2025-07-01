package me.hannsi.lfjg.frame.setting.settings;

import me.hannsi.lfjg.frame.Frame;
import me.hannsi.lfjg.frame.setting.system.FrameSettingBase;
import me.hannsi.lfjg.frame.setting.system.ReflectionsLevel;
import me.hannsi.lfjg.utils.graphics.GLFWUtil;
import me.hannsi.lfjg.utils.reflection.location.Location;
import org.lwjgl.glfw.GLFW;

/**
 * Represents a setting for the icon of a frame.
 */
@ReflectionsLevel(level = 8)
public class IconSetting extends FrameSettingBase<Location> {

    /**
     * Constructs a new IconSetting with the specified frame.
     *
     * @param frame the frame to associate with this setting
     */
    public IconSetting(Frame frame) {
        super(frame, "IconSetting", 8, null);
    }

    /**
     * Updates the icon setting.
     * Sets the GLFW window icon based on the current value.
     * If the value is null, the icon is removed.
     */
    @Override
    public void updateSetting() {
        if (getFrame().getWindowID() != -1L) {
            if (getValue() == null) {
                GLFW.glfwSetWindowIcon(getFrame().getWindowID(), null);
            } else {
                GLFWUtil.setWindowIcon(getFrame().getWindowID(), getValue());
            }
        }
    }
}