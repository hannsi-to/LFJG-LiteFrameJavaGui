package me.hannsi.lfjg.render.renderers.svg;

import me.hannsi.lfjg.core.utils.graphics.color.Color;
import me.hannsi.lfjg.core.utils.math.MathHelper;
import me.hannsi.lfjg.core.utils.math.io.IOUtil;
import me.hannsi.lfjg.render.renderers.polygon.GLRect;
import me.hannsi.lfjg.render.system.rendering.FrameBuffer;
import me.hannsi.lfjg.render.system.rendering.GLStateCache;
import org.lwjgl.nanovg.NSVGImage;
import org.lwjgl.opengl.GL30;
import org.lwjgl.system.MemoryStack;

import java.nio.ByteBuffer;

import static me.hannsi.lfjg.render.LFJGRenderContext.svgCache;
import static org.lwjgl.nanovg.NanoSVG.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.GL_CLAMP_TO_EDGE;
import static org.lwjgl.stb.STBImageResize.STBIR_RGBA_PM;
import static org.lwjgl.stb.STBImageResize.stbir_resize_uint8_srgb;
import static org.lwjgl.system.MemoryUtil.*;

public class GLSVG extends GLRect {
    private FrameBuffer frameBuffer;
    private SVGRenderer svgRenderer;
    private int textureId;

    /**
     * Constructs a new GLPolygon with the specified name.
     *
     * @param name the name of the polygon renderer
     */
    public GLSVG(String name) {
        super(name);
    }

    public void svg(String svgName, float x, float y, float scaleX, float scaleY) {
        ByteBuffer svgData = svgCache.getSVG(svgName).getByteBuffer();

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

        frameBuffer = new FrameBuffer(0, 0, svgWidth * scaleX, svgHeight * scaleY);
        frameBuffer.createFrameBuffer();
        frameBuffer.createMatrix();

        svgRenderer = new SVGRenderer();
        svgRenderer.addVertex(0, 0, svgWidth * scaleX, svgHeight * scaleY);
        svgRenderer.init();

        uv(0, 0, 1, 1);
        rectWH(x, y, svgWidth * scaleX, svgHeight * scaleY, new Color(0, 0, 0, 0));
    }

    public void svg(String svgName, double x, double y, double scaleX, double scaleY) {
        svg(svgName, (float) x, (float) y, (float) scaleX, (float) scaleY);
    }

    @Override
    public void draw() {
        frameBuffer.bindFrameBuffer();

        svgRenderer.flush(textureId, 0);

        frameBuffer.unbindFrameBuffer();

        GLStateCache.enable(GL_TEXTURE_2D);
        GL30.glActiveTexture(GL30.GL_TEXTURE0);
        GL30.glBindTexture(GL30.GL_TEXTURE_2D, frameBuffer.getTextureId());
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

        textureId = glGenTextures();

        glBindTexture(GL_TEXTURE_2D, textureId);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);

        IOUtil.premultiplyAlpha(image, width, height, width * 4);

        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, image);

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

            glTexImage2D(GL_TEXTURE_2D, ++mipmapLevel, GL_RGBA8, output_w, output_h, 0, GL_RGBA, GL_UNSIGNED_BYTE, output_pixels);

            input_pixels = output_pixels;
            input_w = output_w;
            input_h = output_h;
        }
        memFree(input_pixels);
    }

    @Override
    public void cleanup() {
        glDeleteTextures(textureId);

        super.cleanup();
    }

    public FrameBuffer getFrameBuffer() {
        return frameBuffer;
    }

    public void setFrameBuffer(FrameBuffer frameBuffer) {
        this.frameBuffer = frameBuffer;
    }

    public SVGRenderer getSvgRenderer() {
        return svgRenderer;
    }

    public void setSvgRenderer(SVGRenderer svgRenderer) {
        this.svgRenderer = svgRenderer;
    }

    public int getTextureId() {
        return textureId;
    }

    public void setTextureId(int textureId) {
        this.textureId = textureId;
    }
}

