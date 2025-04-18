package me.hannsi.lfjg.render.nanoVG.system.font;

import me.hannsi.lfjg.utils.reflection.location.ResourcesLocation;
import org.lwjgl.nanovg.NanoVG;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * Represents a font that can be used with NanoVG.
 */
public class Font {
    private final String name;
    private ResourcesLocation resourcesLocation;
    private long nvg;
    private ByteBuffer byteBuffer;

    /**
     * Constructs a new Font with the specified NanoVG context, name, and resource location.
     *
     * @param nvg               the NanoVG context
     * @param name              the name of the font
     * @param resourcesLocation the location of the font resource
     */
    public Font(long nvg, String name, ResourcesLocation resourcesLocation) {
        this.nvg = nvg;
        this.name = name;
        this.resourcesLocation = resourcesLocation;

        InputStream inputStream = this.getClass().getResourceAsStream(resourcesLocation.getPath());

        try {
            setByteBuffer(toByteBuffer(inputStream));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Converts an InputStream to a ByteBuffer.
     *
     * @param input the InputStream to convert
     * @return the resulting ByteBuffer
     * @throws IOException if an I/O error occurs
     */
    public static ByteBuffer toByteBuffer(InputStream input) throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        byte[] buffer = new byte[4096];

        for (int n = 0; n != -1; n = input.read(buffer)) {
            output.write(buffer, 0, n);
        }

        byte[] bytes = output.toByteArray();
        ByteBuffer data = ByteBuffer.allocateDirect(bytes.length).order(ByteOrder.nativeOrder()).put(bytes);
        data.flip();

        return data;
    }

    /**
     * Loads the font into the NanoVG context.
     */
    public void loadFont() {
        NanoVG.nvgCreateFontMem(nvg, name, getByteBuffer(), false);
    }

    /**
     * Gets the NanoVG context.
     *
     * @return the NanoVG context
     */
    public long getNvg() {
        return nvg;
    }

    /**
     * Sets the NanoVG context.
     *
     * @param nvg the NanoVG context
     */
    public void setNvg(long nvg) {
        this.nvg = nvg;
    }

    /**
     * Gets the ByteBuffer containing the font data.
     *
     * @return the ByteBuffer containing the font data
     */
    public ByteBuffer getByteBuffer() {
        return byteBuffer;
    }

    /**
     * Sets the ByteBuffer containing the font data.
     *
     * @param byteBuffer the ByteBuffer containing the font data
     */
    public void setByteBuffer(ByteBuffer byteBuffer) {
        this.byteBuffer = byteBuffer;
    }

    /**
     * Gets the name of the font.
     *
     * @return the name of the font
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the resource location of the font.
     *
     * @return the resource location of the font
     */
    public ResourcesLocation getResourcesLocation() {
        return resourcesLocation;
    }

    /**
     * Sets the resource location of the font.
     *
     * @param resourcesLocation the resource location of the font
     */
    public void setResourcesLocation(ResourcesLocation resourcesLocation) {
        this.resourcesLocation = resourcesLocation;
    }
}