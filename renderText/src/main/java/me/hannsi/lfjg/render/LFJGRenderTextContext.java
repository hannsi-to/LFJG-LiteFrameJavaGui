package me.hannsi.lfjg.render;

import me.hannsi.lfjg.render.system.text.font.FontCache;

public class LFJGRenderTextContext {
    public static final FontCache FONT_CACHE;

    static {
        FONT_CACHE = FontCache.createFontCache();
    }
}
