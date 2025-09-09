package me.hannsi.lfjg.frame.setting.settings;

import me.hannsi.lfjg.core.utils.graphics.DisplayUtil;
import me.hannsi.lfjg.frame.Frame;
import me.hannsi.lfjg.frame.setting.system.FrameSettingBase;
import me.hannsi.lfjg.frame.setting.system.ReflectionsLevel;
import org.lwjgl.glfw.GLFW;

/**
 * Represents a setting for the width of a frame.
 */
@ReflectionsLevel(level = 2)
public class WidthSetting extends FrameSettingBase<Integer> {

    /**
     * Constructs a new WidthSetting with the specified frame.
     *
     * @param frame the frame to associate with this setting
     */
    public WidthSetting(Frame frame) {
        super(frame, "WidthSetting", 2, DisplayUtil.getDisplaySizeDimension().width);
    }

    /**
     * Updates the width setting.
     * Sets the GLFW window size based on the current width value and the height value from the HeightSetting.
     */
    @Override
    public void updateSetting() {
        if (getFrame().getWindowID() != -1L) {
            GLFW.glfwSetWindowSize(getFrame().getWindowID(), getValue(), getFrame().getFrameSettingValue(HeightSetting.class));
        }

        super.updateSetting();
    }
}