package me.hannsi.lfjg.render.system.font;

import lombok.Getter;
import lombok.Setter;
import me.hannsi.lfjg.debug.DebugLevel;
import me.hannsi.lfjg.debug.LogGenerateType;
import me.hannsi.lfjg.debug.LogGenerator;
import me.hannsi.lfjg.utils.math.io.IOUtil;
import me.hannsi.lfjg.utils.reflection.location.FileLocation;
import me.hannsi.lfjg.utils.reflection.location.Location;
import me.hannsi.lfjg.utils.reflection.location.URLLocation;
import org.jetbrains.annotations.NotNull;

import java.io.InputStream;
import java.net.URL;
import java.nio.ByteBuffer;

import static me.hannsi.lfjg.utils.graphics.NanoVGUtil.nvgCreateFontMem;

@Getter
public class Font {
    private final String name;
    private final Location location;
    @Setter
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

}
