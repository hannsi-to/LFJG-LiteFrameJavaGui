package me.hannsi.lfjg.render.openGL.system.font;

import me.hannsi.lfjg.debug.debug.DebugLog;
import me.hannsi.lfjg.debug.debug.LogGenerator;
import me.hannsi.lfjg.utils.reflection.FileLocation;
import me.hannsi.lfjg.utils.reflection.ResourcesLocation;

import java.util.HashMap;
import java.util.Map;

public class FontCache {
    public static final ResourcesLocation DEFAULT_TEXTURE = new ResourcesLocation("font/default.ttf");
    public static final int DEFAULT_FONT_SIZE = 64;
    private static boolean autoCreateFontCache = true;

    private Map<FileLocation, FontData> fontMap;

    public FontCache() {
        this.fontMap = new HashMap<>();

        createCache(DEFAULT_TEXTURE, DEFAULT_FONT_SIZE);
    }

    public static boolean isAutoCreateFontCache() {
        return autoCreateFontCache;
    }

    public static void setAutoCreateFontCache(boolean autoCreateFontCache) {
        FontCache.autoCreateFontCache = autoCreateFontCache;
    }

    public void createCache(FileLocation fontPath, int fontSize) {
        FontData fontData = new FontData(new CFont(fontPath, fontSize), fontSize);

        fontMap.put(fontPath, fontData);

        LogGenerator logGenerator = new LogGenerator("FontCache Debug Message", "Source: FontCache", "Type: Cache Creation", "ID: " + fontPath.hashCode(), "Severity: Info", "Message: Create font cache: " + fontPath.getPath() + " | Font size: " + fontSize);

        DebugLog.debug(getClass(), logGenerator.createLog());
    }

    public void cleanup() {
        fontMap.values().forEach(fontData -> {
            fontData.getcFont().cleanup();
        });

        fontMap.clear();
    }

    public CFont getFont(FileLocation fontPath, int fontSize) {
        FontData fontData = null;
        if (fontMap.containsKey(fontPath)) {
            fontData = fontMap.get(fontPath);
        }

        if (fontData == null && autoCreateFontCache) {
            createCache(fontPath, fontSize);

            return getFont(fontPath, fontSize);
        }

        CFont cFont = null;
        if (fontData != null && fontData.getFontSize() == fontSize) {
            cFont = fontData.getcFont();
        }

        if (cFont == null && autoCreateFontCache) {
            createCache(fontPath, fontSize);

            return getFont(fontPath, fontSize);
        }

        return cFont;
    }

    public Map<FileLocation, FontData> getFontMap() {
        return fontMap;
    }

    public void setFontMap(Map<FileLocation, FontData> fontMap) {
        this.fontMap = fontMap;
    }

    public static class FontData {
        private CFont cFont;
        private int fontSize;

        public FontData(CFont cFont, int fontSize) {
            this.cFont = cFont;
            this.fontSize = fontSize;
        }

        public CFont getcFont() {
            return cFont;
        }

        public void setcFont(CFont cFont) {
            this.cFont = cFont;
        }

        public int getFontSize() {
            return fontSize;
        }

        public void setFontSize(int fontSize) {
            this.fontSize = fontSize;
        }
    }
}
