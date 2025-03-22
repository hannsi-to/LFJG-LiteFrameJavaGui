package me.hannsi.lfjg.render.openGL.system.font;

import me.hannsi.lfjg.debug.debug.DebugLevel;
import me.hannsi.lfjg.debug.debug.LogGenerator;
import me.hannsi.lfjg.utils.reflection.FileLocation;
import me.hannsi.lfjg.utils.reflection.ResourcesLocation;

import java.util.HashMap;
import java.util.Map;

/**
 * Manages a cache of fonts for OpenGL rendering.
 */
public class FontCache {
    public static final ResourcesLocation DEFAULT_TEXTURE = new ResourcesLocation("font/default.ttf");
    public static final int DEFAULT_FONT_SIZE = 1;
    private static boolean autoCreateFontCache = true;

    private Map<FileLocation, FontData> fontMap;

    /**
     * Constructs a new FontCache and initializes it with the default font.
     */
    public FontCache() {
        this.fontMap = new HashMap<>();

        createCache(DEFAULT_TEXTURE, DEFAULT_FONT_SIZE);
    }

    /**
     * Checks if the font cache is set to auto-create.
     *
     * @return true if the font cache is set to auto-create, false otherwise
     */
    public static boolean isAutoCreateFontCache() {
        return autoCreateFontCache;
    }

    /**
     * Sets whether the font cache should auto-create fonts.
     *
     * @param autoCreateFontCache true to enable auto-creation, false to disable
     */
    public static void setAutoCreateFontCache(boolean autoCreateFontCache) {
        FontCache.autoCreateFontCache = autoCreateFontCache;
    }

    /**
     * Creates a font cache for the specified font path and size.
     *
     * @param fontPath the file path of the font
     * @param fontSize the size of the font
     */
    public void createCache(FileLocation fontPath, int fontSize) {
        FontData fontData = new FontData(new CFont(fontPath, fontSize), fontSize);

        fontMap.put(fontPath, fontData);

        LogGenerator logGenerator = new LogGenerator("FontCache Debug Message", "Source: FontCache", "Type: Cache Creation", "ID: " + fontPath.hashCode(), "Severity: Info", "Message: Create font cache: " + fontPath.getPath() + " | Font size: " + fontSize);
        logGenerator.logging(DebugLevel.DEBUG);
    }

    /**
     * Cleans up the font cache by clearing all cached fonts.
     */
    public void cleanup() {
        fontMap.values().forEach(fontData -> {
            fontData.getcFont().cleanup();
        });

        fontMap.clear();

        LogGenerator logGenerator = new LogGenerator("FontCache", "Source: FontCache", "Type: Cleanup", "ID: " + this.hashCode(), "Severity: Debug", "Message: FontCache cleanup is complete.");
        logGenerator.logging(DebugLevel.DEBUG);
    }

    /**
     * Retrieves a font from the cache or creates it if not present.
     *
     * @param fontPath the file path of the font
     * @param fontSize the size of the font
     * @return the cached or newly created font
     */
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

    /**
     * Gets the font map.
     *
     * @return the font map
     */
    public Map<FileLocation, FontData> getFontMap() {
        return fontMap;
    }

    /**
     * Sets the font map.
     *
     * @param fontMap the font map to set
     */
    public void setFontMap(Map<FileLocation, FontData> fontMap) {
        this.fontMap = fontMap;
    }

    /**
     * Represents font data including the font and its size.
     */
    public static class FontData {
        private CFont cFont;
        private int fontSize;

        /**
         * Constructs a new FontData with the specified font and size.
         *
         * @param cFont    the font
         * @param fontSize the size of the font
         */
        public FontData(CFont cFont, int fontSize) {
            this.cFont = cFont;
            this.fontSize = fontSize;
        }

        /**
         * Gets the font.
         *
         * @return the font
         */
        public CFont getcFont() {
            return cFont;
        }

        /**
         * Sets the font.
         *
         * @param cFont the font to set
         */
        public void setcFont(CFont cFont) {
            this.cFont = cFont;
        }

        /**
         * Gets the size of the font.
         *
         * @return the size of the font
         */
        public int getFontSize() {
            return fontSize;
        }

        /**
         * Sets the size of the font.
         *
         * @param fontSize the size to set
         */
        public void setFontSize(int fontSize) {
            this.fontSize = fontSize;
        }
    }
}