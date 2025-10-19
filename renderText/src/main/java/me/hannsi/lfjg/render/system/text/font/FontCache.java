package me.hannsi.lfjg.render.system.text.font;

import me.hannsi.lfjg.core.debug.DebugLevel;
import me.hannsi.lfjg.core.debug.DebugLog;
import me.hannsi.lfjg.core.debug.LogGenerateType;
import me.hannsi.lfjg.core.debug.LogGenerator;

import java.util.HashMap;
import java.util.Map;

public class FontCache {
    private final Map<String, Font> fontCaches;

    FontCache() {
        this.fontCaches = new HashMap<>();
    }

    public static FontCache createFontCache() {
        return new FontCache();
    }

    public FontCache createCache(String name, Font font) {
        fontCaches.put(name, font);

        new LogGenerator(
                LogGenerateType.CREATE_CACHE,
                getClass(),
                name,
                ""
        ).logging(getClass(), DebugLevel.DEBUG);

        return this;
    }

    public Font getFont(String name) {
        Font font = fontCaches.get(name);
        if (font == null) {
            DebugLog.warning(getClass(), "Font not found in cache: " + name);
        }
        return font;
    }

    public FontCache cleanup() {
        StringBuilder ids = new StringBuilder();
        int index = 0;
        for (Map.Entry<String, Font> font : fontCaches.entrySet()) {
            if (index == 0) {
                ids.append(font.getKey());
            } else {
                ids.append(", ").append(font.getKey());
            }
            font.getValue().cleanup();
            index++;
        }
        fontCaches.clear();

        new LogGenerator(
                LogGenerateType.CLEANUP,
                getClass(),
                ids.toString(),
                ""
        ).logging(getClass(), DebugLevel.DEBUG);

        return this;
    }

    public Map<String, Font> getFontCaches() {
        return fontCaches;
    }
}
