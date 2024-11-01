package me.hannsi.lfjg.render.nanoVG.system.font;

import me.hannsi.lfjg.utils.reflection.ResourcesLocation;
import org.lwjgl.nanovg.NanoVG;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class Font {
    private final String name;
    private ResourcesLocation resourcesLocation;
    private long nvg;
    private ByteBuffer byteBuffer;

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

    public void loadFont() {
        NanoVG.nvgCreateFontMem(nvg, name, getByteBuffer(), false);
    }

    public long getNvg() {
        return nvg;
    }

    public void setNvg(long nvg) {
        this.nvg = nvg;
    }

    public ByteBuffer getByteBuffer() {
        return byteBuffer;
    }

    public void setByteBuffer(ByteBuffer byteBuffer) {
        this.byteBuffer = byteBuffer;
    }

    public String getName() {
        return name;
    }

    public ResourcesLocation getResourcesLocation() {
        return resourcesLocation;
    }

    public void setResourcesLocation(ResourcesLocation resourcesLocation) {
        this.resourcesLocation = resourcesLocation;
    }
}
