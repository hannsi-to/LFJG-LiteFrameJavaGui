package me.hannsi.lfjg.render.system.font;

import me.hannsi.lfjg.debug.LogGenerator;
import me.hannsi.lfjg.debug.DebugLevel;

import java.util.HashMap;

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

        new LogGenerator("FontCache Debug Message", "Source: FontCache", "Type: Cache Creation", "ID: " + font.getName(), "Severity: Info", "Message: Create font cache: " + font.getName()).logging(DebugLevel.DEBUG);

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

                new LogGenerator("FontCache Debug Message", "Source: FontCache", "Type: Cache Cleanup", "ID: " + name, "Severity: Info", "Message: Cleanup font: " + name).logging(DebugLevel.DEBUG);
            } else {
                new LogGenerator("FontCache Debug Message", "Source: FontCache", "Type: Cache Cleanup", "ID: " + name, "Severity: Warning", "Message: Font not found in cache for cleanup: " + name).logging(DebugLevel.WARNING);
            }
        }
    }

    public void cleanup() {
        fontCache.forEach((key, value) -> value.cleanup());
        fontCache.clear();

        new LogGenerator("FontCache Debug Message", "Source: FontCache", "Type: Cache Cleanup", "ID: All", "Severity: Info", "Message: Cleanup font cache").logging(DebugLevel.DEBUG);
    }

    public HashMap<String, Font> getFontCache() {
        return fontCache;
    }

    public void setFontCache(HashMap<String, Font> fontCache) {
        this.fontCache = fontCache;
    }
}