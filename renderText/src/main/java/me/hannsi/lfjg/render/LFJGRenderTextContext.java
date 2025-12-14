package me.hannsi.lfjg.render;

import me.hannsi.lfjg.render.system.text.font.FontCache;

public class LFJGRenderTextContext {
    public static FontCache FONT_CACHE;

    public static void init() {
        FONT_CACHE = FontCache.createFontCache();
    }
}
