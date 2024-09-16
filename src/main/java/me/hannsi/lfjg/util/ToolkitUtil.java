package me.hannsi.lfjg.util;

import java.awt.*;

public class ToolkitUtil {
    public static Toolkit getDefaultToolkit() {
        return Toolkit.getDefaultToolkit();
    }

    public static String get(String key, String defaultValue) {
        return Toolkit.getProperty(key, defaultValue);
    }
}
