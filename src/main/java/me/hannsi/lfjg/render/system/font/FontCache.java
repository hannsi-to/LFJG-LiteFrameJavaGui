package me.hannsi.lfjg.render.system.font;

import lombok.Getter;
import lombok.Setter;
import me.hannsi.lfjg.core.debug.DebugLevel;
import me.hannsi.lfjg.core.debug.LogGenerateType;
import me.hannsi.lfjg.core.debug.LogGenerator;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicReference;

@Getter
@Setter
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

}