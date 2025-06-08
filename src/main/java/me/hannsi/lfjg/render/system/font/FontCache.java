package me.hannsi.lfjg.render.system.font;

import me.hannsi.lfjg.debug.DebugLevel;
import me.hannsi.lfjg.debug.LogGenerateType;
import me.hannsi.lfjg.debug.LogGenerator;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicReference;

public class FontCache {
    private HashMap<String, Font> fontCache;

    private FontCache() {
        fontCache = new HashMap<>();
    }

    public static FontCache initFontCache() {
        return new FontCache();
    }

    public FontCache createCache(Font font) {
        fontCache.put(font.getName(), font);

        new LogGenerator(
                LogGenerateType.CREATE_CACHE,
                getClass(),
                font.getName(),
                ""
        ).logging(DebugLevel.DEBUG);

        return this;
    }

    public FontCache loadFonts() {
        fontCache.forEach((key, value) -> value.loadFont());
        return this;
    }

    public Font getFont(String fontName) {
        return fontCache.get(fontName);
    }

    public void cleanup(String... fontNames) {
        for (String name : fontNames) {
            Font font = fontCache.remove(name);
            if (font != null) {
                font.cleanup();

                new LogGenerator(
                        LogGenerateType.CLEANUP,
                        getClass(),
                        name,
                        ""
                ).logging(DebugLevel.DEBUG);
            } else {
                new LogGenerator(
                        getClass().getSimpleName() + " Debug Message",
                        "Source: " + getClass().getName(),
                        "Type: Cache Cleanup",
                        "ID: " + name,
                        "Severity: Warning",
                        "Message: Font not found in cache for cleanup: " + name
                ).logging(DebugLevel.WARNING);
            }
        }
    }

    public void cleanup() {
        AtomicReference<String> ids = new AtomicReference<>();
        fontCache.forEach((key, value) -> {
            value.cleanup();
            ids.set(ids.get() + ", ");
        });
        fontCache.clear();

        new LogGenerator(
                LogGenerateType.CLEANUP,
                getClass(),
                ids.get().substring(0, ids.get().length() - 2),
                ""
        ).logging(DebugLevel.DEBUG);
    }

    public HashMap<String, Font> getFontCache() {
        return fontCache;
    }

    public void setFontCache(HashMap<String, Font> fontCache) {
        this.fontCache = fontCache;
    }
}