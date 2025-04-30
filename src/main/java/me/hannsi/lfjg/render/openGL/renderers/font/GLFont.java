package me.hannsi.lfjg.render.openGL.renderers.font;

import me.hannsi.lfjg.frame.frame.LFJGContext;
import me.hannsi.lfjg.render.openGL.renderers.polygon.GLRect;
import me.hannsi.lfjg.render.openGL.system.font.Font;
import me.hannsi.lfjg.render.openGL.system.rendering.FrameBuffer;
import me.hannsi.lfjg.utils.graphics.color.Color;
import me.hannsi.lfjg.utils.math.AlignExtractor;
import me.hannsi.lfjg.utils.toolkit.StringUtil;
import me.hannsi.lfjg.utils.type.types.AlignType;
import me.hannsi.lfjg.utils.type.types.TextFormatType;
import org.lwjgl.nanovg.NVGColor;
import org.lwjgl.opengl.GL30;

import java.util.List;

import static me.hannsi.lfjg.utils.graphics.NanoVGUtil.*;
import static me.hannsi.lfjg.utils.graphics.NanoVGUtil.nvgBeginPath;
import static me.hannsi.lfjg.utils.graphics.NanoVGUtil.nvgClosePath;
import static me.hannsi.lfjg.utils.graphics.NanoVGUtil.nvgFill;
import static me.hannsi.lfjg.utils.graphics.NanoVGUtil.nvgFillColor;
import static me.hannsi.lfjg.utils.graphics.NanoVGUtil.nvgFontFace;
import static me.hannsi.lfjg.utils.graphics.NanoVGUtil.nvgFontSize;
import static me.hannsi.lfjg.utils.graphics.NanoVGUtil.nvgLineTo;
import static me.hannsi.lfjg.utils.graphics.NanoVGUtil.nvgMoveTo;
import static me.hannsi.lfjg.utils.graphics.NanoVGUtil.nvgRestore;
import static me.hannsi.lfjg.utils.graphics.NanoVGUtil.nvgSave;
import static me.hannsi.lfjg.utils.graphics.NanoVGUtil.nvgStroke;
import static me.hannsi.lfjg.utils.graphics.NanoVGUtil.nvgStrokeColor;
import static me.hannsi.lfjg.utils.graphics.NanoVGUtil.nvgStrokeWidth;
import static me.hannsi.lfjg.utils.graphics.NanoVGUtil.nvgText;
import static me.hannsi.lfjg.utils.graphics.NanoVGUtil.nvgTextAlign;
import static me.hannsi.lfjg.utils.graphics.NanoVGUtil.nvgTextBounds;
import static me.hannsi.lfjg.utils.graphics.NanoVGUtil.nvgTextMetrics;
import static me.hannsi.lfjg.utils.graphics.NanoVGUtil.nvgTransform;
import static org.lwjgl.nanovg.NanoVG.*;

/**
 * Class representing a font renderer in OpenGL.
 */
public class GLFont extends GLRect {
    private FrameBuffer frameBuffer;

    private Font font;
    private String text;
    private float textX;
    private float textY;
    private float fontSize;
    private Color color;
    private AlignType align;

    /**
     * Constructs a new GLRect with the specified name.
     *
     * @param name the name of the rectangle renderer
     */
    public GLFont(String name) {
        super(name);
    }

    private static void drawLine(float x, float y, float x1, float y1, float lineWidth, Color color) {
        nvgBeginPath();

        nvgMoveTo(x, y);
        nvgLineTo(x1, y1);

        NVGColor nvgColor = colorToNVG(color);
        nvgStrokeColor(nvgColor);
        nvgStrokeWidth(lineWidth);
        nvgStroke();

        nvgClosePath();
    }

    private static void drawLineWH(float x, float y, float width, float height, float lineWidth, Color color) {
        drawLine(x, y, x + width, y + height, lineWidth, color);
    }

    public void text(Font font, String text, float x, float y, float fontSize, Color color, AlignType align) {
        this.font = font;
        this.text = text;
        this.fontSize = fontSize;
        this.color = color;
        this.align = align;
        this.textX = x;
        this.textY = y;

        this.frameBuffer = new FrameBuffer(0, 0, LFJGContext.resolution.x(), LFJGContext.resolution.y());
        this.frameBuffer.createFrameBuffer();
        this.frameBuffer.createShaderProgram();

        uv(0, 0, 1, 1);
        rectWH(0, 0, LFJGContext.resolution.x(), LFJGContext.resolution.y(), new Color(0, 0, 0, 0));
    }

    public float getWidth() {
        return getTextWidth(text);
    }

    public float getTextWidth(char ch) {
        return getTextWidth(StringUtil.getStringFromChar(ch));
    }

    public float getTextWidth(String str) {
        nvgFontFace(font.getName());
        nvgFontSize(fontSize);
        return nvgTextBounds(0, 0, str, new float[4]);
    }

    public float getHeight() {
        return getTextHeight();
    }

    public float getTextHeight() {
        nvgFontFace(font.getName());
        nvgFontSize(fontSize);

        float[] lineHeight = new float[1];
        nvgTextMetrics(new float[1], new float[1], lineHeight);

        return lineHeight[0];
    }

    @Override
    public void draw() {
        frameBuffer.bindFrameBuffer();

        drawTextFormat(font.getName(), textX, textY, color, align.getId());

        frameBuffer.unbindFrameBuffer();

        getGlUtil().addGLTarget(GL30.GL_TEXTURE_2D);
        GL30.glActiveTexture(GL30.GL_TEXTURE0);
        GL30.glBindTexture(GL30.GL_TEXTURE_2D, frameBuffer.getTextureId());
        super.draw();
        GL30.glBindTexture(GL30.GL_TEXTURE_2D, 0);
    }

    private void drawTextFormat(String fontName, float x, float y, Color color, int align) {
        nvgFramePush();

        float offsetX = 0.0f;
        float offsetY = 0.0f;
        boolean code = false;
        boolean obfuscated = false;
        char obfuscatedChar = '\u0000';
        boolean drawStrikethrough = false;
        float strikethroughLineX = 0.0f;
        float strikethroughLineY = 0.0f;
        float strikethroughLineWidth = 0.0f;
        float strikethroughLineHeight = 0.0f;
        boolean drawUnderline = false;
        float underLineX = 0.0f;
        float underLineY = 0.0f;
        float underLineWidth = 0.0f;
        float underLineHeight = 0.0f;
        boolean italic = false;
        float size = 0.0f;
        TextFormatType textFormatType = null;
        boolean bold = false;

        for (char ch : StringUtil.getChars(text)) {
            if (code) {
                textFormatType = TextFormatType.getTextFormatType(ch);
                code = false;
                continue;
            }

            if (textFormatType != null) {
                Color textFormatColor = TextFormatType.getColor(textFormatType);
                if (textFormatColor != null) {
                    color = textFormatColor;
                }

                switch (textFormatType) {
                    case REST -> {
                        drawStrikethrough = false;
                        strikethroughLineX = 0.0f;
                        strikethroughLineY = 0.0f;
                        strikethroughLineWidth = 0.0f;
                        strikethroughLineHeight = 0.0f;
                        drawUnderline = false;
                        underLineX = 0.0f;
                        underLineY = 0.0f;
                        underLineWidth = 0.0f;
                        underLineHeight = 0.0f;
                        italic = false;
                        size = 0.0f;
                        textFormatType = null;
                        obfuscated = false;
                        obfuscatedChar = '\u0000';
                        bold = false;
                    }
                    case OBFUSCATED -> {
                        String characters = "1234567890abcdefghijklmnopqrstuvwxyz~!@#$%^&*()-=_+{}[]";
                        obfuscatedChar = StringUtil.getRandomCharacter(characters);
                        obfuscated = true;
                    }
                    case BOLD -> {
                        bold = true;
                        size = 10f;
                    }
                    case STRIKETHROUGH -> {
                        List<Integer> alignments = AlignExtractor.getAlignmentsAsInteger(align);

                        for (Integer integer : alignments) {
                            switch (integer) {
                                case NVG_ALIGN_LEFT:
                                    strikethroughLineX = 0.0f;
                                    strikethroughLineWidth = getTextWidth(ch);
                                    break;
                                case NVG_ALIGN_CENTER:
                                    float widthCenter = getTextWidth(ch) / 2f;
                                    strikethroughLineX = -widthCenter;
                                    strikethroughLineWidth = getTextWidth(ch);
                                    break;
                                case NVG_ALIGN_RIGHT:
                                    strikethroughLineX = -getTextWidth(ch);
                                    strikethroughLineWidth = getTextWidth(ch);
                                    break;
                            }
                        }

                        strikethroughLineY = fontSize * 0.3f;

                        drawStrikethrough = true;
                    }
                    case UNDERLINE -> {
                        List<Integer> alignments = AlignExtractor.getAlignmentsAsInteger(align);

                        for (Integer integer : alignments) {
                            switch (integer) {
                                case NVG_ALIGN_LEFT:
                                    underLineX = 0.0f;
                                    underLineWidth = getTextWidth(ch);
                                    break;
                                case NVG_ALIGN_CENTER:
                                    float widthCenter = getTextWidth(ch) / 2f;
                                    underLineX = -widthCenter;
                                    underLineWidth = getTextWidth(ch);
                                    break;
                                case NVG_ALIGN_RIGHT:
                                    underLineX = -getTextWidth(ch);
                                    underLineWidth = getTextWidth(ch);
                                    break;
                            }
                        }

                        float[] ascender = new float[1];
                        float[] descender = new float[1];
                        float[] lineHeight = new float[1];

                        nvgTextMetrics(ascender, descender, lineHeight);

                        float lineOffset = fontSize * 0.3f;
                        float middleLine = -(ascender[0] - descender[0]) / 2f;
                        underLineY = middleLine + lineOffset;

                        drawUnderline = true;
                    }
                    case ITALIC -> italic = true;
                    case NEWLINE -> {
                        offsetX = 0.0f;
                        offsetY -= getTextHeight();
                        textFormatType = null;
                    }
                }
            }

            if (ch == TextFormatType.PREFIX_CODE) {
                code = true;
                continue;
            }

            float lineWidth = bold ? fontSize + size / 10f : fontSize / 10f;
            drawNanoVGText(fontName, obfuscated ? obfuscatedChar : ch, x + offsetX, y + offsetY, color, align, italic, bold ? size : 0);
            if (drawStrikethrough) {
                drawLineWH(x + offsetX + strikethroughLineX, y + offsetY + strikethroughLineY, strikethroughLineWidth, strikethroughLineHeight, lineWidth, Color.getRandomColor());
            }
            if (drawUnderline) {
                drawLineWH(x + offsetX + underLineX, y + offsetY + underLineY, underLineWidth, underLineHeight, lineWidth, Color.getRandomColor());
            }

            offsetX += getTextWidth(ch);
        }

        nvgFramePop();
    }

    private void drawNanoVGText(String fontName, char ch, float x, float y, Color color, int align, boolean italic, float plusSize) {
        NVGColor nvgColor = colorToNVG(color);
        nvgFillColor(nvgColor);

        nvgFontSize(fontSize + plusSize);
        nvgTextAlign(align);
        nvgFontFace(fontName);

        nvgSave();
        if (italic) {
            float skewX = (float) Math.tan(0.3);
            nvgTransform(
                    1, 0,
                    -skewX, 1,
                    skewX * (1060 - y), 0.0f
            );
        }

        nvgBeginPath();
        nvgText(x, y, StringUtil.getStringFromChar(ch));
        nvgFill();
        nvgRestore();
        nvgClosePath();
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public float getFontSize() {
        return fontSize;
    }

    public void setFontSize(float fontSize) {
        this.fontSize = fontSize;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public float getTextX() {
        return textX;
    }

    public void setTextX(float textX) {
        this.textX = textX;
    }

    public float getTextY() {
        return textY;
    }

    public void setTextY(float textY) {
        this.textY = textY;
    }
}