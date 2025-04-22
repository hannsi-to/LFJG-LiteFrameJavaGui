package me.hannsi.lfjg.render.openGL.system.font;

import me.hannsi.lfjg.debug.debug.logger.LogGenerator;
import me.hannsi.lfjg.debug.debug.system.DebugLevel;

import java.util.HashMap;

public class FontCache {
    private HashMap<String, Font> fontCache;

    public FontCache() {
        fontCache = new HashMap<>();
    }

    public void createCache(Font font) {
        fontCache.put(font.getName(), font);

        new LogGenerator("FontCache Debug Message", "Source: FontCache", "Type: Cache Creation", "ID: " + font.getName(), "Severity: Info", "Message: Create font cache: " + font.getName()).logging(DebugLevel.DEBUG);
    }

    public void loadFont() {
        fontCache.forEach((key, value) -> value.loadFont());
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