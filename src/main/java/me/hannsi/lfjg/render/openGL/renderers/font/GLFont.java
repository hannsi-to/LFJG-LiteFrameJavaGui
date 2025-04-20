package me.hannsi.lfjg.render.openGL.renderers.font;

import me.hannsi.lfjg.frame.frame.LFJGContext;
import me.hannsi.lfjg.render.openGL.renderers.polygon.GLRect;
import me.hannsi.lfjg.render.openGL.system.font.Font;
import me.hannsi.lfjg.render.openGL.system.rendering.FrameBuffer;
import me.hannsi.lfjg.utils.graphics.color.Color;
import org.lwjgl.nanovg.NVGColor;
import org.lwjgl.opengl.GL30;

import static me.hannsi.lfjg.utils.graphics.NanoVGUtil.nvgBeginPath;
import static me.hannsi.lfjg.utils.graphics.NanoVGUtil.nvgClosePath;
import static me.hannsi.lfjg.utils.graphics.NanoVGUtil.nvgFill;
import static me.hannsi.lfjg.utils.graphics.NanoVGUtil.nvgFillColor;
import static me.hannsi.lfjg.utils.graphics.NanoVGUtil.nvgFontFace;
import static me.hannsi.lfjg.utils.graphics.NanoVGUtil.nvgFontSize;
import static me.hannsi.lfjg.utils.graphics.NanoVGUtil.nvgRotate;
import static me.hannsi.lfjg.utils.graphics.NanoVGUtil.nvgText;
import static me.hannsi.lfjg.utils.graphics.NanoVGUtil.nvgTextAlign;
import static me.hannsi.lfjg.utils.graphics.NanoVGUtil.*;
import static org.lwjgl.nanovg.NanoVG.*;

/**
 * Class representing a font renderer in OpenGL.
 */
public class GLFont extends GLRect {
    private FrameBuffer frameBuffer;
    private Font font;
    private String text;
    private float x;
    private float y;
    private float fontSize;
    private Color color;

    /**
     * Constructs a new GLRect with the specified name.
     *
     * @param name the name of the rectangle renderer
     */
    public GLFont(String name) {
        super(name);
    }

    public void text(Font font, String text, float x, float y, float fontSize, Color color) {
        this.font = font;
        this.text = text;
        this.x = x;
        this.y = y;
        this.fontSize = fontSize;
        this.color = color;

        this.frameBuffer = new FrameBuffer();
        this.frameBuffer.createFrameBuffer();
        this.frameBuffer.createShaderProgram();

        uv(0, 0, 1, 1);
        rectWH(0, 0, LFJGContext.resolution.x(), LFJGContext.resolution.y(), new Color(0, 0, 0, 0));
    }

    @Override
    public void draw() {
        float italic = 0f;
        float plusSize = 0f;

        frameBuffer.bindFrameBuffer();

        nvgFramePush();

        nvgBeginPath();
        nvgRotate(italic);
        NVGColor nvgColor = colorToNVG(color);
        nvgFillColor(nvgColor);

        nvgFontSize(fontSize + plusSize);
        nvgTextAlign(NVG_ALIGN_LEFT | NVG_ALIGN_BASELINE);
        nvgFontFace(font.getName());
        nvgText(x, y, text);

        nvgRGBAf(0, 0, 0, 0, nvgColor);
        nvgFillColor(nvgColor);
        nvgFill();
        nvgRotate(0.0f);

        nvgRotate(-italic);
        nvgClosePath();

        nvgFramePop();

        frameBuffer.unbindFrameBuffer();

        getGlUtil().addGLTarget(GL30.GL_TEXTURE_2D);
        GL30.glActiveTexture(GL30.GL_TEXTURE0);
        GL30.glBindTexture(GL30.GL_TEXTURE_2D, frameBuffer.getTextureId());
        super.draw();
        GL30.glBindTexture(GL30.GL_TEXTURE_2D, 0);
    }
}