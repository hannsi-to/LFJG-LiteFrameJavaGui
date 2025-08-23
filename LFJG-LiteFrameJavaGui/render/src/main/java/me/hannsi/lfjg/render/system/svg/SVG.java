package me.hannsi.lfjg.render.system.svg;

import me.hannsi.lfjg.core.debug.DebugLevel;
import me.hannsi.lfjg.core.debug.LogGenerateType;
import me.hannsi.lfjg.core.debug.LogGenerator;
import me.hannsi.lfjg.core.utils.math.io.IOUtil;
import me.hannsi.lfjg.core.utils.reflection.location.Location;
import me.hannsi.lfjg.core.utils.type.types.LocationType;

import java.nio.ByteBuffer;

public class SVG {
    private final String name;
    private final Location location;

    private ByteBuffer byteBuffer;

    public SVG(String name, Location location) {
        this.name = name;
        this.location = location;
    }

    public void loadSVG() {
        try {
            if (location.locationType() == LocationType.URL) {
                byteBuffer = IOUtil.downloadSVG(location);
            } else if (location.locationType() == LocationType.FILE || location.locationType() == LocationType.RESOURCE) {
                byteBuffer = IOUtil.svgToByteBuffer(location);
            }

            if (byteBuffer == null) {
                throw new RuntimeException("Invalid svg location: " + location);
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to load svg from location: " + location, e);
        }

        new LogGenerator(
                LogGenerateType.LOAD,
                getClass(),
                name,
                ""
        ).logging(DebugLevel.DEBUG);
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
