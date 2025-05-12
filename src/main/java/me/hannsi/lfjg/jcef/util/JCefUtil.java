package me.hannsi.lfjg.jcef.util;

import me.hannsi.lfjg.utils.graphics.color.Color;
import org.cef.CefSettings;

public class JCefUtil {
    public static CefSettings.ColorType color(CefSettings cefSettings, Color color) {
        return new CefSettings.ColorType(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
    }
}
