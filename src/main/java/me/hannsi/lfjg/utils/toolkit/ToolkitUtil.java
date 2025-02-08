package me.hannsi.lfjg.utils.toolkit;

import java.awt.*;

/**
 * Utility class for interacting with the AWT Toolkit.
 */
public class ToolkitUtil {

    /**
     * Gets the default toolkit.
     *
     * @return the default toolkit
     */
    public static Toolkit getDefaultToolkit() {
        return Toolkit.getDefaultToolkit();
    }

    /**
     * Gets the property value associated with the specified key.
     *
     * @param key the key of the property
     * @param defaultValue the default value to return if the property is not found
     * @return the property value associated with the specified key, or the default value if the property is not found
     */
    public static String get(String key, String defaultValue) {
        return Toolkit.getProperty(key, defaultValue);
    }
}