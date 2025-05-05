package me.hannsi.lfjg.render.openGL.system.font;

import me.hannsi.lfjg.debug.debug.logger.LogGenerator;
import me.hannsi.lfjg.debug.debug.system.DebugLevel;
import me.hannsi.lfjg.utils.reflection.location.FileLocation;
import me.hannsi.lfjg.utils.reflection.location.Location;
import me.hannsi.lfjg.utils.reflection.location.URLLocation;
import me.hannsi.lfjg.utils.toolkit.IOUtil;
import org.jetbrains.annotations.NotNull;

import java.io.InputStream;
import java.net.URL;
import java.nio.ByteBuffer;

import static me.hannsi.lfjg.utils.graphics.NanoVGUtil.nvgCreateFontMem;

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

        new LogGenerator("Font Debug Message", "Source: Font", "Type: Font Cleanup", "ID: " + getName(), "Severity: Info", "Message: Cleanup font");
    }

    public void loadFont() {
        InputStream inputStream = getInputStream();

        this.byteBuffer = IOUtil.toByteBuffer(inputStream);

        nvgCreateFontMem(name, byteBuffer, false);

        new LogGenerator("Font Debug Message", "Source: Font", "Type: Load SVG", "ID: " + getName(), "Severity: Info", "Message: Load font: " + getName()).logging(DebugLevel.DEBUG);
    }

    private @NotNull InputStream getInputStream() {
        InputStream inputStream = null;
        try {
            if (location.isUrl()) {
                URL url = ((URLLocation) location).getURL();
                inputStream = url.openStream();
            } else if (location.isPath()) {
                FileLocation fileLocation = (FileLocation) location;
                inputStream = fileLocation.getInputStream();
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
