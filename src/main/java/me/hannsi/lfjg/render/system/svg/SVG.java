package me.hannsi.lfjg.render.system.svg;

import me.hannsi.lfjg.debug.DebugLevel;
import me.hannsi.lfjg.debug.LogGenerateType;
import me.hannsi.lfjg.debug.LogGenerator;
import me.hannsi.lfjg.utils.reflection.location.FileLocation;
import me.hannsi.lfjg.utils.reflection.location.Location;
import me.hannsi.lfjg.utils.reflection.location.URLLocation;
import me.hannsi.lfjg.utils.toolkit.IOUtil;

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
            if (location.isUrl()) {
                byteBuffer = IOUtil.downloadSVG((URLLocation) location);
            } else if (location.isPath()) {
                byteBuffer = IOUtil.svgToByteBuffer((FileLocation) location);
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
