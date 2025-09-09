package me.hannsi.lfjg.frame.setting.settings;

import me.hannsi.lfjg.core.utils.graphics.DisplayUtil;
import me.hannsi.lfjg.frame.Frame;
import me.hannsi.lfjg.frame.setting.system.FrameSettingBase;
import me.hannsi.lfjg.frame.setting.system.ReflectionsLevel;
import org.lwjgl.glfw.GLFW;

/**
 * Represents a setting for the height of a frame.
 */
@ReflectionsLevel(level = 3)
public class HeightSetting extends FrameSettingBase<Integer> {

    /**
     * Constructs a new HeightSetting with the specified frame.
     *
     * @param frame the frame to associate with this setting
     */
    public HeightSetting(Frame frame) {
        super(frame, "HeightSetting", 3, DisplayUtil.getDisplaySizeDimension().height);
    }

    /**
     * Updates the height setting.
     * Sets the GLFW window size based on the current value.
     */
    @Override
    public void updateSetting() {
        if (getFrame().getWindowID() != -1L) {
            GLFW.glfwSetWindowSize(getFrame().getWindowID(), getFrame().getFrameSettingValue(WidthSetting.class), getValue());
        }

        super.updateSetting();
    }
}