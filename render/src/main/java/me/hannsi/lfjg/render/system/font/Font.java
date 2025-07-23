package me.hannsi.lfjg.render.system.font;

import me.hannsi.lfjg.core.debug.DebugLevel;
import me.hannsi.lfjg.core.debug.LogGenerateType;
import me.hannsi.lfjg.core.debug.LogGenerator;
import me.hannsi.lfjg.core.utils.math.io.IOUtil;
import me.hannsi.lfjg.core.utils.reflection.location.Location;
import me.hannsi.lfjg.core.utils.type.types.LocationType;
import org.jetbrains.annotations.NotNull;

import java.io.InputStream;
import java.net.URL;
import java.nio.ByteBuffer;

import static me.hannsi.lfjg.core.utils.graphics.NanoVGUtil.nvgCreateFontMem;

public class Font {
    private final String name;
    private final Location location;
    private ByteBuffer byteBuffer;

    public Font(String name, Location location) {
        this.name = name;
        this.location = location;
    }

    public void cleanup() {
        byteBuffer.clear();
        byteBuffer = null;

        new LogGenerator(
                LogGenerateType.CLEANUP,
                getClass(),
                name,
                ""
        ).logging(DebugLevel.DEBUG);
    }

    public void loadFont() {
        InputStream inputStream = getInputStream();
        this.byteBuffer = IOUtil.toByteBuffer(inputStream);
        nvgCreateFontMem(name, byteBuffer, false);

        new LogGenerator(
                LogGenerateType.LOAD,
                getClass(),
                name,
                name
        ).logging(DebugLevel.DEBUG);
    }

    private @NotNull InputStream getInputStream() {
        InputStream inputStream = null;
        try {
            if (location.locationType() == LocationType.URL) {
                URL url = location.getURL();
                inputStream = url.openStream();
            } else if (location.locationType() == LocationType.FILE || location.locationType() == LocationType.RESOURCE) {
                inputStream = location.openStream();
            }

            if (inputStream == null) {
                throw new RuntimeException("Invalid font location: " + location);
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to load font from location: " + location, e);
        }
        return inputStream;
    }

    public String getName() {
        return name;
    }

    public Location getLocation() {
        return location;
    }

    public ByteBuffer getByteBuffer() {
        return byteBuffer;
    }

    public void setByteBuffer(ByteBuffer byteBuffer) {
        this.byteBuffer = byteBuffer;
    }
}
