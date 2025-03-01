package me.hannsi.lfjg.render.openGL.renderers.svg;

import me.hannsi.lfjg.render.openGL.renderers.polygon.GLRect;
import me.hannsi.lfjg.utils.buffer.ByteUtil;
import me.hannsi.lfjg.utils.graphics.color.Color;
import me.hannsi.lfjg.utils.math.MathHelper;
import me.hannsi.lfjg.utils.reflection.FileLocation;
import me.hannsi.lfjg.utils.reflection.URLLocation;
import org.lwjgl.nanovg.NSVGImage;
import org.lwjgl.opengl.GL30;
import org.lwjgl.system.MemoryStack;

import java.nio.ByteBuffer;

import static org.lwjgl.nanovg.NanoSVG.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.GL_CLAMP_TO_EDGE;
import static org.lwjgl.stb.STBImageResize.STBIR_RGBA_PM;
import static org.lwjgl.stb.STBImageResize.stbir_resize_uint8_srgb;
import static org.lwjgl.system.MemoryUtil.*;

public class GLSVG extends GLRect {
    private int texture;

    /**
     * Constructs a new GLPolygon with the specified name.
     *
     * @param name the name of the polygon renderer
     */
    public GLSVG(String name) {
        super(name);
    }

    public void svg(ByteBuffer svgData, float x, float y, float scaleX, float scaleY) {
        NSVGImage svg;
        float svgWidth;
        float svgHeight;
        try (MemoryStack stack = MemoryStack.stackPush()) {
            svg = nsvgParse(svgData, stack.ASCII("px"), 96.0f);
            if (svg == null) {
                throw new IllegalStateException("Failed to parse SVG");
            }

            svgWidth = svg.width();
            svgHeight = svg.height();
        } finally {
            memFree(svgData);
        }

        createTexture(svg, svgWidth, svgHeight, scaleX, scaleY);

        uv(0, 1, 1, 0);
        rectWH(x, y, svgWidth * scaleX, svgHeight * scaleY, Color.of(0, 0, 0, 0), Color.of(0, 0, 0, 0), Color.of(0, 0, 0, 0), Color.of(0, 0, 0, 0));
    }

    public void svg(ByteBuffer svgData, double x, double y, double scaleX, double scaleY) {
        svg(svgData, (float) x, (float) y, (float) scaleX, (float) scaleY);
    }

    public void svg(FileLocation fileLocation, float x, float y, float scaleX, float scaleY) {
        ByteBuffer svgData = ByteUtil.svgToByteBuffer(fileLocation);

        svg(svgData, x, y, scaleX, scaleY);
    }

    public void svg(FileLocation fileLocation, double x, double y, double scaleX, double scaleY) {
        svg(fileLocation, (float) x, (float) y, (float) scaleX, (float) scaleY);
    }

    public void svg(URLLocation urlLocation, float x, float y, float scaleX, float scaleY) {
        ByteBuffer svgData = ByteUtil.downloadSVG(urlLocation);

        svg(svgData, x, y, scaleX, scaleY);
    }

    public void svg(URLLocation urlLocation, double x, double y, double scaleX, double scaleY) {
        svg(urlLocation, (float) x, (float) y, (float) scaleX, (float) scaleY);
    }

    @Override
    public void draw() {
        getGlUtil().addGLTarget(GL30.GL_TEXTURE_2D);
        GL30.glActiveTexture(GL30.GL_TEXTURE0);
        GL30.glBindTexture(GL30.GL_TEXTURE_2D, texture);
        super.draw();
        GL30.glBindTexture(GL30.GL_TEXTURE_2D, 0);
    }

    private void createTexture(NSVGImage svg, float svgWidth, float svgHeight, float contentScaleX, float contentScaleY) {
        long rasterizer = nsvgCreateRasterizer();
        if (rasterizer == NULL) {
            throw new IllegalStateException("Failed to create SVG rasterizer.");
        }

        int width = (int) (svgWidth * contentScaleX);
        int height = (int) (svgHeight * contentScaleY);

        ByteBuffer image = memAlloc(width * height * 4);

        nsvgRasterize(rasterizer, svg, 0, 0, MathHelper.min(contentScaleX, contentScaleY), image, width, height, width * 4);
        nsvgDeleteRasterizer(rasterizer);

        texture = glGenTextures();

        glBindTexture(GL_TEXTURE_2D, texture);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);

        ByteUtil.premultiplyAlpha(image, width, height, width * 4);

        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, image);

        ByteBuffer input_pixels = image;
        int input_w = width;
        int input_h = height;
        int mipmapLevel = 0;
        while (1 < input_w || 1 < input_h) {
            int output_w = MathHelper.max(1, input_w >> 1);
            int output_h = MathHelper.max(1, input_h >> 1);

            ByteBuffer output_pixels = null;
            if (input_pixels != null) {
                output_pixels = stbir_resize_uint8_srgb(input_pixels, input_w, input_h, input_w * 4, null, output_w, output_h, output_w * 4, STBIR_RGBA_PM);
            }

            memFree(input_pixels);

            glTexImage2D(GL_TEXTURE_2D, ++mipmapLevel, GL_RGBA, output_w, output_h, 0, GL_RGBA, GL_UNSIGNED_BYTE, output_pixels);

            input_pixels = output_pixels;
            input_w = output_w;
            input_h = output_h;
        }
        memFree(input_pixels);
    }

    @Override
    public void cleanup() {
        glDeleteTextures(texture);

        super.cleanup();
    }
}
