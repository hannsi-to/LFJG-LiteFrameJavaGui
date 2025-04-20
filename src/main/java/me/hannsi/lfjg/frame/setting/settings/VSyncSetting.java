package me.hannsi.lfjg.frame.setting.settings;

import me.hannsi.lfjg.frame.frame.Frame;
import me.hannsi.lfjg.frame.setting.system.FrameSettingBase;
import me.hannsi.lfjg.frame.setting.system.ReflectionsLevel;
import me.hannsi.lfjg.utils.type.types.VSyncType;
import org.lwjgl.glfw.GLFW;

/**
 * Represents a setting for enabling or disabling VSync for a frame.
 */
@ReflectionsLevel(level = 7)
public class VSyncSetting extends FrameSettingBase<VSyncType> {

    /**
     * Constructs a new VSyncSetting with the specified frame.
     *
     * @param frame the frame to associate with this setting
     */
    public VSyncSetting(Frame frame) {
        super(frame, "VSyncSetting", 7, VSyncType.V_SYNC_OFF);
    }

    /**
     * Updates the VSync setting.
     * Sets the GLFW swap interval based on the current VSync value.
     */
    @Override
    public void updateSetting() {
        if (getFrame().getWindowID() != -1L) {
            GLFW.glfwSwapInterval(getValue().getId());
        }
    }
}