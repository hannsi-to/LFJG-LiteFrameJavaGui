package me.hannsi.lfjg.utils.graphics;

import me.hannsi.lfjg.utils.toolkit.ToolkitUtil;

import java.awt.*;

public class DisplayUtil {
    public static Dimension getScreenSize() {
        return ToolkitUtil.getDefaultToolkit().getScreenSize();
    }
}
